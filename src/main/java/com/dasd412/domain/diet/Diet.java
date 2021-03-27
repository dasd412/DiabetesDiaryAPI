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

  protected Diet() {
  }

  public Diet(String foodName) {
    checkArgument(isNotEmpty(foodName), "foodName must be provided");
    checkArgument(foodName.length() >= 2 && foodName.length() <= 50,
        "foodName length must be between 2 and 50 characters!");
    this.foodName = foodName;
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

  public String getFoodName() { return foodName; }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        .append("id", id)
        .append("foodName", foodName)
        .toString();
  }
}
