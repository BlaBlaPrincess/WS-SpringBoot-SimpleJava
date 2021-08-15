package com.blablaprincess.springboot_simplejava.rest.controllers;

import com.blablaprincess.springboot_simplejava.business.controllercalls.ControllerCallDescriptionEntity;
import com.blablaprincess.springboot_simplejava.business.controllercalls.ControllerCallsHistoryLastCallsArgs;
import com.blablaprincess.springboot_simplejava.business.controllercalls.ControllerCallsHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/calls")
@RequiredArgsConstructor
public class ControllerCallsHistoryController {

    private final ControllerCallsHistoryService controllerCallsHistoryService;

    @GetMapping("")
    @Operation(summary = "get list of controller calls by specific params")
    public List<ControllerCallDescriptionEntity> getControllerCalls(ControllerCallsHistoryLastCallsArgs args) {
        return controllerCallsHistoryService.getLastCalls(args);
    }

}
