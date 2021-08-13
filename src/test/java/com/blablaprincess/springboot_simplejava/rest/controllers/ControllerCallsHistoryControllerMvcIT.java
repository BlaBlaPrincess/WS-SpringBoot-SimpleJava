package com.blablaprincess.springboot_simplejava.rest.controllers;

import com.blablaprincess.springboot_simplejava.business.controllercalls.ControllerCallsHistoryLastCallsArgs;
import com.blablaprincess.springboot_simplejava.business.controllercalls.ControllerCallsHistoryService;
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
    private ControllerCallsHistoryService controllerCallsHistoryService;

    @DisplayName("GET /calls")
    @ParameterizedTest(name = "/calls{0}")
    @MethodSource("getCallsCases")
    void getControllerCalls(String urlParams, ControllerCallsHistoryLastCallsArgs expected) throws Exception {
        // Arrange
        String urlBase = "/calls";

        // Act
        mvc.perform(MockMvcRequestBuilders.get(urlBase + urlParams))
                // Assert
                .andExpect(status().isOk());

        verify(controllerCallsHistoryService).getLastCalls(eq(expected));
    }

    static Stream<Arguments> getCallsCases() {
        LocalDateTime to   = LocalDateTime.parse("2021-01-01T12:00");
        LocalDateTime from = LocalDateTime.parse("2011-01-01T12:00");
        return Stream.of(
                arguments("",
                        argsBuilder().build()),
                arguments("?timestampBefore=2021-01-01 12:00",
                        argsBuilder().timestampBefore(to).build()),
                arguments("?timestampAfter=2011-01-01 12:00",
                        argsBuilder().timestampAfter(from).build()),
                arguments("?timestampBefore=2021-01-01 12:00&timestampAfter=2011-01-01 12:00",
                        argsBuilder().timestampBefore(to).timestampAfter(from).build())
        );
    }

    private static ControllerCallsHistoryLastCallsArgs.ControllerCallsHistoryLastCallsArgsBuilder argsBuilder() {
        return ControllerCallsHistoryLastCallsArgs.builder();
    }

}