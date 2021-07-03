package com.blablaprincess.springboot_simplejava.business.arraycounting.integers;

import com.blablaprincess.springboot_simplejava.common.exceptions.EmptyArrayException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntegersAverageTests {

    private final IntegersAverage integersAverage = new IntegersAverage();

    @Test
    void countWithPositiveSet() {
        // Arrange
        Integer[] set = {1, 2, 3, 4, 10};

        // Act
        double result = integersAverage.count(set);

        // Assert
        assertEquals(4, result);
    }

    @Test
    void countWithMixedSet() {
        // Arrange
        Integer[] set = {20, -10};

        // Act
        double result = integersAverage.count(set);

        // Assert
        assertEquals(5, result);
    }

    @Test
    void countWithEmptySet() {
        // Arrange
        Integer[] set = {};

        // Act + Assert
        assertThrows(EmptyArrayException.class, () -> integersAverage.count(set));
    }

}