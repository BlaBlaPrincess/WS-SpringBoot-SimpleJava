package com.blablaprincess.springboot_simplejava.rest.controllers;

import com.blablaprincess.springboot_simplejava.business.afterhandlingprocessor.ProcessAfterHandlingWith;
import com.blablaprincess.springboot_simplejava.business.arraycounting.presenters.ArrayCountingAlgorithmsPresenterDto;
import com.blablaprincess.springboot_simplejava.business.methodcalls.MethodCallsHistoryAfterHandlingProcessor;
import com.blablaprincess.springboot_simplejava.business.notifiers.telegram.ahp.TelegramBotNotifierAfterHandlingProcessor;
import com.blablaprincess.springboot_simplejava.rest.actions.IntegersCountingAlgorithmsPresenterAction;
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
@ProcessAfterHandlingWith({
        MethodCallsHistoryAfterHandlingProcessor.class,
        TelegramBotNotifierAfterHandlingProcessor.class
})
public class CountsController {

    private final IntegersCountingAlgorithmsPresenterAction integersCountingAlgorithmsPresenterAction;

    @GetMapping("/int")
    @Operation(summary = "get list of all counting algorithms for array of digits")
    public String[] getCountsForInteger() {
        return integersCountingAlgorithmsPresenterAction.getAlgorithms();
    }

    @GetMapping("/int/{integer}")
    @Operation(summary = "get algorithm calculations for an array of digits")
    public ArrayCountingAlgorithmsPresenterDto getCountsForInteger(@PathVariable int integer) {
        return integersCountingAlgorithmsPresenterAction.getAlgorithmsCounts(integer);
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
