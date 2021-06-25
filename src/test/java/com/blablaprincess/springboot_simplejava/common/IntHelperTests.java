package com.blablaprincess.springboot_simplejava.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntHelperTests {

    @Test
    void getLengthWithPositiveNum(){
        // Arrange
        int num = 150;

        // Act
        int result = IntHelper.getLength(num);

        // Assert
        assertEquals(3, result);
    }

    @Test
    void getLengthWithNegativeNum(){
        // Arrange
        int num = -5;

        // Act
        int result = IntHelper.getLength(num);

        // Assert
        assertEquals(1, result);
    }

    @Test
    void getLengthWithZero(){
        // Arrange
        int num = 0;

        // Act
        int result = IntHelper.getLength(num);

        // Assert
        assertEquals(1, result);
    }

}