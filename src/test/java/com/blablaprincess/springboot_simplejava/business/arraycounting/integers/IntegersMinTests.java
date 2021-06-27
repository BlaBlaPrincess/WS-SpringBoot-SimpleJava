package com.blablaprincess.springboot_simplejava.business.arraycounting.integers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntegersMinTests {

    private final IntegersMin integersMin = new IntegersMin();

    @Test
    void countWithPositiveSet() {
        // Arrange
        Integer[] set = {1, 2, 3, 4, 5, 5};

        // Act
        double result = integersMin.count(set);

        // Assert
        assertEquals(1, result);
    }

    @Test
    void countWithMixedSet() {
        // Arrange
        Integer[] set = {20, -10};

        // Act
        double result = integersMin.count(set);

        // Assert
        assertEquals(-10, result);
    }

    @Test
    void countWithEmptySetThrowsIllegalArgumentException() {
        // Arrange
        Integer[] set = {};

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> integersMin.count(set));
    }

}