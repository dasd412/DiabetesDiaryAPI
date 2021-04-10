package com.dasd412.controller.tag;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class TagResponseDTO {

  private String foodName;
  private boolean saveSuccess;

  public TagResponseDTO() {
  }

  public TagResponseDTO(String foodName, boolean saveSuccess) {
    this.foodName = foodName;
    this.saveSuccess = saveSuccess;
  }

  public String getFoodName() {
    return foodName;
  }

  public boolean isExist() {
    return saveSuccess;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        .append("foodName", foodName)
        .append("exist", saveSuccess)
        .toString();
  }
}
