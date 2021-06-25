package com.blablaprincess.springboot_simplejava.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntHelperTest {

    @Test
    void getLength_PositiveNum_3(){
        // Arrange
        int num = 150;

        // Act
        int result = IntHelper.getLength(num);

        // Assert
        assertEquals(3, result);
    }

    @Test
    void getLength_NegativeNum_1(){
        // Arrange
        int num = -5;

        // Act
        int result = IntHelper.getLength(num);

        // Assert
        assertEquals(1, result);
    }

    @Test
    void getLength_Zero_1(){
        // Arrange
        int num = 0;

        // Act
        int result = IntHelper.getLength(num);

        // Assert
        assertEquals(1, result);
    }

}