package com.dasd412.controller.diabetesDiary;

import com.dasd412.domain.diary.DiabetesDiary;
import com.dasd412.domain.diary.DiabetesDiaryRepository;
import com.dasd412.domain.diary.Writer;
import com.dasd412.domain.user.Email;

import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DiabetesDiaryControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private DiabetesDiaryRepository repository;

    @After
    public void tearDown()throws Exception{
        repository.deleteAll();
    }

    @Transactional
    @Test
    public void 일지를_등록한다()throws Exception{

        //given
        Email email=new Email("dasd412@naver.com");
        Writer writer=new Writer("tester", email);

        DiabetesDiaryRequestDTO dto=new DiabetesDiaryRequestDTO.Builder()
                .fastingPlasmaGlucose(90)
                .breakfastBloodSugar(90)
                .lunchBloodSugar(100)
                .dinnerBloodSugar(110)
                .writer(writer)
                .remark("test")
                .build();

        String url="http://localhost:"+port+"/api/diabetes/diary/post";

        //when
        ResponseEntity<Long>responseEntity=testRestTemplate.postForEntity(url,dto,Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<DiabetesDiary>diaryList=repository.findAll();
        assertThat(diaryList.get(0).getFastingPlasmaGlucose()).isEqualTo(90);
        assertThat(diaryList.get(0).getBreakfastBloodSugar()).isEqualTo(90);
        assertThat(diaryList.get(0).getLunchBloodSugar()).isEqualTo(100);
        assertThat(diaryList.get(0).getDinnerBloodSugar()).isEqualTo(110);
        assertThat(diaryList.get(0).getWriter().getName()).isEqualTo(Optional.of("tester"));
        assertThat(diaryList.get(0).getWriter().getEmail().getAddress()).isEqualTo("dasd412@naver.com");
        assertThat(diaryList.get(0).getRemark()).isEqualTo("test");

    }


}