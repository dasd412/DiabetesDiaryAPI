package com.dasd412.controller.diabetesDiary;

import com.dasd412.controller.ApiResult;
import com.dasd412.domain.diary.DiabetesDiary;
import com.dasd412.domain.diary.DiabetesDiaryRepository;

import java.time.format.DateTimeFormatter;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DiabetesDiaryRestControllerTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate testRestTemplate;

  @Autowired
  private DiabetesDiaryRepository repository;

  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext context;

  @Before
  public void setup() {
    mockMvc = MockMvcBuilders
        .webAppContextSetup(context)
        .apply(springSecurity())
        .build();
  }

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @After
  public void tearDown() throws Exception {
    repository.deleteAll();
  }

  @Transactional
  @Test
  @WithMockUser(roles = "USER")
  public void 일지를_등록한다() throws Exception {

    //given

    DiabetesDiaryRequestDTO dto = new DiabetesDiaryRequestDTO.Builder()
        .fastingPlasmaGlucose(90)
        .breakfastBloodSugar(90)
        .lunchBloodSugar(100)
        .dinnerBloodSugar(110)
        .remark("test")
        .year("2021")
        .month("02")
        .day("07")
        .build();

    String url = "http://localhost:" + port + "/api/diabetes/diary/post";

    //when
    mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(new ObjectMapper().writeValueAsString(dto)))
        .andExpect(status().isOk());
    //then
    List<DiabetesDiary> diaryList = repository.findAll();
    assertThat(diaryList.get(0).getFastingPlasmaGlucose()).isEqualTo(90);
    assertThat(diaryList.get(0).getBreakfastBloodSugar()).isEqualTo(90);
    assertThat(diaryList.get(0).getLunchBloodSugar()).isEqualTo(100);
    assertThat(diaryList.get(0).getDinnerBloodSugar()).isEqualTo(110);
    assertThat(diaryList.get(0).getRemark()).isEqualTo("test");

    String[] array = diaryList.get(0).getWrittenTime().format(DateTimeFormatter.ISO_DATE)
        .split("-");
    assertThat(array[0]).isEqualTo("2021");
    assertThat(array[1]).isEqualTo("02");
    assertThat(array[2]).isEqualTo("07");

  }

  @Transactional
  @Test
  @WithMockUser(roles = "USER")
  public void 일지를_수정한다() throws Exception {
    //given

    DiabetesDiary savedDiary = new DiabetesDiary.Builder()
        .fastingPlasmaGlucose(100)
        .breakfastBloodSugar(95)
        .lunchBloodSugar(100)
        .dinnerBloodSugar(150)
        .writtenTime("2021", "02", "27")
        .build();

    repository.save(savedDiary);

    Long updatedId = savedDiary.getId();

    DiabetesDiaryUpdateRequestDTO updateRequestDTO = new DiabetesDiaryUpdateRequestDTO.Builder()
        .breakfastBloodSugar(20)
        .lunchBloodSugar(55)
        .remark("test")
        .build();

    String url = "http://localhost:" + port + "/api/diabetes/diary/" + updatedId;

    HttpEntity<DiabetesDiaryUpdateRequestDTO> requestDTOHttpEntity = new HttpEntity<>(
        updateRequestDTO);

    //when
    mockMvc.perform(put(url).contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(new ObjectMapper().writeValueAsString(updateRequestDTO)))
        .andExpect(status().isOk());
    //then

    List<DiabetesDiary> list = repository.findAll();
    logger.info("diary : " + list.get(0).getFastingPlasmaGlucose() + " " + list.get(0)
        .getBreakfastBloodSugar() + " " + list.get(0).getLunchBloodSugar() + " " + list.get(0)
        .getDinnerBloodSugar() + " " + list.get(0).getRemark());

    assertThat(list.get(0).getFastingPlasmaGlucose()).isEqualTo(100);
    assertThat(list.get(0).getBreakfastBloodSugar()).isEqualTo(20);
    assertThat(list.get(0).getLunchBloodSugar()).isEqualTo(55);
    assertThat(list.get(0).getDinnerBloodSugar()).isEqualTo(150);
    assertThat(list.get(0).getRemark()).isEqualTo("test");

    String[] array = list.get(0).getWrittenTime().format(DateTimeFormatter.ISO_DATE).split("-");
    assertThat(array[0]).isEqualTo("2021");
    assertThat(array[1]).isEqualTo("02");
    assertThat(array[2]).isEqualTo("27");

  }


}