package com.blablaprincess.springboot_simplejava.rest.controllers;

import com.blablaprincess.springboot_simplejava.business.common.datetime.DateTime;
import com.blablaprincess.springboot_simplejava.business.controllercalls.ControllerCallsHistory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Execution(ExecutionMode.SAME_THREAD)
@WebMvcTest(ControllerCallsHistoryController.class)
class ControllerCallsHistoryControllerMvcIT {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ControllerCallsHistory controllerCallsHistoryService;

    @MockBean
    private DateTime dateTime;

    @Test
    void getControllerCalls() throws Exception {
        // Act
        mvc.perform(MockMvcRequestBuilders.get("/calls"))
                // Assert
                .andExpect(status().isOk());

        verify(controllerCallsHistoryService, times(1)).getCalls();
    }

    @Test
    void getControllerCallsByAmount() throws Exception {
        // Arrange
        int amount = 100;
        String url = "/calls?amount=" + amount;

        // Act
        mvc.perform(MockMvcRequestBuilders.get(url))
                // Assert
                .andExpect(status().isOk());

        verify(controllerCallsHistoryService, times(1)).getLastCalls(amount);
    }

    @DisplayName("getControllerCallsByTime()")
    @ParameterizedTest(name = "{0}")
    @MethodSource("requestGetControllerCallsByTimeCases")
    void getControllerCallsByTime(String url) throws Exception {
        // Arrange
        Date date = new Date();
        when(dateTime.getDate()).thenReturn(date);
        when(dateTime.add(any(Date.class), any(DateTime.DateField.class), anyInt())).thenReturn(date);

        // Act
        mvc.perform(MockMvcRequestBuilders.get(url))
                // Assert
                .andExpect(status().isOk());

        verify(controllerCallsHistoryService, times(1)).getCalls(any(Date.class), any(Date.class));
    }

    static Stream<String> requestGetControllerCallsByTimeCases() {
        return Stream.of(
                "/calls?days=1", "/calls?hours=1", "/calls?minutes=1",
                "/calls?days=1&hours=1", "/calls?days=1&minutes=1", "/calls?hours=1&minutes=1",
                "/calls?days=1&hours=1&minutes=1"
        );
    }

    @DisplayName("getControllerCallsByAmountAndTime()")
    @ParameterizedTest(name = "{0}")
    @MethodSource("getControllerCallsByAmountAndTimeCases")
    void getControllerCallsByAmountAndTime(String url) throws Exception {
        // Arrange
        Date date = new Date();
        when(dateTime.getDate()).thenReturn(date);
        when(dateTime.add(any(Date.class), any(DateTime.DateField.class), anyInt())).thenReturn(date);

        // Act
        mvc.perform(MockMvcRequestBuilders.get(url))
                // Assert
                .andExpect(status().isOk());

        verify(controllerCallsHistoryService, times(1)).getLastCalls(any(Date.class), any(Date.class), anyInt());
    }

    static Stream<String> getControllerCallsByAmountAndTimeCases() {
        return requestGetControllerCallsByTimeCases()
                .map(url -> url + "&amount=1");
    }

}