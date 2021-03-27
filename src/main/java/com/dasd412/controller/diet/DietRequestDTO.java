package com.dasd412.controller.diet;

import com.dasd412.domain.diet.EatTime;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class DietRequestDTO {

  private final String foodName;

  private final EatTime eatTime;

  public DietRequestDTO(String foodName, EatTime eatTime) {
    this.foodName = foodName;
    this.eatTime = eatTime;
  }

  public String getFoodName() {
    return foodName;
  }

  public EatTime getEatTime() {
    return eatTime;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        .append("foodName", foodName)
        .append("eatTime", eatTime)
        .toString();
  }

}
