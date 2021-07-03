package com.blablaprincess.springboot_simplejava.business.arraycounting.integers;

import com.blablaprincess.springboot_simplejava.business.common.exceptions.EmptyArrayException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntegersMaxTests {

    private final IntegersMax integersMax = new IntegersMax();

    @Test
    void countWithPositiveSet() {
        // Arrange
        Integer[] set = {1, 2, 3, 4, 5, 5};

        // Act
        double result = integersMax.count(set);

        // Assert
        assertEquals(5, result);
    }

    @Test
    void countWithMixedSet() {
        // Arrange
        Integer[] set = {20, -10};

        // Act
        double result = integersMax.count(set);

        // Assert
        assertEquals(20, result);
    }

    @Test
    void countWithEmptySetThrowsIllegalArgumentException() {
        // Arrange
        Integer[] set = {};

        // Act + Assert
        assertThrows(EmptyArrayException.class, () -> integersMax.count(set));
    }

}