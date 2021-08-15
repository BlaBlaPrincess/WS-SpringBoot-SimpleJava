package com.blablaprincess.springboot_simplejava.business.controllercalls;

import com.blablaprincess.springboot_simplejava.business.common.datetime.DateTimeFormats;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ControllerCallsHistoryLastCallsArgs {

    @DateTimeFormat(pattern = DateTimeFormats.YEAR_TO_MINUTE)
    private LocalDateTime timestampAfter;

    @DateTimeFormat(pattern = DateTimeFormats.YEAR_TO_MINUTE)
    private LocalDateTime timestampBefore;

}
