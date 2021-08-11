package com.blablaprincess.springboot_simplejava.business.validation.integers;

import com.blablaprincess.springboot_simplejava.business.common.utils.IntUtils;
import com.blablaprincess.springboot_simplejava.business.validation.ValidationException;
import com.blablaprincess.springboot_simplejava.business.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
@ConditionalOnProperty(name = "validation.int.max-length.enabled", havingValue = "true")
public class MaxLengthValidator implements Validator<Integer> {

    @Value("${validation.int.max-length:8}")
    private final Integer MAX_LENGTH;

    @Autowired
    public MaxLengthValidator(@Value("${validation.int.max-length:8}") int maxLength) {
        MAX_LENGTH = maxLength;
    }

    @Override
    public void validate(Integer value) throws ValidationException {
        if (IntUtils.getLength(value) > MAX_LENGTH) {
            throw new ValidationException(String.format("The number cannot be larger than %d signs", MAX_LENGTH));
        }
    }

}
