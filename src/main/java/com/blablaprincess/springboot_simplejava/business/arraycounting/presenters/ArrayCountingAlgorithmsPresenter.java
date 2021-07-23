package com.blablaprincess.springboot_simplejava.business.arraycounting.presenters;

public interface ArrayCountingAlgorithmsPresenter<T> {
    ArrayCountingAlgorithmsPresenterDto getAlgorithmsCounts(T[] array);
    String[] getAlgorithms();
}
