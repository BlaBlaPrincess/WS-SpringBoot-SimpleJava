package com.blablaprincess.springboot_simplejava.business.digitsrepresentation;

import lombok.var;

public class DigitsRepresentation {

    public static int[] getDigitsArray(int number) {
        number = Math.abs(number);
        var length = number == 0 ? 1 : (int)(Math.log10(number) + 1);
        var array = new int[length];
        for (int i = 0; i < length; i++) {
            array[length - i - 1] = number % 10;
            number /= 10;
        }
        return array;
    }

}
