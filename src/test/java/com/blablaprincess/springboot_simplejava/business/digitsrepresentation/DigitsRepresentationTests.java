package com.blablaprincess.springboot_simplejava.business.digitsrepresentation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DigitsRepresentationTests {

    @Test
    void getDigitsArray_PositiveNumber_Equals(){
        // Arrange
        int number = 8721654;

        // Act
        Integer[] result = DigitsRepresentation.getDigitsArray(number);

        // Assert
        assertArrayEquals(new Integer[]{8, 7, 2, 1, 6, 5, 4}, result);
    }

    @Test
    void getDigitsArray_NegativeNumber_Equals(){
        // Arrange
        int number = -21415;

        // Act
        Integer[] result = DigitsRepresentation.getDigitsArray(number);

        // Assert
        assertArrayEquals(new Integer[]{2, 1, 4, 1, 5}, result);
    }

    @Test
    void getDigitsArray_SingleDigit_Equals(){
        // Arrange
        int number = 1;

        // Act
        Integer[] result = DigitsRepresentation.getDigitsArray(number);

        // Assert
        assertArrayEquals(new Integer[]{1}, result);
    }

}