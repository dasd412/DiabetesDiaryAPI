package com.dasd412.domain.diary;


import com.dasd412.domain.diet.Diet;

import java.time.LocalDateTime;
import java.util.List;

public class DiabetesDiary {

    private  Long id;

    private int fastingPlasmaGlucose;//공복 혈당

    private int breakfastBloodSugar;//아침 식사 1시간 후 혈당

    private int lunchBloodSugar;//점식 식사 1시간 후 혈당

    private int dinnerBloodSugar;//저녁 식사 1시간 후 혈당
    
    private List<Diet> dietList;//식단 리스트

    private Writer writer;//작성한 사람

    private LocalDateTime createdAt;//만들어진 시기

    private LocalDateTime updatedAt;//갱신된 시기



}
