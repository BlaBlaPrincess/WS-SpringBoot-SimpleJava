package com.blablaprincess.springboot_simplejava.business.notifiers.telegram.ahp;

import com.blablaprincess.springboot_simplejava.business.notifiers.telegram.TelegramBotNotifierService;
import com.blablaprincess.springboot_simplejava.business.notifiers.telegram.ahp.messagebuilder.TelegramBotNotifierAfterHandlingProcessorMessageBuilder;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class TelegramNotifierAfterHandlingProcessorUT {

    private final TelegramBotNotifierService service = mock(TelegramBotNotifierService.class);
    private final TelegramBotNotifierAfterHandlingProcessorMessageBuilder messageBuilder =
            mock(TelegramBotNotifierAfterHandlingProcessorMessageBuilder.class);

    private final TelegramBotNotifierAfterHandlingProcessor telegramBotNotifierAfterHandlingProcessor
            = new TelegramBotNotifierAfterHandlingProcessor(service, messageBuilder);

    private final Object[] args = new Object[]{};
    private final Signature signature = mock(Signature.class);
    private final String message = "message";

    @Test
    void processOnSuccess() {
        // Arrange
        Object response = "response";
        when(messageBuilder.buildOnSuccess(args, signature, response)).thenReturn(message);

        // Act
        telegramBotNotifierAfterHandlingProcessor.processOnSuccess(args, signature, response);

        // Assert
        verify(service).sendNotification(message);
    }

    @Test
    void processOnThrows() {
        // Arrange
        Throwable throwable = new Throwable();
        when(messageBuilder.buildOnThrows(args, signature, throwable)).thenReturn(message);

        // Act
        telegramBotNotifierAfterHandlingProcessor.processOnThrows(args, signature, throwable);

        // Assert
        verify(service).sendNotification(message);
    }

}