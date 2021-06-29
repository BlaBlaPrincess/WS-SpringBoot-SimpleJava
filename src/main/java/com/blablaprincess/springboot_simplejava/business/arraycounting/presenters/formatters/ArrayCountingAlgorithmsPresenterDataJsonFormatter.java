package com.blablaprincess.springboot_simplejava.business.arraycounting.presenters.formatters;

import com.blablaprincess.springboot_simplejava.business.arraycounting.presenters.ArrayCountingAlgorithmsPresenterData;
import com.blablaprincess.springboot_simplejava.business.arraycounting.presenters.ArrayCountingAlgorithmsPresenterDataFormatter;
import lombok.var;
import org.json.JSONObject;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "count_alg.presenter.format", havingValue = "json", matchIfMissing = true)
public class ArrayCountingAlgorithmsPresenterDataJsonFormatter
        implements ArrayCountingAlgorithmsPresenterDataFormatter {

    @Override
    public String format(ArrayCountingAlgorithmsPresenterData data) {
        var json = new JSONObject();
        json.put(data.getClass()
                     .getSimpleName(),
                 data.getCounts());
        return json.toString();
    }

}
