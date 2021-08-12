package com.blablaprincess.springboot_simplejava.rest.controllers;

import com.blablaprincess.springboot_simplejava.business.controllercalls.ControllerCallDescriptionEntity;
import com.blablaprincess.springboot_simplejava.rest.actions.ControllerCallsHistoryAction;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.convert.DurationFormat;
import org.springframework.boot.convert.DurationStyle;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/calls")
@RequiredArgsConstructor
public class ControllerCallsHistoryController {

    private final ControllerCallsHistoryAction controllerCallsHistoryAction;
    private final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    @GetMapping("")
    @Operation(summary = "get list of all controller calls")
    public List<ControllerCallDescriptionEntity> getControllerCalls(Integer amount) {
        return controllerCallsHistoryAction.getCalls(amount);
    }

    @GetMapping("/from")
    @Operation(summary = "get list of controller calls by specific params")
    public List<ControllerCallDescriptionEntity> getControllerCalls
            (Integer amount,
             @RequestParam(required = true) @DurationFormat(DurationStyle.SIMPLE) Duration offset,
             @RequestParam(required = false) @DateTimeFormat(pattern = DATE_TIME_PATTERN) LocalDateTime from) {
        return controllerCallsHistoryAction.getCalls(amount, offset, from);
    }

    @GetMapping("/between")
    @Operation(summary = "get list of controller calls by specific params")
    public List<ControllerCallDescriptionEntity> getControllerCalls
            (Integer amount,
             @RequestParam(required = true) @DateTimeFormat(pattern = DATE_TIME_PATTERN) LocalDateTime from,
             @RequestParam(required = false) @DateTimeFormat(pattern = DATE_TIME_PATTERN) LocalDateTime to) {
        return controllerCallsHistoryAction.getCalls(amount, from, to);
    }

}
