package com.blablaprincess.springboot_simplejava.business.arraycounting.presenters.formatters;

import com.blablaprincess.springboot_simplejava.business.arraycounting.presenters.ArrayCountingAlgorithmsPresenterData;
import com.blablaprincess.springboot_simplejava.business.arraycounting.presenters.ArrayCountingAlgorithmsPresenterDataFormatter;
import lombok.var;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "count_alg.presenter.format", havingValue = "text")
public class ArrayCountingAlgorithmsPresenterDataTextFormatter
        implements ArrayCountingAlgorithmsPresenterDataFormatter {

    @Override
    public String format(ArrayCountingAlgorithmsPresenterData data) {
        var builder = new StringBuilder();
        for (var count : data.getCounts().entrySet()) {
            builder.append(String.format("%s: %.2f%n", count.getKey(), count.getValue()));
        }
        builder.setLength(builder.length() - 1);
        return builder.toString();
    }

}
