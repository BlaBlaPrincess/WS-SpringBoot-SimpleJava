package com.blablaprincess.springboot_simplejava.rest.controllers;

import com.blablaprincess.springboot_simplejava.business.common.datetime.DateTime;
import com.blablaprincess.springboot_simplejava.business.controllercalls.ControllerCallDescriptionEntity;
import com.blablaprincess.springboot_simplejava.business.controllercalls.ControllerCallsHistory;
import com.blablaprincess.springboot_simplejava.business.notifiers.telegram.TelegramBotNotifierAfterDispatchingProcessor;
import com.blablaprincess.springboot_simplejava.rest.afterdispatchingprocessor.ProcessAfterDispatchingWith;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/calls")
@RequiredArgsConstructor
@ProcessAfterDispatchingWith({
        TelegramBotNotifierAfterDispatchingProcessor.class
})
public class ControllerCallsHistoryController {

    private final DateTime dateTime;
    private final ControllerCallsHistory controllerCallsHistoryService;

    @GetMapping("")
    @Operation(summary = "get list of controller calls by specific params")
    public List<ControllerCallDescriptionEntity> getControllerCalls
            (Integer amount, Integer days, Integer hours, Integer minutes) {

        if (days != null || hours !=null || minutes != null) {

            Date to = dateTime.getDate();
            Date from = (Date) to.clone();

            if (days != null) {
                from = dateTime.add(from, DateTime.DateField.DAYS, -days);
            }
            if (hours != null) {
                from = dateTime.add(from, DateTime.DateField.HOURS, -hours);
            }
            if (minutes != null) {
                from = dateTime.add(from, DateTime.DateField.MINUTES, -minutes);
            }

            if (amount != null) {
                return controllerCallsHistoryService.getLastCalls(from, to, amount);

            } else {
                return controllerCallsHistoryService.getCalls(from, to);
            }

        } else if (amount != null) {
            return controllerCallsHistoryService.getLastCalls(amount);

        } else {
            return controllerCallsHistoryService.getCalls();
        }
    }

}
