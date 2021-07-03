package com.blablaprincess.springboot_simplejava.business.arraycounting.presenters.formatters;

import com.blablaprincess.springboot_simplejava.business.arraycounting.presenters.ArrayCountingAlgorithmsPresenterDto;
import com.blablaprincess.springboot_simplejava.business.arraycounting.presenters.ArrayCountingAlgorithmsPresenterDtoFormatter;
import lombok.var;
import org.json.JSONObject;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "count-alg.presenter.format", havingValue = "json", matchIfMissing = true)
public class ArrayCountingAlgorithmsPresenterDtoJsonFormatter
        implements ArrayCountingAlgorithmsPresenterDtoFormatter {

    @Override
    public String format(ArrayCountingAlgorithmsPresenterDto data) {
        var json = new JSONObject();
        json.put(data.getClass()
                     .getSimpleName(),
                 data.getCounts());
        return json.toString();
    }

}
