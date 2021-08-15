package com.blablaprincess.springboot_simplejava.rest.controllers;

import com.blablaprincess.springboot_simplejava.business.methodcalls.MethodCallDescriptionEntity;
import com.blablaprincess.springboot_simplejava.business.methodcalls.MethodCallsHistoryLastCallsArgs;
import com.blablaprincess.springboot_simplejava.business.methodcalls.MethodCallsHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/calls")
@RequiredArgsConstructor
public class MethodCallsHistoryController {

    private final MethodCallsHistoryService methodCallsHistoryService;

    @GetMapping("")
    @Operation(summary = "get list of controller calls by specific params")
    public List<MethodCallDescriptionEntity> getControllerCalls(MethodCallsHistoryLastCallsArgs args) {
        return methodCallsHistoryService.getLastCalls(args);
    }

}
