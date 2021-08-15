package com.blablaprincess.springboot_simplejava.business.methodcalls;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class MethodCallsHistoryAfterHandlingProcessorTests {

    private final MethodCallsHistory service = mock(MethodCallsHistory.class);
    private final ObjectMapper mapper = mock(ObjectMapper.class);

    private final MethodCallsHistoryAfterHandlingProcessor methodCallsHistoryAfterDispatchingProcessor
            = new MethodCallsHistoryAfterHandlingProcessor(service, mapper);

    @Test
    void processOnSuccess() {
        // Act
        methodCallsHistoryAfterDispatchingProcessor.processOnSuccess(new Object[0], mock(Signature.class), null);

        // Assert
        verify(service).saveCall(any(MethodCallDescriptionEntity.class));
    }

    @Test
    void processOnThrows() {
        // Act
        methodCallsHistoryAfterDispatchingProcessor.processOnThrows(new Object[0], mock(Signature.class), null);

        // Assert
        verify(service).saveCall(any(MethodCallDescriptionEntity.class));
    }

}