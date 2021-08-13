package com.blablaprincess.springboot_simplejava.business.notifiers.telegram;

import com.blablaprincess.springboot_simplejava.business.notifiers.Notifier;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Service
public class TelegramBotNotifierService implements Notifier {

    public final String HTTP_TOKEN;
    public final String CHAT_ID;

    private final String SEND_MESSAGE_TEMPLATE;

    private final WebClient client;

    public TelegramBotNotifierService(@Value("${telegram-bot.http-token}") String httpToken,
                                      @Value("${telegram-bot.chat-id}") String chatId,
                                      WebClient client) {
        HTTP_TOKEN = httpToken;
        CHAT_ID = chatId;
        this.client = client;

        SEND_MESSAGE_TEMPLATE = String.format("https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text={message}", HTTP_TOKEN, CHAT_ID);
    }

    @Override
    @Async("telegramBotNotifierServiceTaskExecutor")
    public void sendNotification(String message) {
        URI uri = UriComponentsBuilder.fromHttpUrl(SEND_MESSAGE_TEMPLATE)
                                      .buildAndExpand(message)
                                      .toUri();

        client.get()
              .uri(uri)
              .retrieve()
              .bodyToMono(JsonNode.class)
              .doOnError(this::logNotificationFailed)
              .block();
    }

    private void logNotificationFailed(Throwable e) {
        log.warn(String.format("Telegram notification failed. Cause: %s (%s)", e.getCause(), e.getMessage()));
    }

}
