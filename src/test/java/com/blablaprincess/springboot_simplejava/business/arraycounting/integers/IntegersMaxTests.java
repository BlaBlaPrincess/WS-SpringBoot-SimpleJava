package com.blablaprincess.springboot_simplejava.business.arraycounting.integers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntegersMaxTests {

    @Test
    void count_PositiveSet_5() {
        Integer[] set = {1, 2, 3, 4, 5, 5};

        double result = new IntegersMax().count(set);

        assertEquals(5, result);
    }

    @Test
    void count_NegativeSet_20() {
        Integer[] set = {20, -10};

        double result = new IntegersMax().count(set);

        assertEquals(20, result);
    }

    @Test
    void count_EmptySet_ThrowsIllegalArgumentException() {
        Integer[] set = {};

        assertThrows(IllegalArgumentException.class, () -> new IntegersMax().count(set));
    }

}