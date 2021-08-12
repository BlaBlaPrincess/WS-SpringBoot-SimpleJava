package com.blablaprincess.springboot_simplejava.business.common.utils;

import org.springframework.stereotype.Component;

@Component
public class StringUtilsImpl implements StringUtils  {

    public String cropByMaxLength(String str, int maxLength) {
        int length = str.length();
        if (length > maxLength) {
            int half = maxLength / 2;
            return str.substring(0, half - 5) + "… … …" + str.substring(length - half, length - 1);
        }
        return str;
    }

}
