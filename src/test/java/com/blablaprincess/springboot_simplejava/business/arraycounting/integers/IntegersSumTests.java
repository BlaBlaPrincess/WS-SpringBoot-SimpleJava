package com.blablaprincess.springboot_simplejava.business.arraycounting.integers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntegersSumTests {

    @Test
    void count_PositiveSet_20() {
        Integer[] set = {1, 2, 3, 4, 5, 5};

        double result = new IntegersSum().count(set);

        assertEquals(20, result);
    }

    @Test
    void count_NegativeSet_10() {
        Integer[] set = {20, -10};

        double result = new IntegersSum().count(set);

        assertEquals(10, result);
    }

    @Test
    void count_EmptySet_0() {
        Integer[] set = {};

        double result = new IntegersSum().count(set);

        assertEquals(0, result);
    }

}