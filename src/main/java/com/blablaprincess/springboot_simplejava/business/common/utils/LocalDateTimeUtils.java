package com.blablaprincess.springboot_simplejava.business.common.utils;

import java.time.Duration;
import java.time.LocalDateTime;

public interface LocalDateTimeUtils {
    LocalDateTime now();
    LocalDateTime plus(LocalDateTime dateTime, Duration duration);
}
