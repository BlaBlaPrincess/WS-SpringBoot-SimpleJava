package com.blablaprincess.springboot_simplejava.business.notifiers.telegram.adp;

import com.blablaprincess.springboot_simplejava.business.notifiers.telegram.TelegramBotNotifierService;
import com.blablaprincess.springboot_simplejava.business.notifiers.telegram.adp.messagebuilder.TelegramBotNotifierAfterDispatchingProcessorMessageBuilder;
import com.blablaprincess.springboot_simplejava.rest.afterdispatchingprocessor.AfterDispatchingProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "telegram-bot.adp.enabled", havingValue = "true")
public class TelegramBotNotifierAfterDispatchingProcessor implements AfterDispatchingProcessor {

    private final TelegramBotNotifierService notifierService;
    private final TelegramBotNotifierAfterDispatchingProcessorMessageBuilder messageBuilder;

    @Override
    public void process(String responseBody, HttpServletRequest request, HttpServletResponse response) {
        String message = messageBuilder.build(responseBody, request, response);
        notifierService.sendNotification(message);
    }

}
