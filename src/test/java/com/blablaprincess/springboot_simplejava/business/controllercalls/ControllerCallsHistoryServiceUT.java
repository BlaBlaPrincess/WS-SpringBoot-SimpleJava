package com.blablaprincess.springboot_simplejava.business.controllercalls;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Date;

import static org.mockito.Mockito.*;

class ControllerCallsHistoryServiceUT {

    private final ControllerCallDescriptionsRepository repository = mock(ControllerCallDescriptionsRepository.class);
    private final ControllerCallsHistoryService service = new ControllerCallsHistoryService(repository);

    @Test
    void saveCall() {
        // Arrange
        ControllerCallDescriptionEntity entity = new ControllerCallDescriptionEntity();

        // Act
        service.saveCall(entity);

        // Arrange
        verify(repository, times(1)).save(entity);
    }

    @Test
    void getCalls() {
        // Act
        service.getCalls();

        // Assert
        verify(repository, times(1)).findAll();
    }

    @Test
    void getCallsByDate() {
        // Arrange
        when(repository.findByTimestampIsBetween(any(Date.class), any(Date.class)))
                .thenReturn(new ArrayList<>());

        // Act
        service.getCalls(new Date(), new Date());

        // Assert
        verify(repository, times(1))
                .findByTimestampIsBetween(any(Date.class), any(Date.class));
    }

    @Test
    void getLastCalls() {
        // Arrange
        when(repository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(new ArrayList<>()));

        // Act
        service.getLastCalls(100);

        // Assert
        verify(repository, times(1))
                .findAll(any(Pageable.class));
    }

    @Test
    void getLastCallsByDate() {
        // Arrange
        when(repository.findByTimestampIsBetween(any(Date.class), any(Date.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(new ArrayList<>()));

        // Act
        service.getLastCalls(new Date(), new Date(), 100);

        // Assert
        verify(repository, times(1))
                .findByTimestampIsBetween(any(Date.class), any(Date.class), any(Pageable.class));
    }

}