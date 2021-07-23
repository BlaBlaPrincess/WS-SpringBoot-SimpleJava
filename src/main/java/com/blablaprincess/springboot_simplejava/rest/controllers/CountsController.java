package com.blablaprincess.springboot_simplejava.rest.controllers;

import com.blablaprincess.springboot_simplejava.business.arraycounting.presenters.ArrayCountingAlgorithmsPresenter;
import com.blablaprincess.springboot_simplejava.business.arraycounting.presenters.ArrayCountingAlgorithmsPresenterDto;
import com.blablaprincess.springboot_simplejava.business.digitsrepresentation.DigitsRepresentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/counts")
@RequiredArgsConstructor
public class CountsController {

    private final ArrayCountingAlgorithmsPresenter<Integer> integersCountingAlgorithmsPresenterService;

    @GetMapping("/int")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "get list of all counting algorithms for array of digits")
    public String[] getCountsForInteger() {
        return integersCountingAlgorithmsPresenterService.getAlgorithms();
    }

    @GetMapping("/int/{integer}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "get algorithm calculations for an array of digits")
    public ArrayCountingAlgorithmsPresenterDto getCountsForInteger(@PathVariable int integer) {
        var array = DigitsRepresentation.getDigitsArray(integer);
        return integersCountingAlgorithmsPresenterService.getAlgorithmsCounts(array);
    }

    @GetMapping("/integer/**")
    @ResponseStatus(HttpStatus.PERMANENT_REDIRECT)
    @Operation(summary = "redirects counts/integer/** to counts/int/**")
    public RedirectView redirectCountsForIntegerToInt(
            @Parameter(hidden = true) HttpServletRequest request) {
        var relativeUri = request.getRequestURI().replace("/counts/integer", "");
        return new RedirectView("/counts/int" + relativeUri);
    }

}
