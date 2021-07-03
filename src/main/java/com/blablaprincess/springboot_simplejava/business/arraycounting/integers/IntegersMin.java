package com.blablaprincess.springboot_simplejava.business.arraycounting.integers;

import com.blablaprincess.springboot_simplejava.business.arraycounting.ArrayCountingAlgorithm;
import com.blablaprincess.springboot_simplejava.business.arraycounting.UnexpectedArrayCountingException;
import com.blablaprincess.springboot_simplejava.business.common.utils.ArrayUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@ConditionalOnProperty(name = "count_alg.int.min.enabled", havingValue = "true")
public class IntegersMin implements ArrayCountingAlgorithm<Integer> {

    @Override
    public double count(Integer[] array) {
        ArrayUtils.ValidateArrayNotEmpty(array);
        return Arrays.stream(array)
                     .min(Integer::compareTo)
                     .orElseThrow(UnexpectedArrayCountingException::new);
    }

}
