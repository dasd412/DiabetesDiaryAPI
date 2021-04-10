package com.dasd412.controller.tag;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
public class TagRequestDTO {

  private String foodName;

  public TagRequestDTO() {
  }

  public TagRequestDTO(String foodName) {
    this.foodName = foodName;
  }

  public String getFoodName() {
    return foodName;
  }

  @Override
  public String toString(){
    return new ToStringBuilder(this,ToStringStyle.SHORT_PREFIX_STYLE)
        .append("foodName",foodName)
        .toString();
  }
}
