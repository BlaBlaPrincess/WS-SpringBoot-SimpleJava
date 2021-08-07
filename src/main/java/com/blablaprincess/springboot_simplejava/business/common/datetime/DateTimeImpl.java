package com.blablaprincess.springboot_simplejava.business.common.datetime;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class DateTimeImpl implements DateTime {

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private final Map<DateField, Integer> fieldToCalendarConstantMap;
    private final String illegalFieldMessage;

    public DateTimeImpl() {
        fieldToCalendarConstantMap = new HashMap<DateField, Integer>() {{
            put(DateField.YEARS,   Calendar.YEAR);
            put(DateField.MONTHS,  Calendar.MONTH);
            put(DateField.DAYS,    Calendar.DATE);
            put(DateField.HOURS,   Calendar.HOUR);
            put(DateField.MINUTES, Calendar.MINUTE);
            put(DateField.SECONDS, Calendar.SECOND);
        }};
        Set<String> keySet = fieldToCalendarConstantMap.keySet()
                .stream()
                .map(Enum::toString).
                collect(Collectors.toSet());
        String keys = String.join(",", keySet);
        illegalFieldMessage = "Invalid DateField argument value. Expected: " + keys;
    }

    @Override
    public Date getDate() {
        return new Date();
    }

    @SuppressWarnings("MagicConstant")
    @Override
    public Date add(Date date, DateField field, int amount) {
        if (!fieldToCalendarConstantMap.containsKey(field)) {
            throw new IllegalArgumentException(illegalFieldMessage);
        }
        int cField = fieldToCalendarConstantMap.get(field);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(cField, amount);
        return calendar.getTime();
    }

}
