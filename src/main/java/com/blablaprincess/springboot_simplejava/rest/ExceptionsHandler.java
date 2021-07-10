package com.blablaprincess.springboot_simplejava.rest;

import com.blablaprincess.springboot_simplejava.business.BusinessException;
import com.blablaprincess.springboot_simplejava.rest.common.ProvidingSettersResponseWrapper;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

@RestControllerAdvice
public class ExceptionsHandler extends DefaultHandlerExceptionResolver {

    private final HashMap<Class<? extends Throwable>, Integer> exceptionResponseMap
            = new HashMap<Class<? extends Throwable>, Integer>() {{
        put(BusinessException.class, 500);
        put(RestException.class, 500);
    }};

    @ExceptionHandler(Exception.class)
    public ModelAndView handleServletException
            (Exception exception, HttpServletRequest request, HttpServletResponse response) throws IOException {

        ProvidingSettersResponseWrapper settersWrapper = new ProvidingSettersResponseWrapper(response);
        if (doResolveException(request, settersWrapper, null, exception) == null) {
            int code = exceptionResponseMap.getOrDefault(exception.getClass(), 500);
            settersWrapper.sendError(code);
        }

        String prefix = "Exception-";
        response.addHeader(    prefix + "Path",      request.getRequestURI());
        response.addHeader(    prefix + "Class",     exception.getClass().getName());
        response.addHeader(    prefix + "Message",   exception.getMessage());
        response.addDateHeader(prefix + "Timestamp", new Date().getTime());

        return new ModelAndView();
    }

    @Override
    protected void sendServerError(Exception ex, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendError(500);
    }

}