package com.blablaprincess.springboot_simplejava.business.validation.integers;

import com.blablaprincess.springboot_simplejava.business.arraycounting.integers.IntegersSum;
import com.blablaprincess.springboot_simplejava.business.common.digitsrepresentation.DigitsRepresentation;
import com.blablaprincess.springboot_simplejava.business.validation.ValidationException;
import com.blablaprincess.springboot_simplejava.business.validation.Validator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "validation.int.meaning-of-life.enabled", havingValue = "true")
public class MeaningOfLifeValidator implements Validator<Integer> {

    @Override
    public void validate(Integer value) throws ValidationException {
        Integer[] array = DigitsRepresentation.getDigitsArray(value);
        if (new IntegersSum().count(array) == 42) {
            throw new ValidationException("The sum of the digits of the number cannot be equal to 42");
        }
    }

}
