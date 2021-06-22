package com.blablaprincess.springboot_simplejava.business.arraycounting.integers;

import com.blablaprincess.springboot_simplejava.business.arraycounting.ArrayCountingAlgorithm;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@ConditionalOnProperty(name = "integers_counting_algorithms_presenter.min", havingValue = "true")
public class IntegersMin implements ArrayCountingAlgorithm<Integer> {

    @Override
    public double count(Integer[] array) {
        return Arrays.stream(array).min(Integer::compareTo).orElseThrow(IllegalArgumentException::new);
    }

}
