package com.blablaprincess.springboot_simplejava.business.digitsrepresentation;

import com.blablaprincess.springboot_simplejava.common.utils.IntUtils;
import lombok.var;

public class DigitsRepresentation {

    public static Integer[] getDigitsArray(int number) {
        int abs = Math.abs(number);
        var length = IntUtils.getLength(abs);
        var array = new Integer[length];
        for (int i = 0; i < length; i++) {
            array[length - i - 1] = abs % 10;
            abs /= 10;
        }
        return array;
    }

}
