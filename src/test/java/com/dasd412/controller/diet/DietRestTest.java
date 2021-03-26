package com.dasd412.controller.diet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dasd412.controller.diabetesDiary.DiabetesDiaryRequestDTO;
import com.dasd412.domain.diary.DiabetesDiary;
import com.dasd412.domain.diet.Diet;
import com.dasd412.domain.diet.EatTime;
import com.dasd412.domain.diet.HashTag;
import com.dasd412.domain.diet.HashTagRepository;
import com.dasd412.service.diabetesDiaryForm.DiabetesDiaryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DietRestTest {

  @LocalServerPort
  private int port;

  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext context;

  @Autowired
  private DiabetesDiaryService diaryService;

  @Autowired
  private HashTagRepository hashTagRepository;

  @Before
  public void setup() {
    mockMvc = MockMvcBuilders
        .webAppContextSetup(context)
        .apply(springSecurity())
        .build();
  }

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Test
  @Transactional
  public void 일지와_태그를_같이_작성한다() throws Exception {
    //given

    DiabetesDiary diary = new DiabetesDiary.Builder()
        .fastingPlasmaGlucose(100)
        .breakfastBloodSugar(95)
        .lunchBloodSugar(100)
        .dinnerBloodSugar(150)
        .writtenTime("2021", "03", "26")
        .build();

    diaryService.save(diary);

    List<Diet> dietList = new ArrayList<>();

    dietList.add(new Diet("salad", EatTime.BREAK_FAST));
    dietList.add(new Diet("dumpling", EatTime.LUNCH));
    dietList.add(new Diet("hamburger", EatTime.DINNER));

    diaryService.saveWithTags(diary, dietList);

    //when
    List<HashTag> found = hashTagRepository.findAll();

    //then
    for (HashTag tag : found) {
      logger.info("diary : " + tag.getDiabetesDiary().toString());
      logger.info("diet : " + tag.getDiet().toString());
    }

    assertThat(found.get(0).getDiabetesDiary()).isEqualTo(diary);
    assertThat(found.get(0).getDiet().getFoodName()).isEqualTo("salad");
    assertThat(found.get(0).getDiet().getEatTime()).isEqualTo(EatTime.BREAK_FAST);

    assertThat(found.get(1).getDiabetesDiary()).isEqualTo(diary);
    assertThat(found.get(1).getDiet().getFoodName()).isEqualTo("dumpling");
    assertThat(found.get(1).getDiet().getEatTime()).isEqualTo(EatTime.LUNCH);

    assertThat(found.get(2).getDiabetesDiary()).isEqualTo(diary);
    assertThat(found.get(2).getDiet().getFoodName()).isEqualTo("hamburger");
    assertThat(found.get(2).getDiet().getEatTime()).isEqualTo(EatTime.DINNER);

  }

  @Test
  @Transactional
  @WithMockUser(roles = "USER")
  public void 일지와_태그를_작성하여_전송한다() throws Exception {
    //given
    DietRequestDTO lunch = new DietRequestDTO("dumpling", EatTime.LUNCH);
    DietRequestDTO breakfast = new DietRequestDTO("salad", EatTime.BREAK_FAST);
    DietRequestDTO dinner = new DietRequestDTO("hamburger", EatTime.DINNER);

    List<DietRequestDTO> dietRequestDTOList = new ArrayList<>();
    dietRequestDTOList.add(breakfast);
    dietRequestDTOList.add(lunch);
    dietRequestDTOList.add(dinner);

    DiabetesDiaryRequestDTO dto = new DiabetesDiaryRequestDTO.Builder()
        .fastingPlasmaGlucose(90)
        .breakfastBloodSugar(90)
        .lunchBloodSugar(100)
        .dinnerBloodSugar(110)
        .remark("test")
        .year("2021")
        .month("02")
        .day("07")
        .dietList(dietRequestDTOList)
        .build();

    String url = "/api/diabetes/diary/diet/post";

    //when
    mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(new ObjectMapper().writeValueAsString(dto)))
        .andExpect(status().isOk());

    List<HashTag> found = hashTagRepository.findAll();

    //then
    for (HashTag tag : found) {
      logger.info("diary : " + tag.getDiabetesDiary().toString());
      logger.info("diet : " + tag.getDiet().toString());
    }
    assertThat(found.get(0).getDiet().getFoodName()).isEqualTo("salad");
    assertThat(found.get(0).getDiet().getEatTime()).isEqualTo(EatTime.BREAK_FAST);

    assertThat(found.get(1).getDiet().getFoodName()).isEqualTo("dumpling");
    assertThat(found.get(1).getDiet().getEatTime()).isEqualTo(EatTime.LUNCH);

    assertThat(found.get(2).getDiet().getFoodName()).isEqualTo("hamburger");
    assertThat(found.get(2).getDiet().getEatTime()).isEqualTo(EatTime.DINNER);


  }


}
