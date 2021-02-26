package com.dasd412.controller.calendar;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CalendarDTO {

    private String summary;

    private String startDate;

    private String startTime;

    private String endDate;

    private String endTime;

    private String description="";

    private String eventId;

    private String calendarId;

    public CalendarDTO() { }

    public String getSummary() {
        return summary;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getDescription() {
        return description;
    }

    public String getEventId() {
        return eventId;
    }

    public String getCalendarId() {
        return calendarId;
    }

    public Date getStartDateTime() throws ParseException {
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-ddHH:mm");
        return format.parse(startDate+startTime);
    }

    public Date getEndDateTime() throws ParseException {
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-ddHH:mm");
        return format.parse(endDate+endTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("summary",summary)
                .append("startDate",startDate)
                .append("startTime",startTime)
                .append("endDate",endDate)
                .append("endTime",endTime)
                .append("description",description)
                .append("eventId",eventId)
                .append("calendarId",calendarId)
                .toString();
    }
}
