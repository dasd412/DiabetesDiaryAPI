package com.dasd412.domain.diary;

import java.util.List;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Profile("test")
public class DiabetesDiaryRepositoryTest {

    @Autowired
    DiabetesDiaryRepository repository;

    @After
    public void cleanUp(){
        repository.deleteAll();
    }

    @Test
    public void 일지를_저장하고_불러온다(){

        //given
        DiabetesDiary diary=new DiabetesDiary.Builder()
                .fastingPlasmaGlucose(100)
                .breakfastBloodSugar(95)
                .lunchBloodSugar(100)
                .dinnerBloodSugar(150)
                .writer(null)
                .build();

        repository.save(diary);

        //when
        List<DiabetesDiary>list=repository.findAll();

        //then
        DiabetesDiary d=list.get(0);
        assertThat(d.getFastingPlasmaGlucose()).isEqualTo(diary.getFastingPlasmaGlucose());
        assertThat(d.getBreakfastBloodSugar()).isEqualTo(diary.getBreakfastBloodSugar());
        assertThat(d.getLunchBloodSugar()).isEqualTo(diary.getLunchBloodSugar());
        assertThat(d.getDinnerBloodSugar()).isEqualTo(diary.getDinnerBloodSugar());

    }

}