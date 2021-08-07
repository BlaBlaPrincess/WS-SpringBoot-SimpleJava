package com.blablaprincess.springboot_simplejava.rest.actions;

import com.blablaprincess.springboot_simplejava.business.arraycounting.presenters.ArrayCountingAlgorithmsPresenter;
import com.blablaprincess.springboot_simplejava.business.arraycounting.presenters.ArrayCountingAlgorithmsPresenterDto;
import com.blablaprincess.springboot_simplejava.business.common.digitsrepresentation.DigitsRepresentation;
import com.blablaprincess.springboot_simplejava.business.validation.ValidatorsCollection;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IntegersCountingAlgorithmsPresenterAction {

    private final ArrayCountingAlgorithmsPresenter<Integer> integersCountingAlgorithmsPresenterService;
    private final ValidatorsCollection<Integer> validators;

    public String[] getAlgorithms() {
        return integersCountingAlgorithmsPresenterService.getAlgorithms();
    }

    public ArrayCountingAlgorithmsPresenterDto getAlgorithmsCounts(int integer) {
        validators.validate(integer);
        var array = DigitsRepresentation.getDigitsArray(integer);
        return integersCountingAlgorithmsPresenterService.getAlgorithmsCounts(array);
    }

}
