package com.blablaprincess.springboot_simplejava.rest.actions;

import com.blablaprincess.springboot_simplejava.business.common.utils.LocalDateTimeUtils;
import com.blablaprincess.springboot_simplejava.business.controllercalls.ControllerCallDescriptionEntity;
import com.blablaprincess.springboot_simplejava.business.controllercalls.ControllerCallsHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ControllerCallsHistoryAction {

    private final ControllerCallsHistory controllerCallsHistoryService;
    private final LocalDateTimeUtils localDateTimeUtils;

    public List<ControllerCallDescriptionEntity> getCalls(@Nullable Integer amount) {
        if (amount != null) {
            return controllerCallsHistoryService.getLastCalls(amount);
        }
        return controllerCallsHistoryService.getCalls();
    }

    public List<ControllerCallDescriptionEntity> getCalls
            (@Nullable Integer amount, Duration offset, @Nullable LocalDateTime from) {
        if (from == null) {
            from = localDateTimeUtils.now();
        }
        LocalDateTime to = localDateTimeUtils.plus(from, offset);
        return getCallsByTimestamp(amount, from, to);
    }

    public List<ControllerCallDescriptionEntity> getCalls
            (@Nullable Integer amount, LocalDateTime from, @Nullable LocalDateTime to) {
        if (to == null) {
            to = localDateTimeUtils.now();
        }
        return getCallsByTimestamp(amount, from, to);
    }

    private List<ControllerCallDescriptionEntity> getCallsByTimestamp
            (@Nullable Integer amount, LocalDateTime from, LocalDateTime to) {
        OffsetDateTime utcFrom = OffsetDateTime.of(from, ZoneOffset.UTC);
        OffsetDateTime utcTo = OffsetDateTime.of(to, ZoneOffset.UTC);

        if (amount != null) {
            return controllerCallsHistoryService.getLastCalls(utcFrom, utcTo, amount);
        } else {
            return controllerCallsHistoryService.getCalls(utcFrom, utcTo);
        }
    }

}
