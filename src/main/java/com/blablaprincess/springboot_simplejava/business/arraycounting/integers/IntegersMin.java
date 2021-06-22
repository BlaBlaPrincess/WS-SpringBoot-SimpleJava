package com.blablaprincess.springboot_simplejava.business.arraycounting.integers;

import com.blablaprincess.springboot_simplejava.business.arraycounting.ArrayCountingAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class IntegersMin implements ArrayCountingAlgorithm<Integer> {

    @Override
    public double count(Integer[] array) {
        return Arrays.stream(array).min(Integer::compareTo).orElseThrow(IllegalArgumentException::new);
    }

}
