package com.blablaprincess.springboot_simplejava.rest;

import com.blablaprincess.springboot_simplejava.business.arraycounting.presenters.ArrayCountingAlgorithmsPresenterService;
import com.blablaprincess.springboot_simplejava.business.arraycounting.presenters.ArrayCountingAlgorithmsPresenterDto;
import com.blablaprincess.springboot_simplejava.business.digitsrepresentation.DigitsRepresentation;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class Controller {

    private final ArrayCountingAlgorithmsPresenterService<Integer> IntegersCountingAlgorithmsPresenterService;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.PERMANENT_REDIRECT)
    public RedirectView redirectIndex() {
        return new RedirectView("/counts/int");
    }

    @GetMapping("/counts/integer/**")
    @ResponseStatus(HttpStatus.PERMANENT_REDIRECT)
    public RedirectView redirectCountsForIntToInteger(HttpServletRequest request) {
        var relativeUri = request.getRequestURI().replace("/counts/integer", "");
        return new RedirectView("/counts/int" + relativeUri);
    }

    @GetMapping("/counts/int")
    @ResponseStatus(HttpStatus.OK)
    public String[] getCountsForInteger() {
        return IntegersCountingAlgorithmsPresenterService.getAlgorithms();
    }

    @GetMapping("/counts/int/{integer}")
    @ResponseStatus(HttpStatus.OK)
    public ArrayCountingAlgorithmsPresenterDto getCountsForInteger(@PathVariable int integer) {
        var array = DigitsRepresentation.getDigitsArray(integer);
        return IntegersCountingAlgorithmsPresenterService.getAlgorithmsCounts(array);
    }

}
