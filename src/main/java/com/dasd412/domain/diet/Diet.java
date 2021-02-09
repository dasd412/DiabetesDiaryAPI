package com.dasd412.domain.diet;

import com.dasd412.domain.diary.DiabetesDiary;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Diet {//식단

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore//JSON에서 양방향 참조 방지
    @ManyToOne(fetch = FetchType.LAZY)//다대일 관계
    private DiabetesDiary diary;

    public Diet(){}
}
