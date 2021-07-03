package com.blablaprincess.springboot_simplejava.business.arraycounting.presenters;

import com.blablaprincess.springboot_simplejava.business.arraycounting.ArrayCountingAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ArrayCountingAlgorithmsPresenter<T> {

    private final List<ArrayCountingAlgorithm<T>> algorithms;

    public ArrayCountingAlgorithmsPresenterDto present(T[] array) {
        return ArrayCountingAlgorithmsPresenterDto.builder()
                                                  .counts(getAlgorithmsCounts(array))
                                                  .build();
    }

    private Map<String, Double> getAlgorithmsCounts(T[] array) {
        var counts = new HashMap<String, Double>();
        for (var alg : algorithms) {
            counts.put(alg.getClass()
                          .getSimpleName(),
                       alg.count(array));
        }
        return counts;
    }

}
