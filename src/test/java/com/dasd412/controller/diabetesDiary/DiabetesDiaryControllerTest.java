package com.dasd412.controller.diabetesDiary;

import com.dasd412.domain.diary.DiabetesDiary;
import com.dasd412.domain.diary.DiabetesDiaryRepository;
import com.dasd412.domain.diary.Writer;
import com.dasd412.domain.user.Email;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc//<-Mockmvc 주입용 어노테이션
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DiabetesDiaryControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private DiabetesDiaryRepository repository;

    @Autowired
    private MockMvc mockMvc;

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

    @Transactional
    @Test
    public void 일지를_수정한다()throws Exception{
        //given

        Email email=new Email("dasd412@naver.com");
        Writer writer=new Writer("tester", email);

        DiabetesDiary savedDiary=new DiabetesDiary.Builder()
                .fastingPlasmaGlucose(100)
                .breakfastBloodSugar(95)
                .lunchBloodSugar(100)
                .dinnerBloodSugar(150)
                .writer(writer)
                .build();

        repository.save(savedDiary);

        Long updatedId=savedDiary.getId();

        DiabetesDiaryUpdateRequestDTO updateRequestDTO=new DiabetesDiaryUpdateRequestDTO.Builder()
                .breakfastBloodSugar(20)
                .lunchBloodSugar(55)
                .remark("test")
                .build();

        String url="http://localhost:"+port+"/api/diabetes/diary/"+updatedId;

        HttpEntity<DiabetesDiaryUpdateRequestDTO>requestDTOHttpEntity=new HttpEntity<>(updateRequestDTO);

        //when
        mockMvc.perform(put(url).contentType(MediaType.APPLICATION_JSON_UTF8).content(new ObjectMapper().writeValueAsString(updateRequestDTO)))
                .andExpect(status().isOk());
        //then

        List<DiabetesDiary>list=repository.findAll();
        assertThat(list.get(0).getFastingPlasmaGlucose()).isEqualTo(100);
        assertThat(list.get(0).getBreakfastBloodSugar()).isEqualTo(20);
        assertThat(list.get(0).getLunchBloodSugar()).isEqualTo(55);
        assertThat(list.get(0).getDinnerBloodSugar()).isEqualTo(150);
        assertThat(list.get(0).getRemark()).isEqualTo("test");
        assertThat(list.get(0).getWriter().getName()).isEqualTo(Optional.of("tester"));
        assertThat(list.get(0).getWriter().getEmail().getAddress()).isEqualTo("dasd412@naver.com");



    }


}