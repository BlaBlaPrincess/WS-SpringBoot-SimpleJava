package com.blablaprincess.springboot_simplejava.rest.actions;

import com.blablaprincess.springboot_simplejava.business.common.utils.LocalDateTimeUtils;
import com.blablaprincess.springboot_simplejava.business.controllercalls.ControllerCallsHistory;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ControllerCallsHistoryActionTests {

    private final ControllerCallsHistory controllerCallsHistory = mock(ControllerCallsHistory.class);
    private final LocalDateTimeUtils localDateTimeUtils = mock(LocalDateTimeUtils.class);
    private final ControllerCallsHistoryAction controllerCallsHistoryAction =
            new ControllerCallsHistoryAction(controllerCallsHistory, localDateTimeUtils);

    private final Integer amount = 10;
    private final LocalDateTime from = LocalDateTime.parse("2010-01-01T12:00");
    private final LocalDateTime to   = LocalDateTime.parse("2020-01-01T12:00");
    private final OffsetDateTime utcFrom = OffsetDateTime.of(from, ZoneOffset.UTC);
    private final OffsetDateTime utcTo   = OffsetDateTime.of(to,   ZoneOffset.UTC);
    private final Duration offset = Duration.parse("PT1H");

    @Test
    void getCalls() {
        // Act
        controllerCallsHistoryAction.getCalls(null);

        // Assert
        verify(controllerCallsHistory).getCalls();
    }

    @Test
    void getCallsByAmount() {
        // Act
        controllerCallsHistoryAction.getCalls(amount);

        // Assert
        verify(controllerCallsHistory).getLastCalls(eq(amount));
    }

    @Test
    void getCallsByOffset() {
        // Arrange
        when(localDateTimeUtils.now()).thenReturn(from);
        when(localDateTimeUtils.plus(from, offset)).thenReturn(to);

        // Act
        controllerCallsHistoryAction.getCalls(null, offset, null);

        // Assert
        verify(controllerCallsHistory).getCalls(eq(utcFrom), eq(utcTo));
    }

    @Test
    void getCallsByOffsetFrom() {
        // Arrange
        when(localDateTimeUtils.plus(from, offset)).thenReturn(to);

        // Act
        controllerCallsHistoryAction.getCalls(null, offset, from);

        // Assert
        verify(controllerCallsHistory).getCalls(eq(utcFrom), eq(utcTo));
    }

    @Test
    void getCallsByAmountOffsetFrom() {
        // Arrange
        when(localDateTimeUtils.plus(from, offset)).thenReturn(to);

        // Act
        controllerCallsHistoryAction.getCalls(amount, offset, from);

        // Assert
        verify(controllerCallsHistory).getLastCalls(eq(utcFrom), eq(utcTo), eq(amount));
    }

    @Test
    void getCallsByFrom() {
        // Arrange
        when(localDateTimeUtils.now()).thenReturn(to);

        // Act
        controllerCallsHistoryAction.getCalls(null, from, null);

        // Assert
        verify(controllerCallsHistory).getCalls(eq(utcFrom), eq(utcTo));
    }

    @Test
    void getCallsByFromTo() {
        // Act
        controllerCallsHistoryAction.getCalls(null, from, to);

        // Assert
        verify(controllerCallsHistory).getCalls(eq(utcFrom), eq(utcTo));
    }

    @Test
    void getCallsByAmountFromTo() {
        // Act
        controllerCallsHistoryAction.getCalls(amount, from, to);

        // Assert
        verify(controllerCallsHistory).getLastCalls(eq(utcFrom), eq(utcTo), eq(amount));
    }

}