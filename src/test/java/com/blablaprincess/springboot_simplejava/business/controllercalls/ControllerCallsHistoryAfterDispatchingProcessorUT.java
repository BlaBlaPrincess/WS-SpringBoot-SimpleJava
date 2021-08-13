package com.blablaprincess.springboot_simplejava.business.controllercalls;

import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.*;

class ControllerCallsHistoryAfterDispatchingProcessorUT {

    private final ControllerCallsHistory service = mock(ControllerCallsHistory.class);

    private final ControllerCallsHistoryAfterDispatchingProcessor controllerCallsHistoryAfterDispatchingProcessor
            = new ControllerCallsHistoryAfterDispatchingProcessor(service);

    @Test
    void process() {
        // Act
        controllerCallsHistoryAfterDispatchingProcessor.process("", mock(HttpServletRequest.class), mock(HttpServletResponse.class));

        // Assert
        verify(service).saveCall(any(ControllerCallDescriptionEntity.class));
    }

}