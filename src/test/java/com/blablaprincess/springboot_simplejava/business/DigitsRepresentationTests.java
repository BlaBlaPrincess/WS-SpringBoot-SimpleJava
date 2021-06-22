package com.blablaprincess.springboot_simplejava.business;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DigitsRepresentationTests {

    @Test
    void getDigitsArray_PositiveNumber_Equals(){
        int number = 8721654;

        int[] result = DigitsRepresentation.getDigitsArray(number);

        assertArrayEquals(new int[]{8, 7, 2, 1, 6, 5, 4}, result);
    }

    @Test
    void getDigitsArray_NegativeNumber_Equals(){
        int number = -21415;

        int[] result = DigitsRepresentation.getDigitsArray(number);

        assertArrayEquals(new int[]{2, 1, 4, 1, 5}, result);
    }

    @Test
    void getDigitsArray_SingleDigit_Equals(){
        int number = 1;

        int[] result = DigitsRepresentation.getDigitsArray(number);

        assertArrayEquals(new int[]{1}, result);
    }

    @Test
    void getDigitsArray_Zero_Equals(){
        int number = 0;

        int[] result = DigitsRepresentation.getDigitsArray(number);

        assertArrayEquals(new int[]{0}, result);
    }

}