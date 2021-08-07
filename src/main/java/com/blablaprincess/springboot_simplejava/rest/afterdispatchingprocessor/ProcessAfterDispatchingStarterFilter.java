package com.blablaprincess.springboot_simplejava.rest.afterdispatchingprocessor;

import com.blablaprincess.springboot_simplejava.business.common.utils.InputStreamUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
public class ProcessAfterDispatchingStarterFilter implements Filter {

    private final Map<RequestMappingInfo, HandlerMethod> handlerMethods;
    private final ApplicationContext context;

    private final Set<Class<? extends AfterDispatchingProcessor>> missedBeans = new HashSet<>();

    public ProcessAfterDispatchingStarterFilter(RequestMappingHandlerMapping mappingHandler, ApplicationContext context) {
        handlerMethods = mappingHandler.getHandlerMethods();
        this.context = context;
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        filterChain.doFilter(request, responseWrapper);

        HandlerMethod handlerMethod = handlerMethods.entrySet()
                                                    .stream()
                                                    .filter(method -> method.getKey().getMatchingCondition(request) != null)
                                                    .map(Map.Entry::getValue)
                                                    .findFirst()
                                                    .orElse(null);

        String responseBody = InputStreamUtils.toString(responseWrapper.getContentInputStream());
        responseWrapper.copyBodyToResponse();

        if (handlerMethod != null) {
            ProcessAfterDispatchingWith annotation = handlerMethod.getMethodAnnotation(ProcessAfterDispatchingWith.class);
            if (annotation == null) {
                annotation = handlerMethod.getMethod().getDeclaringClass().getAnnotation(ProcessAfterDispatchingWith.class);
            }
            if (annotation != null) {
                for (Class<? extends AfterDispatchingProcessor> processor : annotation.value()) {
                    try {
                        AfterDispatchingProcessor processorBean = context.getBean(processor);
                        processorBean.process(responseBody, request, response);
                    } catch (NoSuchBeanDefinitionException exception) {
                        if (!missedBeans.contains(processor)) {
                            log.warn("No such after dispatching processor bean for " + processor.getSimpleName());
                            missedBeans.add(processor);
                        }
                    }
                }
            }
        } else {
            log.warn(String.format("No handler found for %s %s", request.getMethod(), request.getRequestURI()));
        }
    }

}
