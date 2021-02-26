package com.dasd412.controller.calendar;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class CalendarRequestDTO {

    private final String year;
    private final String month;
    private final String day;

    public CalendarRequestDTO(String year, String month, String day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public String getYear() {
        return year;
    }

    public String getMonth() {
        return month;
    }

    public String getDay() {
        return day;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("year",year)
                .append("month",month)
                .append("day",day)
                .toString();
    }
}

