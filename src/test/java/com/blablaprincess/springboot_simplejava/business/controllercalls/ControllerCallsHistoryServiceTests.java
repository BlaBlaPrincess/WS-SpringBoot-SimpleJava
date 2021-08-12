package com.blablaprincess.springboot_simplejava.business.controllercalls;

import com.blablaprincess.springboot_simplejava.business.common.utils.StringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SuppressWarnings("unchecked")
class ControllerCallsHistoryServiceTests {

    private final ControllerCallDescriptionsRepository repository = mock(ControllerCallDescriptionsRepository.class);
    private final StringUtils stringUtils = mock(StringUtils.class);
    private final ControllerCallsHistoryService service = new ControllerCallsHistoryService(repository, stringUtils);

    private static final List<ControllerCallDescriptionEntity> expected = (List<ControllerCallDescriptionEntity>) mock(List.class);
    private static final Page<ControllerCallDescriptionEntity> expectedPage = (Page<ControllerCallDescriptionEntity>) mock(Page.class);

    @BeforeAll
    static void setup() {
        when(expectedPage.toList()).thenReturn(expected);
    }

    @Test
    void saveCall() {
        // Arrange
        ControllerCallDescriptionEntity entity = new ControllerCallDescriptionEntity();

        // Act
        service.saveCall(entity);

        // Arrange
        verify(stringUtils).cropByMaxLength(entity.getResponse(), ControllerCallDescriptionEntity.MAX_RESPONSE_LENGTH);
        verify(repository).save(entity);
    }

    @Test
    void getCalls() {
        // Arrange
        when(service.getCalls()).thenReturn(expected);

        // Act
        List<ControllerCallDescriptionEntity> result = service.getCalls();

        // Assert
        assertEquals(expected, result);
        verify(repository).findAll();
    }

    @Test
    void getCallsByDate() {
        // Arrange
        OffsetDateTime from = OffsetDateTime.of(LocalDateTime.of(2000, 1, 1, 0, 0), ZoneOffset.UTC);
        OffsetDateTime to   = OffsetDateTime.of(LocalDateTime.of(2010, 1, 1, 0, 0), ZoneOffset.UTC);
        when(repository.findByTimestampIsBetween(any(OffsetDateTime.class), any(OffsetDateTime.class)))
                .thenReturn(expected);

        // Act
        List<ControllerCallDescriptionEntity> result = service.getCalls(from, to);

        // Assert
        assertEquals(expected, result);
        verify(repository).findByTimestampIsBetween(eq(from), eq(to));
    }

    @Test
    void getLastCalls() {
        // Arrange
        when(repository.findAll(any(Pageable.class))).thenReturn(expectedPage);

        // Act
        List<ControllerCallDescriptionEntity> result = service.getLastCalls(100);

        // Assert
        assertEquals(expected, result);
        verify(repository).findAll(any(Pageable.class));
    }

    @Test
    void getLastCallsByDate() {
        // Arrange
        OffsetDateTime from = OffsetDateTime.of(LocalDateTime.of(2000, 1, 1, 0, 0), ZoneOffset.UTC);
        OffsetDateTime to   = OffsetDateTime.of(LocalDateTime.of(2010, 1, 1, 0, 0), ZoneOffset.UTC);
        when(repository.findByTimestampIsBetween(any(OffsetDateTime.class), any(OffsetDateTime.class), any(Pageable.class)))
                .thenReturn(expectedPage);

        // Act
        List<ControllerCallDescriptionEntity> result = service.getLastCalls(from, to, 100);

        // Assert
        assertEquals(expected, result);
        verify(repository).findByTimestampIsBetween(eq(from), eq(to), any(Pageable.class));
    }

}