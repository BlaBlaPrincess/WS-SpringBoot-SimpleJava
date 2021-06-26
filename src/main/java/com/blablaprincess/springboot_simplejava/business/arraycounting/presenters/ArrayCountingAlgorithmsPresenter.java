package com.blablaprincess.springboot_simplejava.business.arraycounting.presenters;

import com.blablaprincess.springboot_simplejava.business.arraycounting.ArrayCountingAlgorithm;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ArrayCountingAlgorithmsPresenter<T> {

    private final List<ArrayCountingAlgorithm<T>> algorithms;
    private final ArrayCountingAlgorithmsPresenterDataFormatter formatter;

    @Autowired
    public ArrayCountingAlgorithmsPresenter(List<ArrayCountingAlgorithm<T>> algorithms,
                                            ArrayCountingAlgorithmsPresenterDataFormatter formatter) {
        this.algorithms = algorithms;
        this.formatter = formatter;
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

    private ArrayCountingAlgorithmsPresenterData getInfo(T[] array) {
        return ArrayCountingAlgorithmsPresenterData.builder()
                                                   .counts(getAlgorithmsCounts(array))
                                                   .build();
    }

    public String present(T[] array) {
        return formatter.format(getInfo(array));
    }

}
