package com.dasd412.domain.diary;

import java.util.List;
import java.util.Optional;

import com.dasd412.domain.user.Email;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Profile("test")
public class DiabetesDiaryRepositoryTest {

    @Autowired
    DiabetesDiaryRepository repository;

    @Autowired
    private WriterRepository writerRepository;


    @After
    public void cleanUp(){
        repository.deleteAll();
    }

    @Transactional//<-LazyInitializationException: could not initialize proxy 에러를 해결하려면 트랜잭션 처리를 해야함.
    @Test
    public void 일지를_저장하고_불러온다(){

        //given
        Email email=new Email("dasd412@naver.com");
        Writer writer=new Writer("tester", email);


        DiabetesDiary diary=new DiabetesDiary.Builder()
                .fastingPlasmaGlucose(100)
                .breakfastBloodSugar(95)
                .lunchBloodSugar(100)
                .dinnerBloodSugar(150)
                .writer(writer)
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
        assertThat(d.getWriter().getName()).isEqualTo(Optional.of("tester"));
        assertThat(d.getWriter().getEmail().getAddress()).isEqualTo("dasd412@naver.com");

    }

}