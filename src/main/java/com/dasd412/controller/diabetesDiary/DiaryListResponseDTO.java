package com.dasd412.controller.diabetesDiary;

import com.dasd412.domain.diary.DiabetesDiary;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;

public class DiaryListResponseDTO {

    private Long id;

    private int fastingPlasmaGlucose;

    private int breakfastBloodSugar;

    private int lunchBloodSugar;

    private int dinnerBloodSugar;

    private String remark;

    private LocalDateTime createAt;

    private LocalDateTime updatedAt;

    private LocalDateTime writtenTime;

    public DiaryListResponseDTO() { }

    public DiaryListResponseDTO(DiabetesDiary entity) {
        this.id = entity.getId();
        this.fastingPlasmaGlucose = entity.getFastingPlasmaGlucose();
        this.breakfastBloodSugar = entity.getBreakfastBloodSugar();
        this.lunchBloodSugar = entity.getLunchBloodSugar();
        this.dinnerBloodSugar = entity.getDinnerBloodSugar();
        this.remark = entity.getRemark();
        this.createAt=entity.getCreateAt();
        this.updatedAt=entity.getUpdatedAt();
        this.writtenTime=entity.getWrittenTime();
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

    public String getRemark() {
        return remark;
    }

    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public LocalDateTime getCreateAt() { return createAt; }

    public LocalDateTime getWrittenTime() { return writtenTime; }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id",id)
                .append("fastingPlasmaGlucose",fastingPlasmaGlucose)
                .append("breakfastBloodSugar",breakfastBloodSugar)
                .append("lunchBloodSugar",lunchBloodSugar)
                .append("dinnerBloodSugar",dinnerBloodSugar)
                .append("remark",remark)
                .append("createAt",createAt)
                .append("updatedAt",updatedAt)
                .append("writtenTime",writtenTime)
                .toString();
    }
}
