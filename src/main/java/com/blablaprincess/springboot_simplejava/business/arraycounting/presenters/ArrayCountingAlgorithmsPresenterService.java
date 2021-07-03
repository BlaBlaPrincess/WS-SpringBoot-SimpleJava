package com.blablaprincess.springboot_simplejava.business.arraycounting.presenters;

import com.blablaprincess.springboot_simplejava.business.arraycounting.ArrayCountingAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArrayCountingAlgorithmsPresenterService<T> {

    private final List<ArrayCountingAlgorithm<T>> algorithms;

    public ArrayCountingAlgorithmsPresenterDto getAlgorithmsCounts(T[] array) {
        var counts = new HashMap<String, Double>();
        for (var alg : algorithms) {
            counts.put(alg.getClass()
                          .getSimpleName(),
                       alg.count(array));
        }

        return ArrayCountingAlgorithmsPresenterDto.builder()
                                                  .counts(counts)
                                                  .build();
    }

    public String[] getAlgorithms() {
        var result = new String[algorithms.size()];
        for (int i = 0; i < algorithms.size(); i++) {
            result[i] = algorithms.get(i).getClass().getSimpleName();
        }
        return result;
    }

}
