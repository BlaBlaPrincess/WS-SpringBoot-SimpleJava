package com.blablaprincess.springboot_simplejava.business.arraycounting.integers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntegersSumTests {

    @Test
    void count_PositiveSet_20() {
        // Arrange
        Integer[] set = {1, 2, 3, 4, 5, 5};

        // Act
        double result = new IntegersSum().count(set);

        // Assert
        assertEquals(20, result);
    }

    @Test
    void count_NegativeSet_10() {
        // Arrange
        Integer[] set = {20, -10};

        // Act
        double result = new IntegersSum().count(set);

        // Assert
        assertEquals(10, result);
    }

    @Test
    void count_EmptySet_0() {
        // Arrange
        Integer[] set = {};

        // Act
        double result = new IntegersSum().count(set);

        // Assert
        assertEquals(0, result);
    }

}