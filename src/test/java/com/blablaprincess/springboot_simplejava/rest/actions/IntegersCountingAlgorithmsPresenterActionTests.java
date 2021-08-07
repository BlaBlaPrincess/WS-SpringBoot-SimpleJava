package com.blablaprincess.springboot_simplejava.rest.actions;

import com.blablaprincess.springboot_simplejava.business.arraycounting.presenters.ArrayCountingAlgorithmsPresenter;
import com.blablaprincess.springboot_simplejava.business.arraycounting.presenters.ArrayCountingAlgorithmsPresenterDto;
import com.blablaprincess.springboot_simplejava.business.validation.ValidatorsCollection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.*;

class IntegersCountingAlgorithmsPresenterActionTests {

    @SuppressWarnings("unchecked")
    private final ArrayCountingAlgorithmsPresenter<Integer> presenter =
            (ArrayCountingAlgorithmsPresenter<Integer>) mock(ArrayCountingAlgorithmsPresenter.class);

    @SuppressWarnings("unchecked")
    private final ValidatorsCollection<Integer> validators =
            (ValidatorsCollection<Integer>) mock(ValidatorsCollection.class);

    private final IntegersCountingAlgorithmsPresenterAction integersCountingAlgorithmsPresenterAction
            = new IntegersCountingAlgorithmsPresenterAction(presenter, validators);

    @Test
    void getAlgorithms() {
        // Arrange
        String[] expected = new String[]{};
        when(presenter.getAlgorithms()).thenReturn(expected);

        // Act
        String[] result = integersCountingAlgorithmsPresenterAction.getAlgorithms();

        // Assert
        assertEquals(expected, result);
        verify(presenter, times(1)).getAlgorithms();
    }

    @DisplayName("getAlgorithmsCounts()")
    @ParameterizedTest(name = "{0}")
    @MethodSource("getAlgorithmsCountsCases")
    void getAlgorithmsCounts(int param, Integer[] parsedParam) {
        // Arrange
        ArrayCountingAlgorithmsPresenterDto expected = new ArrayCountingAlgorithmsPresenterDto();
        when(presenter.getAlgorithmsCounts(parsedParam)).thenReturn(expected);

        // Act
        ArrayCountingAlgorithmsPresenterDto result = integersCountingAlgorithmsPresenterAction.getAlgorithmsCounts(param);

        // Assert
        assertEquals(expected, result);
        verify(validators, times(1)).validate(anyInt());
        verify(presenter,  times(1)).getAlgorithmsCounts(parsedParam);
    }

    private static Stream<Arguments> getAlgorithmsCountsCases() {
        return Stream.of(
                arguments(100, new Integer[]{1, 0, 0}),
                arguments(-10, new Integer[]{1, 0})
        );
    }

}