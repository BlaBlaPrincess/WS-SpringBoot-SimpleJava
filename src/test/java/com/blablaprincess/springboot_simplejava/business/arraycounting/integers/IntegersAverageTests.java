package com.blablaprincess.springboot_simplejava.business.arraycounting.integers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntegersAverageTests {

    @Test
    void count_PositiveSet_4() {
        // Arrange
        Integer[] set = {1, 2, 3, 4, 10};

        // Act
        double result = new IntegersAverage().count(set);

        // Assert
        assertEquals(4, result);
    }

    @Test
    void count_MixedSet_5() {
        // Arrange
        Integer[] set = {20, -10};

        // Act
        double result = new IntegersAverage().count(set);

        // Assert
        assertEquals(5, result);
    }

    @Test
    void count_EmptySet_0() {
        // Arrange
        Integer[] set = {};

        // Act + Assert
        assertThrows(ArithmeticException.class, () -> new IntegersAverage().count(set));
    }

}