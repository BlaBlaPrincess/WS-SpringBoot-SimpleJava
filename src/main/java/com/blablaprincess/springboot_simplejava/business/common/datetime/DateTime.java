package com.blablaprincess.springboot_simplejava.business.common.datetime;

import java.util.Date;

public interface DateTime {

    enum DateField {
        YEARS,
        MONTHS,
        DAYS,
        HOURS,
        MINUTES,
        SECONDS
    }

    Date getDate();
    Date add(Date date, DateField field, int amount);
}
