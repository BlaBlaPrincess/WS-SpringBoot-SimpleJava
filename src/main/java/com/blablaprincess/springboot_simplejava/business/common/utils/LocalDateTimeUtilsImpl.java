package com.blablaprincess.springboot_simplejava.business.common.utils;

import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class LocalDateTimeUtilsImpl implements LocalDateTimeUtils {

    @Override
    public LocalDateTime now() {
        return LocalDateTime.now();
    }

    @Override
    public LocalDateTime plus(LocalDateTime dateTime, Duration duration) {
        return dateTime.plus(duration);
    }

}
