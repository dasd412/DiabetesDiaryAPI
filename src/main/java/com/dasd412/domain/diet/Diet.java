package com.dasd412.domain.diet;

import com.dasd412.domain.diary.DiabetesDiary;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Entity
public class Diet {//식단

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore//JSON에서 양방향 참조 방지
    @ManyToOne(fetch = FetchType.LAZY)//다대일 관계
    private DiabetesDiary diary;

    private String foodName;

    private EatTime eatTime;//식사시간을 나타내는 Enum


    public Diet(){}

    public Diet(String foodName){
      this(foodName,null);
    }

    public Diet(String foodName, EatTime eatTime){
        checkArgument(isNotEmpty(foodName),"foodName must be provided");
        checkArgument(foodName.length()>=2&&foodName.length()<=50,"foodName length must be between 2 and 50 characters!");
        this.foodName=foodName;
        this.eatTime=eatTime;
    }

    public String getFoodName() {
        return foodName;
    }

    public Optional<EatTime> getEatTime() {
        return Optional.ofNullable(eatTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("foodName",foodName)
                .append("eatTime",eatTime)
                .toString();
    }
}
