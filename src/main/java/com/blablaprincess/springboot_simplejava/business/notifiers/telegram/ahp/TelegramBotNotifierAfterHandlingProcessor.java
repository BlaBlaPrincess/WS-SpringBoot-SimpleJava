package com.blablaprincess.springboot_simplejava.business.notifiers.telegram.ahp;

import com.blablaprincess.springboot_simplejava.business.notifiers.telegram.TelegramBotNotifierService;
import com.blablaprincess.springboot_simplejava.business.notifiers.telegram.ahp.messagebuilder.TelegramBotNotifierAfterHandlingProcessorMessageBuilder;
import com.blablaprincess.springboot_simplejava.business.common.afterhandlingprocessor.AfterHandlingProcessor;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.Signature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "telegram-bot.ahp.enabled", havingValue = "true")
public class TelegramBotNotifierAfterHandlingProcessor implements AfterHandlingProcessor {

    private final TelegramBotNotifierService notifierService;
    private final TelegramBotNotifierAfterHandlingProcessorMessageBuilder messageBuilder;

    @Override
    public void processOnSuccess(Object[] args, Signature signature, Object response) {
        String message = messageBuilder.buildOnSuccess(args, signature, response);
        notifierService.sendNotification(message);
    }

    @Override
    public void processOnThrows(Object[] args, Signature signature, Throwable throwable) {
        String message = messageBuilder.buildOnThrows(args, signature, throwable);
        notifierService.sendNotification(message);
    }
}
