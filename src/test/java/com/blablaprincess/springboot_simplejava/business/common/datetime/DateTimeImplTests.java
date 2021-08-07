package com.blablaprincess.springboot_simplejava.business.common.datetime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class DateTimeImplTests {

    private final DateTimeImpl dateTime = new DateTimeImpl();
    private final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    @DisplayName("add()")
    @ParameterizedTest(name = "from {0} to {3}")
    @MethodSource("getAddCases")
    void add(String sDate, DateTime.DateField field, int amount, String sExpected) throws ParseException {
        // Arrange
        Date date = format.parse(sDate);
        Date expected = format.parse(sExpected);

        // Act
        Date result = dateTime.add(date, field, amount);

        // Assert
        assertEquals(expected, result);
    }

    static Stream<Arguments> getAddCases() {
        return Stream.of(
                arguments("10-10-2010 16:40:40", DateTime.DateField.YEARS,   5, "10-10-2015 16:40:40"),
                arguments("10-10-2010 16:40:40", DateTime.DateField.MONTHS,  5, "10-15-2010 16:40:40"),
                arguments("10-10-2010 16:40:40", DateTime.DateField.DAYS,    5, "15-10-2010 16:40:40"),
                arguments("10-10-2010 16:40:40", DateTime.DateField.HOURS,   5, "10-10-2010 21:40:40"),
                arguments("10-10-2010 16:40:40", DateTime.DateField.MINUTES, 5, "10-10-2010 16:45:40"),
                arguments("10-10-2010 16:40:40", DateTime.DateField.SECONDS, 5, "10-10-2010 16:40:45")
        );
    }

}