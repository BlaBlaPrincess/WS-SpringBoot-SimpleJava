package com.blablaprincess.springboot_simplejava.business.arraycounting.presenters;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
public class ArrayCountingAlgorithmsPresenterDto {
    private final Map<String, Double> counts;
}
