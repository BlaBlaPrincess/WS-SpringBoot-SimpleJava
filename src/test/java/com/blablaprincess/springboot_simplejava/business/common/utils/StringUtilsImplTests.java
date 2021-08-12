package com.blablaprincess.springboot_simplejava.business.common.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class StringUtilsImplTests {

    private final StringUtilsImpl stringUtils = new StringUtilsImpl();

    @DisplayName("cropByMaxLength() expect not cropped")
    @ParameterizedTest(name = "{0} input length, {1} max length")
    @MethodSource("cropByMaxLengthNotCroppedCases")
    void cropByMaxLengthExpectNotCropped(int inputLength, int maxLength) {
        // Arrange
        String input = RandomStringUtils.randomAlphabetic(inputLength);

        // Act
        String result = stringUtils.cropByMaxLength(input, maxLength);

        // Assert
        assertEquals(input, result);
    }

    @DisplayName("cropByMaxLength() expect cropped")
    @ParameterizedTest(name = "{0} input length, {1} max length")
    @MethodSource("cropByMaxLengthCroppedCases")
    void setCroppedResponseExpectCropped(int inputLength, int maxLength) {
        // Arrange
        String input = RandomStringUtils.randomAlphabetic(inputLength);
        int half = maxLength / 2;
        String croppedInput = input.substring(0, half - 5) + "… … …" + input.substring(inputLength - half, inputLength - 1);

        // Act
        String result = stringUtils.cropByMaxLength(input, maxLength);

        // Assert
        assertEquals(croppedInput, result);
    }

    private static Stream<Arguments> cropByMaxLengthNotCroppedCases() {
        int case11 = 255 / 2;
        int case12 = 255 - 1;
        int case13 = 255;

        int case21 = 256 / 2;
        int case22 = 256 - 1;
        int case23 = 256;

        int case31 = 511 / 2;
        int case32 = 511 - 1;
        int case33 = 511;

        return Stream.of(
                arguments(case11, 255), arguments(case12, 255), arguments(case13, 255),
                arguments(case21, 256), arguments(case22, 256), arguments(case23, 256),
                arguments(case31, 511), arguments(case32, 511), arguments(case33, 511)
        );
    }

    private static Stream<Arguments> cropByMaxLengthCroppedCases() {
        int case14 = 255 + 1;
        int case15 = 255 * 2;

        int case24 = 256 + 1;
        int case25 = 256 * 2;

        int case34 = 511 + 1;
        int case35 = 511 * 2;

        return Stream.of(
                arguments(case14, 255), arguments(case15, 255),
                arguments(case24, 255), arguments(case25, 256),
                arguments(case34, 511), arguments(case35, 511)
        );
    }

}