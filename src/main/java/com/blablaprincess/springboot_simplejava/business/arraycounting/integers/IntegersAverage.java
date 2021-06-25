package com.blablaprincess.springboot_simplejava.business.arraycounting.integers;

import com.blablaprincess.springboot_simplejava.business.arraycounting.ArrayCountingAlgorithm;
import lombok.var;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@ConditionalOnProperty(name = "integers_counting_algorithms_presenter.average", havingValue = "true")
public class IntegersAverage implements ArrayCountingAlgorithm<Integer> {

    @Override
    public double count(Integer[] array) {
        var count = array.length;
        if (count == 0) {
            throw new ArithmeticException();
        }
        return (double) Arrays.stream(array)
                              .mapToInt(Integer::intValue)
                              .sum() / count;
    }

}
