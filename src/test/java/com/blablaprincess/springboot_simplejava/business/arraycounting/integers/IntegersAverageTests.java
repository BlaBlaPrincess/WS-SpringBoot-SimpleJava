package com.blablaprincess.springboot_simplejava.business.arraycounting.integers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntegersAverageTests {

    @Test
    void count_PositiveSet_4() {
        Integer[] set = {1, 2, 3, 4, 10};

        double result = new IntegersAverage().count(set);

        assertEquals(4, result);
    }

    @Test
    void count_MixedSet_5() {
        Integer[] set = {20, -10};

        double result = new IntegersAverage().count(set);

        assertEquals(5, result);
    }

    @Test
    void count_EmptySet_0() {
        Integer[] set = {};

        assertThrows(ArithmeticException.class, () -> new IntegersAverage().count(set));
    }

}