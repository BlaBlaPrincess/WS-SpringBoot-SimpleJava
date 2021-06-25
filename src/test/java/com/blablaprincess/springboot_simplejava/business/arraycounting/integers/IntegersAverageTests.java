package com.blablaprincess.springboot_simplejava.business.arraycounting.integers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntegersAverageTests {

    @Test
    void countWithPositiveSet() {
        // Arrange
        Integer[] set = {1, 2, 3, 4, 10};

        // Act
        double result = new IntegersAverage().count(set);

        // Assert
        assertEquals(4, result);
    }

    @Test
    void countWithMixedSet() {
        // Arrange
        Integer[] set = {20, -10};

        // Act
        double result = new IntegersAverage().count(set);

        // Assert
        assertEquals(5, result);
    }

    @Test
    void countWithEmptySet() {
        // Arrange
        Integer[] set = {};

        // Act + Assert
        assertThrows(ArithmeticException.class, () -> new IntegersAverage().count(set));
    }

}