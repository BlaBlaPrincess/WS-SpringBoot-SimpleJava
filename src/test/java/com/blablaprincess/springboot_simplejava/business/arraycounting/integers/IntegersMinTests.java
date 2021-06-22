package com.blablaprincess.springboot_simplejava.business.arraycounting.integers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntegersMinTests {

    @Test
    void count_PositiveSet_1() {
        Integer[] set = {1, 2, 3, 4, 5, 5};

        double result = new IntegersMin().count(set);

        assertEquals(1, result);
    }

    @Test
    void count_NegativeSet_negative10() {
        Integer[] set = {20, -10};

        double result = new IntegersMin().count(set);

        assertEquals(-10, result);
    }

    @Test
    void count_EmptySet_ThrowsIllegalArgumentException() {
        Integer[] set = {};

        assertThrows(IllegalArgumentException.class, () -> new IntegersMin().count(set));
    }

}