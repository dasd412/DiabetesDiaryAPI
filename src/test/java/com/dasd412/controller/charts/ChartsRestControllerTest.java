package com.dasd412.controller.charts;

import com.dasd412.domain.diary.DiabetesDiary;
import com.dasd412.domain.diary.DiabetesDiaryRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Profile("test")
public class ChartsRestControllerTest {

    @Autowired
    DiabetesDiaryRepository repository;

    private final Logger logger= LoggerFactory.getLogger(this.getClass());


    @After
    public void cleanUp(){
        repository.deleteAll();
    }

    @Transactional//<-LazyInitializationException: could not initialize proxy 에러를 해결하려면 트랜잭션 처리를 해야함.
    @Test
    public void 일지와_날짜를_저장하고_불러온다(){

        //given
        String year="2021";
        String month="03";
        String day="01";

        DiabetesDiary diary=new DiabetesDiary.Builder()
                .fastingPlasmaGlucose(100)
                .breakfastBloodSugar(95)
                .lunchBloodSugar(100)
                .dinnerBloodSugar(150)
                .writtenTime(year,month,day)
                .build();

        repository.save(diary);

        //when
        List<DiabetesDiary> list=repository.findAll();

        //then
        DiabetesDiary d=list.get(0);
        assertThat(d.getFastingPlasmaGlucose()).isEqualTo(diary.getFastingPlasmaGlucose());
        assertThat(d.getBreakfastBloodSugar()).isEqualTo(diary.getBreakfastBloodSugar());
        assertThat(d.getLunchBloodSugar()).isEqualTo(diary.getLunchBloodSugar());
        assertThat(d.getDinnerBloodSugar()).isEqualTo(diary.getDinnerBloodSugar());
        assertThat(d.getWrittenTime().toString()).isEqualTo(year+"-"+month+"-"+day+"T00:00");
    }

}