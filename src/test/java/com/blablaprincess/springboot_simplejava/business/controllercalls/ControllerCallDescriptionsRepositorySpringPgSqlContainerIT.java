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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.OffsetDateTime;
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

    @BeforeEach
    @DataSet(value = "ControllerCallDescriptionEntity.yml")
    void setupDataset() {
    }

    @Test
    void postgreSqlContainerIsRunning() {
        assertTrue(postgreSqlContainer.isRunning());
    }

    @Test
    void findByTimestampIsBetween() {
        // Arrange
        OffsetDateTime from     = OffsetDateTime.parse("2021-01-01T00:00:30Z");
        OffsetDateTime to       = OffsetDateTime.parse("2021-01-01T00:01:30Z");
        OffsetDateTime expected = OffsetDateTime.parse("2021-01-01T00:01:00Z");

        // Act
        List<ControllerCallDescriptionEntity> result = repository.findByTimestampIsBetween(from, to);

        // Assert
        assertEquals(1, result.size());
        assertTrue(expected.isEqual(result.get(0).getTimestamp()));
    }

    @Test
    void findByTimestampIsBetweenWithReversedParams() {
        // Arrange
        OffsetDateTime from = OffsetDateTime.parse("2021-01-01T00:00:30Z");
        OffsetDateTime to   = OffsetDateTime.parse("2021-01-01T00:01:30Z");

        // Act
        List<ControllerCallDescriptionEntity> result = repository.findByTimestampIsBetween(to, from);

        // Assert
        assertEquals(0, result.size());
    }

    @Test
    void findPageByTimestampIsBetween() {
        // Arrange
        OffsetDateTime from     = OffsetDateTime.parse("2021-01-01T00:00:00Z");
        OffsetDateTime to       = OffsetDateTime.parse("2021-01-01T00:02:00Z");
        OffsetDateTime expected = OffsetDateTime.parse("2021-01-01T00:00:00Z");
        Pageable request = PageRequest.of(0, 1, Sort.by("timestamp"));

        // Act
        Page<ControllerCallDescriptionEntity> page = repository.findByTimestampIsBetween(from, to, request);
        List<ControllerCallDescriptionEntity> result = page.toList();

        // Assert
        assertEquals(1, result.size());
        assertTrue(expected.isEqual(result.get(0).getTimestamp()));
    }

}