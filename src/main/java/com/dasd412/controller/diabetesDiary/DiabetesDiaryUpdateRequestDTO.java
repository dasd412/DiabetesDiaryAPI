package com.dasd412.controller.diabetesDiary;


import com.dasd412.controller.diet.DietRequestDTO;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

public class DiabetesDiaryUpdateRequestDTO {

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

  private List<DietRequestDTO> dietRequestDTOList;

  public DiabetesDiaryUpdateRequestDTO() {
  }


  public DiabetesDiaryUpdateRequestDTO(int fastingPlasmaGlucose, int breakfastBloodSugar,
      int lunchBloodSugar, int dinnerBloodSugar, String remark,
      List<DietRequestDTO> dietRequestDTOList) {
    this.fastingPlasmaGlucose = fastingPlasmaGlucose;
    this.breakfastBloodSugar = breakfastBloodSugar;
    this.lunchBloodSugar = lunchBloodSugar;
    this.dinnerBloodSugar = dinnerBloodSugar;
    this.remark = remark;
    this.dietRequestDTOList = dietRequestDTOList;
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

  public List<DietRequestDTO> getDietRequestDTOList() {
    return dietRequestDTOList;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        .append("fastingPlasmaGlucose", fastingPlasmaGlucose)
        .append("breakfastBloodSugar", breakfastBloodSugar)
        .append("lunchBloodSugar", lunchBloodSugar)
        .append("dinnerBloodSugar", dinnerBloodSugar)
        .append("remark", remark)
        .append("dietList", dietRequestDTOList)
        .toString();
  }

  static public class Builder {

    private int fastingPlasmaGlucose;
    private int breakfastBloodSugar;
    private int lunchBloodSugar;
    private int dinnerBloodSugar;
    private String remark;
    private List<DietRequestDTO> dietRequestDTOList;

    public Builder() {
    }

    public Builder(DiabetesDiaryUpdateRequestDTO diabetesDiary) {
      this.fastingPlasmaGlucose = diabetesDiary.fastingPlasmaGlucose;
      this.breakfastBloodSugar = diabetesDiary.breakfastBloodSugar;
      this.lunchBloodSugar = diabetesDiary.lunchBloodSugar;
      this.dinnerBloodSugar = diabetesDiary.dinnerBloodSugar;
      this.remark = diabetesDiary.remark;
      this.dietRequestDTOList = diabetesDiary.dietRequestDTOList;
    }

    public DiabetesDiaryUpdateRequestDTO.Builder fastingPlasmaGlucose(int fastingPlasmaGlucose) {
      checkArgument(fastingPlasmaGlucose > 0, "fastingPlasmaGlucose must be positive number");
      this.fastingPlasmaGlucose = fastingPlasmaGlucose;
      return this;
    }

    public DiabetesDiaryUpdateRequestDTO.Builder breakfastBloodSugar(int breakfastBloodSugar) {
      checkArgument(breakfastBloodSugar > 0, "breakfastBloodSugar must be positive number");
      this.breakfastBloodSugar = breakfastBloodSugar;
      return this;
    }

    public DiabetesDiaryUpdateRequestDTO.Builder lunchBloodSugar(int lunchBloodSugar) {
      checkArgument(lunchBloodSugar > 0, "lunchBloodSugar must be positive number");
      this.lunchBloodSugar = lunchBloodSugar;
      return this;
    }

    public DiabetesDiaryUpdateRequestDTO.Builder dinnerBloodSugar(int dinnerBloodSugar) {
      checkArgument(dinnerBloodSugar > 0, "dinnerBloodSugar must be positive number");
      this.dinnerBloodSugar = dinnerBloodSugar;
      return this;
    }

    public DiabetesDiaryUpdateRequestDTO.Builder remark(String remark) {
      this.remark = remark;
      return this;
    }

    public DiabetesDiaryUpdateRequestDTO.Builder dietList(List<DietRequestDTO> dietRequestDTOList) {
      checkArgument(dietRequestDTOList.size() > 0 && dietRequestDTOList.size() < 4,
          "diet size must be between 1 and 3");
      this.dietRequestDTOList = dietRequestDTOList;
      return this;
    }

    public DiabetesDiaryUpdateRequestDTO build() {
      return new DiabetesDiaryUpdateRequestDTO(fastingPlasmaGlucose, breakfastBloodSugar,
          lunchBloodSugar, dinnerBloodSugar, remark, dietRequestDTOList);
    }

  }


}
