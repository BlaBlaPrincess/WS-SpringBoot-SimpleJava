package com.blablaprincess.springboot_simplejava.common;

public class IntHelper {
    public static int getLength(int number) {
        return number == 0 ? 1 : (int)(Math.log10(Math.abs(number)) + 1);
    }
}
