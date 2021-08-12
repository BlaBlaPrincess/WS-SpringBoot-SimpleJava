package com.blablaprincess.springboot_simplejava.rest.controllers;

import com.blablaprincess.springboot_simplejava.rest.actions.ControllerCallsHistoryAction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Execution(ExecutionMode.SAME_THREAD)
@WebMvcTest(ControllerCallsHistoryController.class)
class ControllerCallsHistoryControllerMvcIT {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ControllerCallsHistoryAction controllerCallsHistoryAction;

    @DisplayName("GET /calls")
    @ParameterizedTest(name = "/calls{0}")
    @MethodSource("getIndexCases")
    void getControllerCalls(String urlParams, Integer expectedAmount) throws Exception {
        // Arrange
        String urlBase = "/calls";

        // Act
        mvc.perform(MockMvcRequestBuilders.get(urlBase + urlParams))
                // Assert
                .andExpect(status().isOk());

        verify(controllerCallsHistoryAction).getCalls(eq(expectedAmount));
    }

    @DisplayName("GET /calls/from")
    @ParameterizedTest(name = "/calls/from{0}")
    @MethodSource("getFromCases")
    void getControllerCalls
            (String urlParams, Integer expectedAmount, Duration expectedDuration, LocalDateTime expectedFrom) throws Exception {
        // Arrange
        String urlBase = "/calls/from";

        // Act
        mvc.perform(MockMvcRequestBuilders.get(urlBase + urlParams))
                // Assert
                .andExpect(status().isOk());

        verify(controllerCallsHistoryAction).getCalls(eq(expectedAmount), eq(expectedDuration), eq(expectedFrom));
    }

    @DisplayName("GET /calls/between")
    @ParameterizedTest(name = "/calls/between{0}")
    @MethodSource("getBetweenCases")
    void getControllerCalls
            (String urlParams, Integer expectedAmount, LocalDateTime expectedFrom, LocalDateTime expectedTo) throws Exception {
        // Arrange
        String urlBase = "/calls/between";

        // Act
        mvc.perform(MockMvcRequestBuilders.get(urlBase + urlParams))
                // Assert
                .andExpect(status().isOk());

        verify(controllerCallsHistoryAction).getCalls(eq(expectedAmount), eq(expectedFrom), eq(expectedTo));
    }

    static Stream<Arguments> getIndexCases() {
        return Stream.of(
                arguments("",            null),
                arguments("?amount=100", 100)
        );
    }

    static Stream<Arguments> getFromCases() {
        return Stream.of(
                arguments("?offset=PT1S",           null, Duration.parse("PT1S"),         null),
                arguments("?offset=PT-10H",         null, Duration.parse("PT-10H"),       null),
                arguments("?offset=P1000DT10H1S",   null, Duration.parse("P1000DT10H1S"), null),
                arguments("?amount=10&offset=PT1S", 10,   Duration.parse("PT1S"),         null),
                arguments("?amount=10&offset=PT1S&from=2021-01-01 12:00:00",
                        10, Duration.parse("PT1S"), LocalDateTime.parse("2021-01-01T12:00"))
        );
    }

    static Stream<Arguments> getBetweenCases() {
        return Stream.of(
                arguments("?from=2021-01-01 12:00:00",
                        null, LocalDateTime.parse("2021-01-01T12:00"), null),
                arguments("?from=2021-01-01 12:00:00&to=2021-11-11 12:00:00",
                        null, LocalDateTime.parse("2021-01-01T12:00"), LocalDateTime.parse("2021-11-11T12:00")),
                arguments("?amount=10&from=2021-01-01 12:00:00&to=2021-11-11 12:00:00",
                        10, LocalDateTime.parse("2021-01-01T12:00"), LocalDateTime.parse("2021-11-11T12:00"))
        );
    }

}