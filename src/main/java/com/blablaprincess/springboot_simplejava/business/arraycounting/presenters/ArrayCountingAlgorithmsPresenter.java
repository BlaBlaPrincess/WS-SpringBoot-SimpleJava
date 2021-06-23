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
    private final StringBuilder builder;
    private T[] array;

    @Autowired
    public ArrayCountingAlgorithmsPresenter(List<ArrayCountingAlgorithm<T>> algorithms){
        this.algorithms = algorithms;
        builder = new StringBuilder();
    }

    public ArrayCountingAlgorithmsPresenter<T> setup(T[] array) {
        this.array = array;
        builder.setLength(0);
        return this;
    }

    private void breakLine() {
        if (builder.length() != 0) {
            builder.append(String.format("%n"));
        }
    }

    public ArrayCountingAlgorithmsPresenter<T> withAlgorithmsCount() {
        breakLine();
        builder.append(String.format("Counting algorithms for %s", array.getClass().getSimpleName()));
        builder.append(algorithms.size() == 0 ? " not found" : String.format(": %d", algorithms.size()));
        return this;
    }

    public ArrayCountingAlgorithmsPresenter<T> withAlgorithmsList() {
        breakLine();
        builder.append(String.format("Algorithms:%n"));
        for (var alg : algorithms) {
            builder.append(String.format("%n-%s", alg.getClass().getSimpleName()));
        }
        return this;
    }

    public ArrayCountingAlgorithmsPresenter<T> withArray() {
        breakLine();
        builder.append(String.format("Array: %s%n", Arrays.toString(array)));
        return this;
    }

    public ArrayCountingAlgorithmsPresenter<T> withCounts() {
        breakLine();
        for (var alg : algorithms) {
            builder.append(String.format("%n%s: %.2f", alg.getClass().getSimpleName(), alg.count(array)));
        }
        return this;
    }

    public ArrayCountingAlgorithmsPresenter<T> withSeparator(String separator) {
        breakLine();
        builder.append(separator);
        return this;
    }

    public ArrayCountingAlgorithmsPresenter<T> withSeparator() {
        breakLine();
        return this;
    }

    @Override
    public String toString() {
        return builder.toString();
    }

}
