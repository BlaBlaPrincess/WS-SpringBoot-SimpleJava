package com.blablaprincess.springboot_simplejava.business.arraycounting.integers;

import com.blablaprincess.springboot_simplejava.business.arraycounting.ArrayCountingAlgorithm;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@ConditionalOnProperty(name = "count_alg.int.max.enabled", havingValue = "true")
public class IntegersMax implements ArrayCountingAlgorithm<Integer> {

    @Override
    public double count(Integer[] array) {
        return Arrays.stream(array)
                     .max(Integer::compareTo)
                     .orElseThrow(IllegalArgumentException::new);
    }

}
