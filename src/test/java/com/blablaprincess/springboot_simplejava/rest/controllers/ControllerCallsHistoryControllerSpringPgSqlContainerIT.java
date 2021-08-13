package com.blablaprincess.springboot_simplejava.rest.controllers;

import com.blablaprincess.springboot_simplejava.business.controllercalls.ControllerCallDescriptionEntity;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.github.database.rider.core.api.configuration.Orthography.LOWERCASE;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Execution(ExecutionMode.SAME_THREAD)
@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
@DBRider
@DBUnit(cacheConnection = false, leakHunter = true, caseInsensitiveStrategy = LOWERCASE)
@RequiredArgsConstructor
class ControllerCallsHistoryControllerSpringPgSqlContainerIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @DisplayName("GET /calls")
    @ParameterizedTest(name = "/calls{0}")
    @MethodSource("getCallsCases")
    @DataSet(value = "ControllerCallDescriptionEntity.yml")
    void getControllerCalls(String urlParams, List<String> expected) throws Exception {
        // Arrange
        String urlBase = "/calls";

        // Act
        String responseBody = mvc.perform(MockMvcRequestBuilders.get(urlBase + urlParams))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<ControllerCallDescriptionEntity> response =
                mapper.readValue(responseBody, new TypeReference<List<ControllerCallDescriptionEntity>>() {
                });

        List<String> responseValues = response.stream()
                .map(ControllerCallDescriptionEntity::getResponse)
                .collect(Collectors.toList());

        // Assert
        assertEquals(expected, responseValues);
    }

    @SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
    static Stream<Arguments> getCallsCases() {
        return Stream.of(
                arguments("",
                        asList("response 0", "response 1")),
                arguments(params(after("00:30")),
                        asList("response 1")),
                arguments(params(before("00:30")),
                        asList("response 0")),
                arguments(params(after("00:00"), before("01:00")),
                        asList("response 0", "response 1")),
                arguments(params(after("01:00"), before("00:00")),
                        asList())
        );
    }

    private static String after(String time) {
        return "timestampAfter=2021-01-01 " + time;
    }

    private static String before(String time) {
        return "timestampBefore=2021-01-01 " + time;
    }

    private static String params(String... params) {
        return "?" + String.join("&", params);
    }

}