package com.dasd412.controller.charts;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;

public class DayChartRequestDTO {

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public DayChartRequestDTO() {}

    public DayChartRequestDTO(LocalDateTime startDate, LocalDateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LocalDateTime getEndDate() { return endDate; }

    public LocalDateTime getStartDate() { return startDate; }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("startDate",startDate)
                .append("endDate",endDate)
                .toString();
    }
}
