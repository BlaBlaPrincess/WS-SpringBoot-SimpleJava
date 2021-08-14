package com.blablaprincess.springboot_simplejava.business.notifiers.telegram.adp;

import com.blablaprincess.springboot_simplejava.business.notifiers.telegram.TelegramBotNotifierService;
import com.blablaprincess.springboot_simplejava.rest.afterdispatchingprocessor.AfterDispatchingProcessor;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "telegram-bot.adp.enabled", havingValue = "true")
public class TelegramBotNotifierAfterDispatchingProcessor implements AfterDispatchingProcessor {

    private final TelegramBotNotifierService notifierService;
    private final ObjectMapper mapper;

    @Override
    @SneakyThrows
    public void process(String responseBody, HttpServletRequest request, HttpServletResponse response) {
        String responseStatusIndicator;
        HttpStatus.Series responseStatusSeries = HttpStatus.Series.resolve(response.getStatus());
        if (responseStatusSeries != null) {
            switch (responseStatusSeries) {
                case INFORMATIONAL:
                    responseStatusIndicator = "\uD83D\uDD35";
                    break;
                case SUCCESSFUL:
                    responseStatusIndicator = "\uD83D\uDFE2";
                    break;
                case REDIRECTION:
                    responseStatusIndicator = "\uD83D\uDFE3";
                    break;
                default:
                    responseStatusIndicator = "\uD83D\uDD34";
                    break;
            }
        } else {
            responseStatusIndicator = "\uD83D\uDFE0";
        }
        JsonNode responseBodyJsonNode = mapper.readTree(responseBody);
        String message = "App has handled the request:\n" +
                "▫️ Mapping: " + request.getRequestURI() + "\n" +
                "▫️ Type: " + request.getMethod() + "\n" +
                "With a response:\n" +
                "▫️ Status: " + response.getStatus() + " " + responseStatusIndicator + "\n" +
                (responseBodyJsonNode.isEmpty() ?
                        "▫️ Body was empty" :
                        "▫️ Body:\n" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseBodyJsonNode));
        notifierService.sendNotification(message);
    }

}
