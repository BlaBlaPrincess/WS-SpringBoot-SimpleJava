package com.blablaprincess.springboot_simplejava.business.controllercalls;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.github.database.rider.core.api.configuration.Orthography.LOWERCASE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@Execution(ExecutionMode.SAME_THREAD)
@Testcontainers
@SpringBootTest
@DBRider
@DBUnit(cacheConnection = false, leakHunter = true, caseInsensitiveStrategy = LOWERCASE)
class ControllerCallsHistoryServiceSpringPgSqlContainerIT {

    @SuppressWarnings("rawtypes")
    @Container
    final static PostgreSQLContainer postgreSqlContainer = new PostgreSQLContainer("postgres:11.1")
            .withDatabaseName("database")
            .withUsername("username")
            .withPassword("password");

    @DynamicPropertySource
    static void getProperties(DynamicPropertyRegistry propertyRegistry) {
        propertyRegistry.add("spring.datasource.url", postgreSqlContainer::getJdbcUrl);
        propertyRegistry.add("spring.datasource.username", postgreSqlContainer::getUsername);
        propertyRegistry.add("spring.datasource.password", postgreSqlContainer::getPassword);
    }

    @Autowired
    private ControllerCallsHistory service;

    @BeforeEach
    @DataSet(value = "ControllerCallDescriptionEntity.yml")
    void setupDataset() {
    }

    @Test
    void postgreSqlContainerIsRunning() {
        assertTrue(postgreSqlContainer.isRunning());
    }

    @DisplayName("getLastCalls()")
    @ParameterizedTest(name = "{0}")
    @MethodSource("getLastCallsCases")
    void getLastCalls(String description, ControllerCallsHistoryLastCallsArgs args, List<String> expected) {
        // Act
        List<ControllerCallDescriptionEntity> result = service.getLastCalls(args);
        List<String> resultResponses = result.stream()
                                             .map(ControllerCallDescriptionEntity::getResponse)
                                             .collect(Collectors.toList());

        // Assert
        assertEquals(expected, resultResponses);
    }

    @SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
    static Stream<Arguments> getLastCallsCases() {
        return Stream.of(
                arguments("find all",
                        argsBuilder().build(),
                        Arrays.asList("response 0", "response 1")),
                arguments("find after",
                        argsBuilder().timestampAfter(time("00:30")).build(),
                        Arrays.asList("response 1")),
                arguments("find before",
                        argsBuilder().timestampBefore(time("00:30")).build(),
                        Arrays.asList("response 0")),
                arguments("find between",
                        argsBuilder().timestampAfter(time("00:00")).timestampBefore(time("01:00")).build(),
                        Arrays.asList("response 0", "response 1")),
                arguments("find between reversed",
                        argsBuilder().timestampAfter(time("01:00")).timestampBefore(time("00:00")).build(),
                        Arrays.asList())
        );
    }

    private static ControllerCallsHistoryLastCallsArgs.ControllerCallsHistoryLastCallsArgsBuilder argsBuilder() {
        return ControllerCallsHistoryLastCallsArgs.builder();
    }

    private static LocalDateTime time(String time) {
        return LocalDateTime.parse("2021-01-01T" + time);
    }

}