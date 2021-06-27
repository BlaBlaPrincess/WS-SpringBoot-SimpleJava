package com.blablaprincess.springboot_simplejava.business.arraycounting.integers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntegersSumTests {

    private final IntegersSum integersSum = new IntegersSum();

    @Test
    void countWithPositiveSet() {
        // Arrange
        Integer[] set = {1, 2, 3, 4, 5, 5};

        // Act
        double result = integersSum.count(set);

        // Assert
        assertEquals(20, result);
    }

    @Test
    void countWithMixedSet() {
        // Arrange
        Integer[] set = {20, -10};

        // Act
        double result = integersSum.count(set);

        // Assert
        assertEquals(10, result);
    }

    @Test
    void countWithEmptySet() {
        // Arrange
        Integer[] set = {};

        // Act
        double result = integersSum.count(set);

        // Assert
        assertEquals(0, result);
    }

}