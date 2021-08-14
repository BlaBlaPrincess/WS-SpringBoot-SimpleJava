package com.blablaprincess.springboot_simplejava.business.common.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class StringUtilsUT {

    private final StringUtils stringUtils = new StringUtils();

    @DisplayName("cropByMaxLength()")
    @ParameterizedTest(name = "{0} input length, {1} max length")
    @MethodSource("getCropByMaxLengthCases")
    void cropByMaxLength(String input, int maxLength, String expected) {
        // Act
        String result = stringUtils.cropByMaxLength(input, maxLength);

        // Assert
        assertEquals(expected, result);
    }

    private static Stream<Arguments> getCropByMaxLengthCases() {

        return Stream.of(
                arguments("123456", 7, "123456"), // input < maxLength
                arguments("1234567", 7, "1234567"), // input == maxLength
                arguments("12345678", 7, "123…678"), // input (even) > maxLength (odd)
                arguments("123456789", 7, "123…789"), // input (odd) > maxLength (odd)
                arguments("123456789", 8, "123…6789"), // input (odd) > maxLength (even)
                arguments("1234567890", 8, "123…7890") // input (even) > maxLength (even)
        );
    }

}