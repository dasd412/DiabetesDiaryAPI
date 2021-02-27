package com.dasd412.controller.diabetesDiary;

import com.dasd412.controller.ApiResult;
import com.dasd412.domain.diary.DiabetesDiary;
import com.dasd412.domain.diary.DiabetesDiaryRepository;
import com.dasd412.domain.diary.Writer;
import com.dasd412.domain.user.Email;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class DiabetesDiaryRestControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private DiabetesDiaryRepository repository;

    @Autowired
    private MockMvc mockMvc;

    private final Logger logger= LoggerFactory.getLogger(this.getClass());

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
                .year("2021")
                .month("02")
                .day("07")
                .build();

        String url="http://localhost:"+port+"/api/diabetes/diary/post";

        //when
        ResponseEntity<ApiResult>responseEntity=testRestTemplate.postForEntity(url,dto, ApiResult.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        logger.info("json body"+responseEntity.getBody());

        List<DiabetesDiary>diaryList=repository.findAll();
        assertThat(diaryList.get(0).getFastingPlasmaGlucose()).isEqualTo(90);
        assertThat(diaryList.get(0).getBreakfastBloodSugar()).isEqualTo(90);
        assertThat(diaryList.get(0).getLunchBloodSugar()).isEqualTo(100);
        assertThat(diaryList.get(0).getDinnerBloodSugar()).isEqualTo(110);
        assertThat(diaryList.get(0).getWriter().getName()).isEqualTo(Optional.of("tester"));
        assertThat(diaryList.get(0).getWriter().getEmail().getAddress()).isEqualTo("dasd412@naver.com");
        assertThat(diaryList.get(0).getRemark()).isEqualTo("test");


        String[]array=diaryList.get(0).getWrittenTime().format(DateTimeFormatter.ISO_DATE).split("-");
        assertThat(array[0]).isEqualTo("2021");
        assertThat(array[1]).isEqualTo("02");
        assertThat(array[2]).isEqualTo("07");

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
                .writtenTime("2021","02","27")
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
        logger.info("diary : "+list.get(0).getFastingPlasmaGlucose()+" "+list.get(0).getBreakfastBloodSugar()+" "+list.get(0).getLunchBloodSugar()+" "+list.get(0).getDinnerBloodSugar()+" "+list.get(0).getRemark());
        logger.info("cascade : "+list.get(0).getWriter().getName()+" "+list.get(0).getWriter().getEmail().getAddress());

        assertThat(list.get(0).getFastingPlasmaGlucose()).isEqualTo(100);
        assertThat(list.get(0).getBreakfastBloodSugar()).isEqualTo(20);
        assertThat(list.get(0).getLunchBloodSugar()).isEqualTo(55);
        assertThat(list.get(0).getDinnerBloodSugar()).isEqualTo(150);
        assertThat(list.get(0).getRemark()).isEqualTo("test");
        assertThat(list.get(0).getWriter().getName()).isEqualTo(Optional.of("tester"));
        assertThat(list.get(0).getWriter().getEmail().getAddress()).isEqualTo("dasd412@naver.com");

        String[]array=list.get(0).getWrittenTime().format(DateTimeFormatter.ISO_DATE).split("-");
        assertThat(array[0]).isEqualTo("2021");
        assertThat(array[1]).isEqualTo("02");
        assertThat(array[2]).isEqualTo("27");

    }


}