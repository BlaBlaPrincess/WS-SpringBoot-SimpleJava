package com.blablaprincess.springboot_simplejava.business.common.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
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
                arguments("0123456789",   9,  "0123…6789"),
                arguments("0123456789",  10, "0123456789"),
                arguments("0123456789A", 10, "0123…6789A"),
                arguments("0123456789",  11, "0123456789")
        );
    }

}