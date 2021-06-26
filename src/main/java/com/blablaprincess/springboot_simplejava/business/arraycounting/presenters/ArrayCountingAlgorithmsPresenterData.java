package com.blablaprincess.springboot_simplejava.business.arraycounting.presenters;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Builder
@AllArgsConstructor
public class ArrayCountingAlgorithmsPresenterData {
    @Getter private final Map<String, Double> counts;
}
