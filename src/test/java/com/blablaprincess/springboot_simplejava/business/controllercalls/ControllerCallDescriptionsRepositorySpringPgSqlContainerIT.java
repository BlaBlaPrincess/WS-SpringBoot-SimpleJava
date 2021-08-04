package com.blablaprincess.springboot_simplejava.business.controllercalls;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.github.database.rider.core.api.configuration.Orthography.LOWERCASE;
import static org.junit.jupiter.api.Assertions.*;

@Execution(ExecutionMode.SAME_THREAD)
@Testcontainers
@SpringBootTest
@DBRider
@DBUnit(cacheConnection = false, leakHunter = true, caseInsensitiveStrategy = LOWERCASE)
class ControllerCallDescriptionsRepositorySpringPgSqlContainerIT {

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
    private ControllerCallDescriptionsRepository repository;

    private final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    @BeforeEach
    @DataSet(value = "ControllerCallDescriptionEntity.yml")
    void setupDataset() {
    }

    @Test
    void postgreSqlContainerIsRunning() {
        assertTrue(postgreSqlContainer.isRunning());
    }

    @Test
    void findByTimestampIsBetween() throws ParseException {
        // Arrange
        Date from     = format.parse("01/01/2021 00:00:30");
        Date to       = format.parse("01/01/2021 00:01:30");
        Date expected = format.parse("01/01/2021 00:01:00");

        // Act
        List<ControllerCallDescriptionEntity> result = repository.findByTimestampIsBetween(from, to);

        // Assert
        assertEquals(1, result.size());
        assertEquals(expected, result.get(0).getTimestamp());
    }

    @Test
    void findByTimestampIsBetweenWithReversedParams() throws ParseException {
        // Arrange
        Date from = format.parse("01/01/2021 00:00:30");
        Date to   = format.parse("01/01/2021 00:01:30");

        // Act
        List<ControllerCallDescriptionEntity> result = repository.findByTimestampIsBetween(to, from);

        // Assert
        assertEquals(0, result.size());
    }

}