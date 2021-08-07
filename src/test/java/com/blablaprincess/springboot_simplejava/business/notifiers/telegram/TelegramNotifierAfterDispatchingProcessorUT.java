package com.blablaprincess.springboot_simplejava.business.notifiers.telegram;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.*;

class TelegramNotifierAfterDispatchingProcessorUT {

    private final TelegramBotNotifierService service = mock(TelegramBotNotifierService.class);

    private final TelegramBotNotifierAfterDispatchingProcessor telegramBotNotifierAfterDispatchingProcessor
            = new TelegramBotNotifierAfterDispatchingProcessor(service, new ObjectMapper());

    @Test
    void process() {
        // Act
        telegramBotNotifierAfterDispatchingProcessor.process("", mock(HttpServletRequest.class), mock(HttpServletResponse.class));

        // Assert
        verify(service, times(1)).sendNotification(anyString());
    }

}