package com.dasd412.controller.diabetesDiary;

import com.dasd412.domain.diary.DiabetesDiary;
import com.dasd412.domain.user.Writer;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import static com.google.common.base.Preconditions.checkArgument;

public class DiabetesDiaryRequestDTO {

  @ApiModelProperty(value = "공복 혈당", required = true)
  private int fastingPlasmaGlucose;//공복 혈당(양수)

  @ApiModelProperty(value = "아침 식사 후 혈당", required = true)
  private int breakfastBloodSugar;//아침 식사 1시간 후 혈당(양수)

  @ApiModelProperty(value = "점심 식사 후 혈당", required = true)
  private int lunchBloodSugar;//점식 식사 1시간 후 혈당(양수)

  @ApiModelProperty(value = "저녁 식사 후 혈당", required = true)
  private int dinnerBloodSugar;//저녁 식사 1시간 후 혈당(양수)

  @ApiModelProperty(value = "비고")
  private String remark;

  @ApiModelProperty(value = "년", required = true)
  private String year;

  @ApiModelProperty(value = "월", required = true)
  private String month;

  @ApiModelProperty(value = "일", required = true)
  private String day;

  public DiabetesDiaryRequestDTO() {
  }


  public DiabetesDiaryRequestDTO(Writer writer) {
    this(0, 0, 0, 0, "", "", "", "");
  }

  public DiabetesDiaryRequestDTO(int fastingPlasmaGlucose, int breakfastBloodSugar,
      int lunchBloodSugar, int dinnerBloodSugar, String remark, String year, String month,
      String day) {
    this.fastingPlasmaGlucose = fastingPlasmaGlucose;
    this.breakfastBloodSugar = breakfastBloodSugar;
    this.lunchBloodSugar = lunchBloodSugar;
    this.dinnerBloodSugar = dinnerBloodSugar;
    this.remark = remark;
    this.year = year;
    this.month = month;
    this.day = day;
  }

  public DiabetesDiary toEntity() {
    return new DiabetesDiary.Builder()
        .fastingPlasmaGlucose(fastingPlasmaGlucose)
        .breakfastBloodSugar(breakfastBloodSugar)
        .lunchBloodSugar(lunchBloodSugar)
        .dinnerBloodSugar(dinnerBloodSugar)
        .remark(remark)
        .writtenTime(year, month, day)
        .build();
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
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


  static public class Builder {

    private int fastingPlasmaGlucose;
    private int breakfastBloodSugar;
    private int lunchBloodSugar;
    private int dinnerBloodSugar;
    private String remark;
    private String year;
    private String month;
    private String day;

    public Builder() {
    }

    public Builder(DiabetesDiaryRequestDTO diabetesDiary) {
      this.fastingPlasmaGlucose = diabetesDiary.fastingPlasmaGlucose;
      this.breakfastBloodSugar = diabetesDiary.breakfastBloodSugar;
      this.lunchBloodSugar = diabetesDiary.lunchBloodSugar;
      this.dinnerBloodSugar = diabetesDiary.dinnerBloodSugar;
      this.remark = diabetesDiary.remark;
      this.year = diabetesDiary.year;
      this.month = diabetesDiary.month;
      this.day = diabetesDiary.day;
    }

    public DiabetesDiaryRequestDTO.Builder fastingPlasmaGlucose(int fastingPlasmaGlucose) {
      checkArgument(fastingPlasmaGlucose > 0, "fastingPlasmaGlucose must be positive number");
      this.fastingPlasmaGlucose = fastingPlasmaGlucose;
      return this;
    }

    public DiabetesDiaryRequestDTO.Builder breakfastBloodSugar(int breakfastBloodSugar) {
      checkArgument(breakfastBloodSugar > 0, "breakfastBloodSugar must be positive number");
      this.breakfastBloodSugar = breakfastBloodSugar;
      return this;
    }

    public DiabetesDiaryRequestDTO.Builder lunchBloodSugar(int lunchBloodSugar) {
      checkArgument(lunchBloodSugar > 0, "lunchBloodSugar must be positive number");
      this.lunchBloodSugar = lunchBloodSugar;
      return this;
    }

    public DiabetesDiaryRequestDTO.Builder dinnerBloodSugar(int dinnerBloodSugar) {
      checkArgument(dinnerBloodSugar > 0, "dinnerBloodSugar must be positive number");
      this.dinnerBloodSugar = dinnerBloodSugar;
      return this;
    }

    public DiabetesDiaryRequestDTO.Builder remark(String remark) {
      this.remark = remark;
      return this;
    }

    public DiabetesDiaryRequestDTO.Builder year(String year) {
      this.year = year;
      return this;
    }


    public DiabetesDiaryRequestDTO.Builder month(String month) {
      this.month = month;
      return this;
    }


    public DiabetesDiaryRequestDTO.Builder day(String day) {
      this.day = day;
      return this;
    }


    public DiabetesDiaryRequestDTO build() {
      return new DiabetesDiaryRequestDTO(fastingPlasmaGlucose, breakfastBloodSugar, lunchBloodSugar,
          dinnerBloodSugar, remark, year, month, day);
    }

  }


}
