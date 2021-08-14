package com.blablaprincess.springboot_simplejava.business.notifiers.telegram.adp;

import com.blablaprincess.springboot_simplejava.business.notifiers.telegram.TelegramBotNotifierService;
import com.blablaprincess.springboot_simplejava.business.notifiers.telegram.adp.messagebuilder.TelegramBotNotifierAfterDispatchingProcessorMessageBuilder;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.*;

class TelegramNotifierAfterDispatchingProcessorTests {

    private final TelegramBotNotifierService service = mock(TelegramBotNotifierService.class);
    private final TelegramBotNotifierAfterDispatchingProcessorMessageBuilder messageBuilder =
            mock(TelegramBotNotifierAfterDispatchingProcessorMessageBuilder.class);

    private final TelegramBotNotifierAfterDispatchingProcessor telegramBotNotifierAfterDispatchingProcessor
            = new TelegramBotNotifierAfterDispatchingProcessor(service, messageBuilder);

    @Test
    void process() {
        // Arrange
        String responseBody = "value";
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        String message = "message";
        when(messageBuilder.build(responseBody, request, response)).thenReturn(message);

        // Act
        telegramBotNotifierAfterDispatchingProcessor.process(responseBody, request, response);

        // Assert
        verify(service).sendNotification(message);
    }

}