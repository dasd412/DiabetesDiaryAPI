package com.dasd412.controller.charts;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.List;

public class AverageChartDTO {

    private final double[]monthlyAverage=new double[12];

    public AverageChartDTO() { }

    public AverageChartDTO(List<Object[]>monthlyData) {
        for (Object[] monthlyDatum : monthlyData) {
            int month = (int) monthlyDatum[0];
            BigDecimal average = (BigDecimal) monthlyDatum[1];
            monthlyAverage[month - 1] = average.doubleValue();
        }
    }

    public double[] getMonthlyAverage() {
        return monthlyAverage;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("monthlyAverage",monthlyAverage)
                .toString();
    }
}
