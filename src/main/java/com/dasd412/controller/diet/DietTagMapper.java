package com.dasd412.controller.diet;

import com.dasd412.domain.diet.EatTime;

public class DietTagMapper {
  //    DietRequestDTO를 서비스 레이어로 보낼 때 잠시 프로퍼티를 묶을 때 사용하는  클래스

  private final String foodName;

  private final EatTime eatTime;

  public DietTagMapper(String foodName, EatTime eatTime) {
    this.foodName = foodName;
    this.eatTime = eatTime;
  }

  public EatTime getEatTime() { return eatTime; }

  public String getFoodName() { return foodName; }
}
