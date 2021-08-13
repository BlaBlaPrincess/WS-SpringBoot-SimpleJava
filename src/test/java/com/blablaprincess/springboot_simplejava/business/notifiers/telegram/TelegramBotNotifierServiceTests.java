package com.blablaprincess.springboot_simplejava.business.notifiers.telegram;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TelegramBotNotifierServiceTests {

    private final TelegramBotNotifierService service;
    private final ExchangeFunction exchangeFunction = mock(ExchangeFunction.class);

    TelegramBotNotifierServiceTests() {
        WebClient webClient = WebClient.builder()
                .exchangeFunction(exchangeFunction)
                .build();

        service = new TelegramBotNotifierService("token", "chatId", webClient);
    }

    @Test
    void sendNotification() {
        // Arrange
        String message = "message";
        when(exchangeFunction.exchange(any(ClientRequest.class)))
                .thenReturn(Mono.just(ClientResponse.create(HttpStatus.OK).build()));

        // Act
        service.sendNotification(message);

        // Assert
        verify(exchangeFunction).exchange(any());
    }

}