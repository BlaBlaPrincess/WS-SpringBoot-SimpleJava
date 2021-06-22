package com.blablaprincess.springboot_simplejava.business.arraycounting.presenters;

import com.blablaprincess.springboot_simplejava.business.arraycounting.ArrayCountingAlgorithm;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class ArrayCountingAlgorithmsPresenter<T> {

    private final List<ArrayCountingAlgorithm<T>> algorithms;

    @Autowired
    public ArrayCountingAlgorithmsPresenter(List<ArrayCountingAlgorithm<T>> algorithms){
        this.algorithms = algorithms;
    }

    public String Present(T[] array) {
        StringBuilder result = new StringBuilder();
        result.append(String.format("Counting algorithms for %s", array.getClass().getSimpleName()));

        if (algorithms.size() == 0) {
            result.append(" not found");
            return result.toString();
        }

        result.append(String.format(": %d%n%nArray: %s%n", algorithms.size(), Arrays.toString(array)));
        for (var alg : algorithms) {
            result.append(String.format("%n%s: %.2f", alg.getClass().getSimpleName(), alg.count(array)));
        }
        return result.toString();
    }

}
