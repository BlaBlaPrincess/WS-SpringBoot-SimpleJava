package com.blablaprincess.springboot_simplejava.business.common.utils;

import com.blablaprincess.springboot_simplejava.business.common.exceptions.EmptyArrayException;

public class ArrayUtils {

    public static <T> void ValidateArrayNotEmpty(T[] array) {
        if (array.length == 0) {
            throw new EmptyArrayException();
        }
    }

}
