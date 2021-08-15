package com.blablaprincess.springboot_simplejava.business.controllercalls;

import com.blablaprincess.springboot_simplejava.rest.afterdispatchingprocessor.AfterDispatchingProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
@RequiredArgsConstructor
public class ControllerCallsHistoryAfterDispatchingProcessor implements AfterDispatchingProcessor {

    private final ControllerCallsHistory controllerCallsHistoryService;

    @Override
    public void process(String responseBody, HttpServletRequest request, HttpServletResponse response) {
        LocalDateTime timestamp = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
        controllerCallsHistoryService.saveCall(ControllerCallDescriptionEntity.builder()
                                                                              .response(responseBody)
                                                                              .mapping(request.getRequestURI())
                                                                              .timestamp(timestamp)
                                                                              .build());
    }

}
