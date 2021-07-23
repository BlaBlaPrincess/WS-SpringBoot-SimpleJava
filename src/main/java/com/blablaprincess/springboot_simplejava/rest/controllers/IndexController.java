package com.blablaprincess.springboot_simplejava.rest.controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class IndexController {

    @GetMapping("/")
    @ResponseStatus(HttpStatus.PERMANENT_REDIRECT)
    @Operation(summary = "redirects index to swagger ui")
    public RedirectView redirectIndexToSwaggerUi() {
        return new RedirectView("/swagger-ui/");
    }

}
