package com.dasd412.domain.diet;

import com.dasd412.domain.diary.DiabetesDiary;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
public class HashTag {
    /*
    Diet와 DiabetesDiary 간의 다대다 관계를 해결하기 위한 연결 테이블 엔티티
     */

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;


  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "diabetesDiary_id")
  private DiabetesDiary diabetesDiary;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "diet_id")
  private Diet diet;

  @Enumerated(EnumType.STRING)//Enum 값 자체를 저장
  private EatTime eatTime;//식사시간을 나타내는 Enum

  public HashTag(DiabetesDiary diabetesDiary, Diet diet, EatTime eatTime) {
    this.diabetesDiary = diabetesDiary;
    this.diet = diet;
    this.eatTime=eatTime;
  }

  public Long getId() { return id; }

  public DiabetesDiary getDiabetesDiary() { return diabetesDiary; }

  public Diet getDiet() { return diet; }

  public EatTime getEatTime() { return eatTime; }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        .append("id",id)
        .append("eat time ",eatTime)
        .append("diabetes diary",diabetesDiary)
        .append("diet",diet)
        .toString();
  }
}
