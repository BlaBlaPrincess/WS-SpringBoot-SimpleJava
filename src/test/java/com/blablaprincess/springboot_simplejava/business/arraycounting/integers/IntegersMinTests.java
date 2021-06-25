package com.blablaprincess.springboot_simplejava.business.arraycounting.integers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntegersMinTests {

    @Test
    void count_PositiveSet_1() {
        // Arrange
        Integer[] set = {1, 2, 3, 4, 5, 5};

        // Act
        double result = new IntegersMin().count(set);

        // Assert
        assertEquals(1, result);
    }

    @Test
    void count_NegativeSet_negative10() {
        // Arrange
        Integer[] set = {20, -10};

        // Act
        double result = new IntegersMin().count(set);

        // Assert
        assertEquals(-10, result);
    }

    @Test
    void count_EmptySet_ThrowsIllegalArgumentException() {
        // Arrange
        Integer[] set = {};

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> new IntegersMin().count(set));
    }

}