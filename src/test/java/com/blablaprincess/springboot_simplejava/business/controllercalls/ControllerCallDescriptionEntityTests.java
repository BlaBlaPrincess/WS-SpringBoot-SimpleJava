package com.blablaprincess.springboot_simplejava.business.controllercalls;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils;

import java.util.stream.Stream;

import static com.blablaprincess.springboot_simplejava.business.controllercalls.ControllerCallDescriptionEntity.MAX_RESPONSE_LENGTH;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class ControllerCallDescriptionEntityTests {


    @DisplayName("setResponse() expect not cropped response")
    @ParameterizedTest(name = "{0} symbols")
    @MethodSource("getSetResponseCases")
    void setResponse(int inputLength) {
        // Arrange
        String input = RandomStringUtils.randomAlphabetic(inputLength);
        ControllerCallDescriptionEntity entity = new ControllerCallDescriptionEntity();

        // Act
        entity.setResponse(input);

        // Assert
        assertEquals(input, entity.getResponse());
    }

    @DisplayName("builder().response() expect not cropped response")
    @ParameterizedTest(name = "{0} symbols")
    @MethodSource("getSetResponseCases")
    void builderSetResponse(int inputLength) {
        // Arrange
        String input = RandomStringUtils.randomAlphabetic(inputLength);

        // Act
        ControllerCallDescriptionEntity entity = ControllerCallDescriptionEntity.builder().response(input).build();

        // Assert
        assertEquals(input, entity.getResponse());
    }

    @DisplayName("setResponse() expect cropped response")
    @ParameterizedTest(name = "{0} symbols")
    @MethodSource("getSetCroppedResponseCases")
    void setCroppedResponse(int inputLength) {
        // Arrange
        String input = RandomStringUtils.randomAlphabetic(inputLength);
        int half = MAX_RESPONSE_LENGTH / 2;
        String croppedInput = input.substring(0, half - 5) + "… … …" + input.substring(inputLength - half, inputLength - 1);
        ControllerCallDescriptionEntity entity = new ControllerCallDescriptionEntity();

        // Act
        entity.setResponse(input);

        // Assert
        assertEquals(croppedInput, entity.getResponse());
    }

    @DisplayName("builder().response() expect cropped response")
    @ParameterizedTest(name = "{0} symbols")
    @MethodSource("getSetCroppedResponseCases")
    void builderSetCroppedResponse(int inputLength) {
        // Arrange
        String input = RandomStringUtils.randomAlphabetic(inputLength);
        int half = MAX_RESPONSE_LENGTH / 2;
        String croppedInput = input.substring(0, half - 5) + "… … …" + input.substring(inputLength - half, inputLength - 1);

        // Act
        ControllerCallDescriptionEntity entity = ControllerCallDescriptionEntity.builder().response(input).build();

        // Assert
        assertEquals(croppedInput, entity.getResponse());
    }

    @SuppressWarnings("UnnecessaryLocalVariable")
    private static Stream<Arguments> getSetResponseCases() {
        int case1 = MAX_RESPONSE_LENGTH / 2;
        int case2 = MAX_RESPONSE_LENGTH - 1;
        int case3 = MAX_RESPONSE_LENGTH;

        return Stream.of(
                arguments(case1),
                arguments(case2),
                arguments(case3)
        );
    }

    private static Stream<Arguments> getSetCroppedResponseCases() {
        int case4 = MAX_RESPONSE_LENGTH + 1;
        int case5 = MAX_RESPONSE_LENGTH * 2;
        return Stream.of(
                arguments(case4),
                arguments(case5)
        );
    }

}