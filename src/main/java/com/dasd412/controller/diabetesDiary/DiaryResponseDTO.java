package com.dasd412.controller.diabetesDiary;

import com.dasd412.domain.diary.DiabetesDiary;
import com.dasd412.domain.diary.Writer;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class DiaryResponseDTO {

    private Long id;
    private int fastingPlasmaGlucose;//공복 혈당(양수)
    private int breakfastBloodSugar;//아침 식사 1시간 후 혈당(양수)
    private int lunchBloodSugar;//점식 식사 1시간 후 혈당(양수)
    private int dinnerBloodSugar;//저녁 식사 1시간 후 혈당(양수)
    private Writer writer;
    private String remark;

    public DiaryResponseDTO() { }

    public DiaryResponseDTO(DiabetesDiary entity) {
        this.id = entity.getId();
        this.fastingPlasmaGlucose = entity.getFastingPlasmaGlucose();
        this.breakfastBloodSugar = entity.getBreakfastBloodSugar();
        this.lunchBloodSugar = entity.getLunchBloodSugar();
        this.dinnerBloodSugar = entity.getDinnerBloodSugar();
        this.writer = entity.getWriter();
        this.remark = entity.getRemark();
    }

    public Long getId() {
        return id;
    }

    public int getFastingPlasmaGlucose() {
        return fastingPlasmaGlucose;
    }

    public int getBreakfastBloodSugar() {
        return breakfastBloodSugar;
    }

    public int getLunchBloodSugar() {
        return lunchBloodSugar;
    }

    public int getDinnerBloodSugar() {
        return dinnerBloodSugar;
    }

    public Writer getWriter() {
        return writer;
    }

    public String getRemark() {
        return remark;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id",id)
                .append("fastingPlasmaGlucose",fastingPlasmaGlucose)
                .append("breakfastBloodSugar",breakfastBloodSugar)
                .append("lunchBloodSugar",lunchBloodSugar)
                .append("dinnerBloodSugar",dinnerBloodSugar)
                .append("writer",writer)
                .append("remark",remark)
                .toString();
    }
}
