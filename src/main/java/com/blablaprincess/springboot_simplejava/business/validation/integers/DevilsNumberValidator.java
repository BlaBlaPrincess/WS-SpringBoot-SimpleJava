package com.blablaprincess.springboot_simplejava.business.validation.integers;

import com.blablaprincess.springboot_simplejava.business.validation.ValidationException;
import com.blablaprincess.springboot_simplejava.business.validation.Validator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@ConditionalOnProperty(name = "validation.int.devils-number.enabled", havingValue = "true")
public class DevilsNumberValidator implements Validator<Integer> {

    @Override
    public void validate(Integer value) throws ValidationException {
        Pattern p = Pattern.compile("(\\D|\\b)666(\\D|\\b)");
        Matcher m = p.matcher(value.toString());
        if (m.matches()) {
            throw new ValidationException();
        }
    }

}
