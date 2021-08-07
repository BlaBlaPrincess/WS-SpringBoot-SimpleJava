package com.blablaprincess.springboot_simplejava.rest.controllers;

import com.blablaprincess.springboot_simplejava.business.controllercalls.ControllerCallsHistoryAfterDispatchingProcessor;
import com.blablaprincess.springboot_simplejava.business.notifiers.telegram.TelegramBotNotifierAfterDispatchingProcessor;
import com.blablaprincess.springboot_simplejava.rest.actions.IntegersCountingAlgorithmsPresenterAction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Execution(ExecutionMode.SAME_THREAD)
@WebMvcTest(CountsController.class)
class CountControllerMvcIT {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private IntegersCountingAlgorithmsPresenterAction integersCountingAlgorithmsPresenterAction;

    @MockBean
    private TelegramBotNotifierAfterDispatchingProcessor telegramBotNotifierAfterDispatchingProcessor;

    @MockBean
    private ControllerCallsHistoryAfterDispatchingProcessor controllerCallsHistoryAfterDispatchingProcessor;

    @DisplayName("GET /counts/int")
    @Test
    void getAlgorithms() throws Exception {
        // Arrange
        String url = "/counts/int";
        Mockito.when(integersCountingAlgorithmsPresenterAction.getAlgorithms()).thenReturn(null);

        // Act
        mvc.perform(MockMvcRequestBuilders.get(url))
                // Assert
                .andExpect(status().isOk());

        verify(integersCountingAlgorithmsPresenterAction, times(1)).getAlgorithms();

        verify(telegramBotNotifierAfterDispatchingProcessor, times(1)).process(any(), any(), any());
        verify(controllerCallsHistoryAfterDispatchingProcessor, times(1)).process(any(), any(), any());
    }

    @DisplayName("GET algorithms counts")
    @ParameterizedTest(name = "/counts/int/{0}")
    @MethodSource("getAlgorithmsCountsCases")
    void getAlgorithmsCounts(Integer param) throws Exception {
        // Arrange
        String url = "/counts/int/" + param;
        Mockito.when(integersCountingAlgorithmsPresenterAction.getAlgorithmsCounts(param)).thenReturn(null);

        // Act
        mvc.perform(MockMvcRequestBuilders.get(url))
                // Assert
                .andExpect(status().isOk());

        verify(integersCountingAlgorithmsPresenterAction, times(1)).getAlgorithmsCounts(param);

        verify(telegramBotNotifierAfterDispatchingProcessor, times(1)).process(any(), any(), any());
        verify(controllerCallsHistoryAfterDispatchingProcessor, times(1)).process(any(), any(), any());
    }

    private static Stream<Integer> getAlgorithmsCountsCases() {
        return Stream.of(100, -10);
    }

    @DisplayName("GET redirect")
    @ParameterizedTest(name = "/counts/integer{0}")
    @MethodSource("redirectCountsForIntegerToIntCases")
    void redirectCountsForIntegerToInt(String subUrl) throws Exception {
        // Arrange
        String from = "/counts/integer" + subUrl;
        String to =   "/counts/int"     + subUrl;

        // Act
        mvc.perform(MockMvcRequestBuilders.get(from))
                // Assert
                .andExpect(status().isPermanentRedirect())
                .andExpect(redirectedUrl(to));

        verify(telegramBotNotifierAfterDispatchingProcessor, times(1)).process(any(), any(), any());
        verify(controllerCallsHistoryAfterDispatchingProcessor, times(1)).process(any(), any(), any());
    }

    private static Stream<String> redirectCountsForIntegerToIntCases() {
        return Stream.of("", "/any", "/a/a");
    }

}
