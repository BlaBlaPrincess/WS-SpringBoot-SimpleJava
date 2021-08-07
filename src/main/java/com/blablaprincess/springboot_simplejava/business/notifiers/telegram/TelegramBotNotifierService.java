package com.blablaprincess.springboot_simplejava.business.notifiers.telegram;

import com.blablaprincess.springboot_simplejava.business.notifiers.Notifier;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriTemplate;

import java.net.URI;

@Slf4j
@Service
public class TelegramBotNotifierService implements Notifier {

    public final String HTTP_TOKEN;
    public final String CHAT_ID;

    private final String SEND_MESSAGE_TEMPLATE;

    public TelegramBotNotifierService(Environment environment) {
        HTTP_TOKEN = environment.getRequiredProperty("telegram-bot.http-token");
        CHAT_ID = environment.getRequiredProperty("telegram-bot.chat-id");

        SEND_MESSAGE_TEMPLATE = new UriTemplate("https://api.telegram.org/bot{token}/sendMessage")
                .expand(HTTP_TOKEN)
                .toString();
    }

    @Override
    @Async("telegramBotNotifierServiceTaskExecutor")
    public void sendNotification(String message) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {

            URI uri = new URIBuilder(SEND_MESSAGE_TEMPLATE)
                    .addParameter("chat_id", CHAT_ID)
                    .addParameter("text", message)
                    .build();

            client.execute(new HttpGet(uri));

        } catch (Exception e) {
            log.warn(String.format("Telegram notification failed. Cause: %s (%s)", e.getCause(), e.getMessage()));
        }
    }

}
