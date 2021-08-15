package com.blablaprincess.springboot_simplejava.business.arraycounting.presenters.formatters;

import com.blablaprincess.springboot_simplejava.business.arraycounting.presenters.ArrayCountingAlgorithmsPresenterDto;
import com.blablaprincess.springboot_simplejava.business.arraycounting.presenters.ArrayCountingAlgorithmsPresenterDtoFormatter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "count-alg.presenter.format", havingValue = "json", matchIfMissing = true)
public class ArrayCountingAlgorithmsPresenterDtoJsonFormatter
        implements ArrayCountingAlgorithmsPresenterDtoFormatter {

    private final ObjectMapper mapper;

    @Override
    @SneakyThrows
    public String format(ArrayCountingAlgorithmsPresenterDto data) {
        return mapper.writeValueAsString(data);
    }

}
