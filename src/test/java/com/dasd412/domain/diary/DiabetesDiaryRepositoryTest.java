package com.dasd412.domain.diary;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import com.dasd412.domain.diet.Diet;
import com.dasd412.domain.diet.EatTime;
import com.dasd412.domain.diet.HashTag;
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



    @After
    public void cleanUp(){
        repository.deleteAll();
    }

    //todo 혈당일지 리포지토리에서 findAll()실행 시 같은 select 문이 수행됨. N+1 문제인 것 같음. 수정 필요.

    @Transactional//<-LazyInitializationException: could not initialize proxy 에러를 해결하려면 트랜잭션 처리를 해야함.
    @Test
    public void 작성자와_일지를_저장하고_불러온다(){

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

    @Transactional//fecthType.lazy인 경우에는 @SpringBootTest일 때 트랜잭션 처리해주어야 한다.
    @Test
    public void 테스트_ManyToOne_작성자_및_일지(){
        //given 작성자 하나에 대해 혈당일지 3개 작성
        Email email=new Email("dasd412@naver.com");
        Writer writer=new Writer("tester", email);

        IntStream.range(0,3).forEach(i->{
            DiabetesDiary diary=new DiabetesDiary.Builder()
                    .fastingPlasmaGlucose(100+i)
                    .breakfastBloodSugar(100+i)
                    .lunchBloodSugar(100+i)
                    .dinnerBloodSugar(100+i)
                    .writer(writer)
                    .build();

            repository.save(diary);
        });

        //when
        List<DiabetesDiary>diaryList=repository.findAll();

        //then
        for(int i=0;i<diaryList.size();i++){
            DiabetesDiary d=diaryList.get(i);
            assertThat(d.getFastingPlasmaGlucose()).isEqualTo(100+i);
            assertThat(d.getBreakfastBloodSugar()).isEqualTo(100+i);
            assertThat(d.getLunchBloodSugar()).isEqualTo(100+i);
            assertThat(d.getDinnerBloodSugar()).isEqualTo(100+i);
            assertThat(d.getWriter().getName()).isEqualTo(Optional.of("tester"));
            assertThat(d.getWriter().getEmail().getAddress()).isEqualTo("dasd412@naver.com");
        }
    }

    //todo 연결 테이블 엔티티 조회 테스트 필요.

//    @Transactional
//    @Test
//    public void 일지에_식단태그를_붙인다(){
//        //given 아침식사로 치즈를 먹었다.
//        Email email=new Email("dasd412@naver.com");
//        Writer writer=new Writer("tester", email);
//
//        Diet diet=new Diet("cheese", EatTime.BREAK_FAST);
//        DiabetesDiary diary=new DiabetesDiary.Builder()
//                .breakfastBloodSugar(100)
//                .writer(writer)
//                .build();
//
//        repository.save(diary);
//
//        //when
//        repository.findAllDietTags().forEach(arr-> System.out.println(Arrays.toString(arr)));
//
//        //then
//
//
//
//    }
//
//    @Transactional
//    @Test
//    public void 테스트_다대다(){
//
//    }


}