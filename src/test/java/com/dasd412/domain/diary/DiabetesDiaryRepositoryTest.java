package com.dasd412.domain.diary;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;
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

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Profile("test")
public class DiabetesDiaryRepositoryTest {

  @Autowired
  DiabetesDiaryRepository repository;

  private final Logger logger = LoggerFactory.getLogger(this.getClass());


  @After
  public void cleanUp() {
    repository.deleteAll();
  }

  @Transactional//<-LazyInitializationException: could not initialize proxy 에러를 해결하려면 트랜잭션 처리를 해야함.
  @Test
  public void 작성자와_일지를_저장하고_불러온다() {

    //given

    DiabetesDiary diary = new DiabetesDiary.Builder()
        .fastingPlasmaGlucose(100)
        .breakfastBloodSugar(95)
        .lunchBloodSugar(100)
        .dinnerBloodSugar(150)
        .build();

    repository.save(diary);

    //when
    List<DiabetesDiary> list = repository.findAll();

    //then
    DiabetesDiary d = list.get(0);
    assertThat(d.getFastingPlasmaGlucose()).isEqualTo(diary.getFastingPlasmaGlucose());
    assertThat(d.getBreakfastBloodSugar()).isEqualTo(diary.getBreakfastBloodSugar());
    assertThat(d.getLunchBloodSugar()).isEqualTo(diary.getLunchBloodSugar());
    assertThat(d.getDinnerBloodSugar()).isEqualTo(diary.getDinnerBloodSugar());
  }

  @Transactional//fecthType.lazy인 경우에는 @SpringBootTest일 때 트랜잭션 처리해주어야 한다.
  @Test
  public void 테스트_ManyToOne_작성자_및_일지() {
    //given 작성자 하나에 대해 혈당일지 3개 작성

    IntStream.range(0, 3).forEach(i -> {
      DiabetesDiary diary = new DiabetesDiary.Builder()
          .fastingPlasmaGlucose(100 + i)
          .breakfastBloodSugar(100 + i)
          .lunchBloodSugar(100 + i)
          .dinnerBloodSugar(100 + i)
          .build();

      repository.save(diary);
    });

    //when
    List<DiabetesDiary> diaryList = repository.findAll();

    //then
    for (int i = 0; i < diaryList.size(); i++) {
      DiabetesDiary d = diaryList.get(i);
      assertThat(d.getFastingPlasmaGlucose()).isEqualTo(100 + i);
      assertThat(d.getBreakfastBloodSugar()).isEqualTo(100 + i);
      assertThat(d.getLunchBloodSugar()).isEqualTo(100 + i);
      assertThat(d.getDinnerBloodSugar()).isEqualTo(100 + i);
    }
  }

  @Transactional
  @Test
  public void testBaseTimeEntity() throws Exception {
    LocalDateTime now = LocalDateTime.of(2020, 1, 20, 0, 0, 0);

    DiabetesDiary diary = new DiabetesDiary.Builder()
        .fastingPlasmaGlucose(100)
        .breakfastBloodSugar(95)
        .lunchBloodSugar(100)
        .dinnerBloodSugar(150)
        .build();

    repository.save(diary);

    //when
    List<DiabetesDiary> list = repository.findAll();

    //then
    DiabetesDiary d = list.get(0);
    logger.info("createdAt: " + d.getCreateAt() + " updatedAt: " + d.getUpdatedAt());

    assertThat(d.getCreateAt()).isAfter(now);
    assertThat(d.getUpdatedAt()).isAfter(now);

  }
}