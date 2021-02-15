package com.dasd412.domain.diet;

import com.dasd412.domain.diary.DiabetesDiary;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class HashTag {
    /*
    Diet와 DiabetesDiary 간의 다대다 관계를 해결하기 위한 연결 테이블 엔티티
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name="diabetesDiary_id")
    private DiabetesDiary diabetesDiary;


    @ManyToOne
    @JoinColumn(name="diet_id")
    private Diet diet;

}
