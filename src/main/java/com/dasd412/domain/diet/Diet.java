package com.dasd412.domain.diet;

import java.util.Objects;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

import java.util.Set;
import java.util.HashSet;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Entity
public class Diet {//식단

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;


  @OneToMany(mappedBy = "diet")
  private Set<HashTag> diaryList = new HashSet<>();

  private String foodName;

  @Enumerated(EnumType.STRING)//Enum 값 자체를 저장
  private EatTime eatTime;//식사시간을 나타내는 Enum


  protected Diet() {
  }

  public Diet(String foodName) {
    this(foodName, null);
  }

  public Diet(String foodName, EatTime eatTime) {
    checkArgument(isNotEmpty(foodName), "foodName must be provided");
    checkArgument(foodName.length() >= 2 && foodName.length() <= 50,
        "foodName length must be between 2 and 50 characters!");
    this.foodName = foodName;
    this.eatTime = eatTime;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Diet diet = (Diet) obj;
    return Objects.equals(this.id, diet.id);
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
