package com.blablaprincess.springboot_simplejava.business.arraycounting.integers;

import com.blablaprincess.springboot_simplejava.business.arraycounting.ArrayCountingAlgorithm;
import com.blablaprincess.springboot_simplejava.business.common.utils.ArrayUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@ConditionalOnProperty(name = "count-alg.int.average.enabled", havingValue = "true")
public class IntegersAverage implements ArrayCountingAlgorithm<Integer> {

    @Override
    public double count(Integer[] array) {
        ArrayUtils.ValidateArrayNotEmpty(array);
        return (double) Arrays.stream(array)
                              .mapToInt(Integer::intValue)
                              .sum() / array.length;
    }

}
