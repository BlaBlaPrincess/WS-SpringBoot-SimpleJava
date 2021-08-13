package com.blablaprincess.springboot_simplejava.business.validation.integers;

import com.blablaprincess.springboot_simplejava.business.validation.ValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class MaxLengthValidatorUT {

    private interface TestScenario {
        void test(Integer value, int maxLength);
    }

    private static final TestScenario toDoesNotThrows = (value, maxLength) -> {
        // Act + Assert
        assertDoesNotThrow(() -> new MaxLengthValidator(maxLength).validate(value));
    };

    private static final TestScenario toThrows = (value, maxLength) -> {
        // Act + Assert
        assertThrows(ValidationException.class, () -> new MaxLengthValidator(maxLength).validate(value));
    };

    @DisplayName("validate")
    @ParameterizedTest(name = "with {0}, max length: {1}")
    @MethodSource("getValidateCases")
    void validateArrayNotEmpty(Integer value, int maxLength, TestScenario testScenario) {
        testScenario.test(value, maxLength);
    }

    static Stream<Arguments> getValidateCases() {
        return Stream.of(
                arguments(12345678,  8, toDoesNotThrows),
                arguments(123456789, 8, toThrows),
                arguments(123456789, 9, toDoesNotThrows),
                arguments(1234,      4, toDoesNotThrows),
                arguments(12345,     4, toThrows),
                arguments(0,        -1, toThrows)
        );
    }

}