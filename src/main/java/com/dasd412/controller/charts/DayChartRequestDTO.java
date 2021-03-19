package com.dasd412.controller.charts;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class DayChartRequestDTO {
    /*
    @DateTimeFormat 등으로 LocalDateTime을 파싱하는 방법이 있으나, 적용이 안되는 관계로
    String으로 받고 서비스 레이어에서 파싱하는 법을 택하였다.
     */

    private String startDate;

    private String endDate;

    public DayChartRequestDTO() {}

    public DayChartRequestDTO(String startDate, String endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getEndDate() { return endDate; }

    public String getStartDate() { return startDate; }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("startDate",startDate)
                .append("endDate",endDate)
                .toString();
    }
}
