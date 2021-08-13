package com.blablaprincess.springboot_simplejava.business.controllercalls;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ControllerCallsHistoryLastCallsArgs {

    @ToString.Exclude
    private final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm";

    @DateTimeFormat(pattern = DATE_TIME_PATTERN)
    private LocalDateTime timestampAfter;

    @DateTimeFormat(pattern = DATE_TIME_PATTERN)
    private LocalDateTime timestampBefore;

}
