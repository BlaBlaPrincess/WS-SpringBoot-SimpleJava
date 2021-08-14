package com.blablaprincess.springboot_simplejava.business.notifiers.telegram;

import com.blablaprincess.springboot_simplejava.business.notifiers.Notifier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramBotNotifierService implements Notifier {

    @Value("https://api.telegram.org/bot${telegram-bot.http-token}/sendMessage?chat_id=${telegram-bot.chat-id}&text={message}")
    private final String messageHttpRequestTemplate;
    private final WebClient client;

    @Override
    public void sendNotification(String message) {
        URI uri = UriComponentsBuilder.fromHttpUrl(messageHttpRequestTemplate)
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
