package com.blablaprincess.springboot_simplejava.business.notifiers.telegram;

import com.blablaprincess.springboot_simplejava.business.notifiers.Notifier;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Service
public class TelegramBotNotifierService implements Notifier {

    private final String sendMessageTemplate;

    private final WebClient client;

    public TelegramBotNotifierService(@Value("${telegram-bot.http-token}") String httpToken,
                                      @Value("${telegram-bot.chat-id}") String chatId,
                                      WebClient client) {
        this.client = client;
        sendMessageTemplate = String.format("https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text={message}", httpToken, chatId);
    }

    @Override
    public void sendNotification(String message) {
        URI uri = UriComponentsBuilder.fromHttpUrl(sendMessageTemplate)
                                      .buildAndExpand(message)
                                      .toUri();

        client.get()
              .uri(uri)
              .retrieve()
              .toBodilessEntity()
              .doOnError(this::logNotificationFailed)
              .block();
    }

    private void logNotificationFailed(Throwable throwable) {
        log.error("Telegram notification failed.", throwable);
    }

}
