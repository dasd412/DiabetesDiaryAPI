package com.dasd412.controller.diabetesDiary;

import com.dasd412.domain.diary.DiabetesDiary;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DiaryResponseDTO {

    /*
    모델에 대한 스웨거 적용은 엔티티 클래스가 아닌 dto 클래스에 적용!!
     */

  @ApiModelProperty(value = "PK", required = true)
  private Long id;

  @ApiModelProperty(value = "공복 혈당", required = true)
  private int fastingPlasmaGlucose;//공복 혈당(양수)

  @ApiModelProperty(value = "아침 식사 후 혈당", required = true)
  private int breakfastBloodSugar;//아침 식사 1시간 후 혈당(양수)

  @ApiModelProperty(value = "점심 식사 후 혈당", required = true)
  private int lunchBloodSugar;//점식 식사 1시간 후 혈당(양수)

  @ApiModelProperty(value = "저녁 식사 후 혈당", required = true)
  private int dinnerBloodSugar;//저녁 식사 1시간 후 혈당(양수)

  @ApiModelProperty(value = "비고", required = true)
  private String remark;

  @ApiModelProperty(value = "년", required = true)
  private String year;

  @ApiModelProperty(value = "월", required = true)
  private String month;

  @ApiModelProperty(value = "일", required = true)
  private String day;

  public DiaryResponseDTO() {
  }

  public DiaryResponseDTO(DiabetesDiary entity) {
    this.id = entity.getId();
    this.fastingPlasmaGlucose = entity.getFastingPlasmaGlucose();
    this.breakfastBloodSugar = entity.getBreakfastBloodSugar();
    this.lunchBloodSugar = entity.getLunchBloodSugar();
    this.dinnerBloodSugar = entity.getDinnerBloodSugar();
    this.remark = entity.getRemark();
    LocalDateTime date = entity.getWrittenTime();

    String[] array = date.format(DateTimeFormatter.ISO_DATE).split("-");
    this.year = array[0];
    this.month = array[1];
    this.day = array[2];
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
        .append("id", id)
        .append("fastingPlasmaGlucose", fastingPlasmaGlucose)
        .append("breakfastBloodSugar", breakfastBloodSugar)
        .append("lunchBloodSugar", lunchBloodSugar)
        .append("dinnerBloodSugar", dinnerBloodSugar)
        .append("remark", remark)
        .append("year", year)
        .append("month", month)
        .append("day", day)
        .toString();
  }
}
