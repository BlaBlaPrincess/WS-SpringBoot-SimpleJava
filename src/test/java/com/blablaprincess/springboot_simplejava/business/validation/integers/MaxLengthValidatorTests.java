package com.blablaprincess.springboot_simplejava.business.validation.integers;

import com.blablaprincess.springboot_simplejava.business.validation.ValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.mock.env.MockEnvironment;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class MaxLengthValidatorTests {

    private interface TestScenario {
        void test(Integer value, String property);
    }

    private static MockEnvironment getEnvironment(String property) {
        MockEnvironment environment = new MockEnvironment();
        if (property != null) {
            environment.setProperty("validation.int.max-length", property);
        }
        return environment;
    }

    private static final TestScenario toDoesNotThrows = (value, property) -> {
        // Arrange
        MockEnvironment environment = getEnvironment(property);
        // Act + Assert
        assertDoesNotThrow(() -> new MaxLengthValidator(environment).validate(value));
    };

    private static final TestScenario toThrows = (value, property) -> {
        // Arrange
        MockEnvironment environment = getEnvironment(property);
        // Act + Assert
        assertThrows(ValidationException.class, () -> new MaxLengthValidator(environment).validate(value));
    };

    @DisplayName("validate")
    @ParameterizedTest(name = "with {0}, property: {1}")
    @MethodSource("getValidateCases")
    void validateArrayNotEmpty(Integer value, String property, TestScenario testScenario) {
        testScenario.test(value, property);
    }

    static Stream<Arguments> getValidateCases() {
        return Stream.of(
                arguments(12345678,  null,  toDoesNotThrows),
                arguments(123456789, null,  toThrows),
                arguments(12345678,  "any", toDoesNotThrows),
                arguments(123456789, "any", toThrows),
                arguments(123456789, "9",   toDoesNotThrows),
                arguments(1234,      "4",   toDoesNotThrows),
                arguments(12345,     "4",   toThrows),
                arguments(0,         "-1",  toThrows)
        );
    }

}