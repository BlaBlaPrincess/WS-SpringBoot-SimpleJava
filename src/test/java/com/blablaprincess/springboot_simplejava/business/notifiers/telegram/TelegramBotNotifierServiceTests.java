package com.blablaprincess.springboot_simplejava.business.notifiers.telegram;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

class TelegramBotNotifierServiceTests {

    @Test
    @SuppressWarnings("Convert2Lambda")
    void sendNotification() {
        // Arrange
        String message = "123abc\n!@#$%^&*()-+=\n\uD83D\uDC35\uD83D\uDE48\uD83D\uDE49️";
        URI expectedUri = UriComponentsBuilder
                .fromHttpUrl("https://api.telegram.org/bottoken/sendMessage?chat_id=chatId&text={message}")
                .buildAndExpand(message)
                .toUri();

        ExchangeFunction exchangeFunction = new ExchangeFunction() {
            public Mono<ClientResponse> exchange(ClientRequest request) {
                URI actualUri = request.url();
                assertEquals(expectedUri, actualUri);
                return Mono.just(ClientResponse.create(HttpStatus.OK).build());
            }
        };
        ExchangeFunction spyExchangeFunction = spy(exchangeFunction);

        WebClient webClient = WebClient.builder()
                .exchangeFunction(spyExchangeFunction)
                .build();

        TelegramBotNotifierService service = new TelegramBotNotifierService("token", "chatId", webClient);

        // Act + Assert
        service.sendNotification(message);
        verify(spyExchangeFunction).exchange(any());
    }

}