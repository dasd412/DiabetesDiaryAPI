package com.dasd412.controller.charts;

import com.dasd412.controller.diabetesDiary.DiabetesDiaryRequestDTO;
import com.dasd412.domain.diary.DiabetesDiary;
import com.dasd412.domain.diary.DiabetesDiaryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Profile("test")
public class ChartsRestControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    DiabetesDiaryRepository repository;

    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setup(){
        mockMvc= MockMvcBuilders
                .webAppContextSetup(context)
                .addFilter(new CharacterEncodingFilter("UTF-8",true))
                .apply(springSecurity())
                .build();
    }

    @After
    public void cleanUp(){
        repository.deleteAll();
    }

    @Transactional
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

    @Transactional
    @Test
    public void 데이터를_저장하고_모두_조회한다_3월한정()throws Exception{
        //given
        String year="2021";
        String month="03";

        for(int i=1;i<=31;i++){
            StringBuilder sb=new StringBuilder();
            if(i<10){
                sb.append("0");
            }
            sb.append(i);

            DiabetesDiary diary=new DiabetesDiary.Builder()
                    .fastingPlasmaGlucose(100+i)
                    .breakfastBloodSugar(1)
                    .lunchBloodSugar(1)
                    .dinnerBloodSugar(1)
                    .writtenTime(year,month,sb.toString())
                    .build();
            repository.save(diary);
        }

        //when
        LocalDateTime startDate=LocalDateTime.parse("2021-03-04T00:00:00");
        LocalDateTime endDate=LocalDateTime.parse("2021-03-24T00:00");
        List<DiabetesDiary>found=repository.findAllBetweenDates(startDate,endDate);

        //then
        for (DiabetesDiary diary:found){
            logger.info("diary : "+diary.getWrittenTime().toString());
        }

    }

    @Transactional
    @Test
    public void 데이터를_저장하고_아침혈당만_조회한다_3월한정()throws Exception{
        //given
        String year="2021";
        String month="03";

        for(int i=1;i<=31;i++){
            StringBuilder sb=new StringBuilder();
            if(i<10){
                sb.append("0");
            }
            sb.append(i);

            DiabetesDiary diary=new DiabetesDiary.Builder()
                    .fastingPlasmaGlucose(100+i)
                    .breakfastBloodSugar(1)
                    .lunchBloodSugar(1)
                    .dinnerBloodSugar(1)
                    .writtenTime(year,month,sb.toString())
                    .build();
            repository.save(diary);
        }

        //when
        LocalDateTime startDate=LocalDateTime.parse("2021-03-01T00:00:00");
        LocalDateTime endDate=LocalDateTime.parse("2021-03-31T00:00");
        List<DiabetesDiary>found=repository.findAllBetweenDates(startDate,endDate);

        //then
        for (int i=0;i<found.size();i++){
            logger.info("fpg : "+found.get(i).getFastingPlasmaGlucose());
           assertThat(found.get(i).getFastingPlasmaGlucose()).isEqualTo(100+i+1);
        }

    }

    @Transactional
    @Test
    public void 데이터를_저장하고_모두_조회한다_3월과_4월_사이()throws Exception{
        //given - 03
        String year="2021";
        String month="03";

        for(int i=1;i<=31;i++){
            StringBuilder sb=new StringBuilder();
            if(i<10){
                sb.append("0");
            }
            sb.append(i);

            DiabetesDiary diary=new DiabetesDiary.Builder()
                    .fastingPlasmaGlucose(100+i)
                    .breakfastBloodSugar(1)
                    .lunchBloodSugar(1)
                    .dinnerBloodSugar(1)
                    .writtenTime(year,month,sb.toString())
                    .build();
            repository.save(diary);
        }

        //given - 04
        String nextMonth="04";
        for(int i=1;i<=30;i++){
            StringBuilder sb=new StringBuilder();
            if(i<10){
                sb.append("0");
            }
            sb.append(i);

            DiabetesDiary diary=new DiabetesDiary.Builder()
                    .fastingPlasmaGlucose(200+i)
                    .breakfastBloodSugar(1)
                    .lunchBloodSugar(1)
                    .dinnerBloodSugar(1)
                    .writtenTime(year,nextMonth,sb.toString())
                    .build();
            repository.save(diary);
        }

        //when 03-04
        LocalDateTime startDate=LocalDateTime.parse("2021-03-11T00:00:00");
        LocalDateTime endDate=LocalDateTime.parse("2021-04-21T00:00");
        List<DiabetesDiary>found=repository.findAllBetweenDates(startDate,endDate);

        //then
        for (DiabetesDiary diary:found){
            logger.info("diary : "+diary.getWrittenTime().toString()+" "+diary.getFastingPlasmaGlucose());
        }

    }

    @Transactional
    @Test
    public void 유효하지_않은_데이터를_저장한다_MUST_FAIL()throws Exception{
        /*
        2021년 2월은 28일까지다.
        따라서 2021년 2월 29일은 생성할 수 없다.
        날짜 예외처리는 writtenTime() 메서드에서 던진다.
        writtenTime()은 String->LocalDateTime으로의 포매팅을 (빌더 내에서) 해준다.
        이 포매팅을 할 때 LocalDateTime.Parse()에서 2021년 2월 29일은 유효하지 않은 날짜임을 예외 처리로 알려준다.
        따라서 해당 혈당일지 엔티티는 생성 및 저장되지 않는다.
        given에서 이미 예외 처리로 걸러지지므로 when과 then은 볼 필요가 없다. 따라서 주석처리하였다.

        결론:이 테스트는 반드시 실패하는 테스트이다. 유효하지 않은 날짜도 체크하는지가 목적이다.
         */
        //given
        String year="2021";
        String month="02";

        for(int i=1;i<=29;i++){
            StringBuilder sb=new StringBuilder();
            if(i<10){
                sb.append("0");
            }
            sb.append(i);

            DiabetesDiary diary=new DiabetesDiary.Builder()
                    .fastingPlasmaGlucose(100+i)
                    .breakfastBloodSugar(1)
                    .lunchBloodSugar(1)
                    .dinnerBloodSugar(1)
                    .writtenTime(year,month,sb.toString())
                    .build();
            repository.save(diary);
        }

//        //when
//        LocalDateTime startDate=LocalDateTime.parse("2021-02-01T00:00:00");
//        LocalDateTime endDate=LocalDateTime.parse("2021-02-29T00:00");
//        List<DiabetesDiary>found=repository.findAllBetweenDates(startDate,endDate);
//
//        //then
//        for (DiabetesDiary diary:found){
//            logger.info("diary : "+diary.getWrittenTime().toString());
//        }
    }

    @Transactional
    @Test
    @WithMockUser(roles="USER")
    public void 일지를_등록하고_기간별_조회한다()throws Exception{


        DiabetesDiary diary=new DiabetesDiary.Builder()
                .fastingPlasmaGlucose(100)
                .breakfastBloodSugar(95)
                .lunchBloodSugar(100)
                .dinnerBloodSugar(150)
                .writtenTime("2021","03","17")
                .build();

        repository.save(diary);


        String url="http://localhost:"+port+"/api/diabetes/charts/list";
        //when
        String result=mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("startDate","2021-03-01T00:00:00").param("endDate","2021-03-31T00:00:00"))
                .andExpect(status().isOk()).andDo(print()).andReturn().getResponse()
                .getContentAsString();
        logger.info("result : "+result);
    }


    @Transactional
    @Test
    @WithMockUser(roles="USER")
    public void 일지를_등록하고_기간별_조회한다_둘다_포함()throws Exception{


        DiabetesDiary diary1=new DiabetesDiary.Builder()
                .fastingPlasmaGlucose(100)
                .breakfastBloodSugar(95)
                .lunchBloodSugar(100)
                .dinnerBloodSugar(150)
                .writtenTime("2021","03","17")
                .build();

        repository.save(diary1);


        DiabetesDiary diary2=new DiabetesDiary.Builder()
                .fastingPlasmaGlucose(100)
                .breakfastBloodSugar(95)
                .lunchBloodSugar(100)
                .dinnerBloodSugar(150)
                .writtenTime("2021","04","05")
                .build();

        repository.save(diary2);



        String url="http://localhost:"+port+"/api/diabetes/charts/list";
        //when
        String result=mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON_UTF8)
        .param("startDate","2021-03-13T00:00:00").param("endDate","2021-04-11T00:00:00"))
                .andExpect(status().isOk()).andDo(print()).andReturn().getResponse()
                .getContentAsString();
        logger.info("result : "+result);
    }

    @Transactional
    @Test
    @WithMockUser(roles="USER")
    public void 일지를_등록하고_기간별_조회한다_하나_미포함()throws Exception{


        DiabetesDiary diary1=new DiabetesDiary.Builder()
                .fastingPlasmaGlucose(100)
                .breakfastBloodSugar(95)
                .lunchBloodSugar(100)
                .dinnerBloodSugar(150)
                .writtenTime("2021","03","17")
                .build();

        repository.save(diary1);


        DiabetesDiary diary2=new DiabetesDiary.Builder()
                .fastingPlasmaGlucose(100)
                .breakfastBloodSugar(95)
                .lunchBloodSugar(100)
                .dinnerBloodSugar(150)
                .writtenTime("2021","04","05")
                .build();

        repository.save(diary2);



        DayChartRequestDTO dto=new DayChartRequestDTO("2021-03-01T00:00:00","2021-03-21T00:00:00");
        String url="http://localhost:"+port+"/api/diabetes/charts/list";
        //when
        String result=mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("startDate","2021-03-01T00:00:00").param("endDate","2021-03-21T00:00:00"))
                .andExpect(status().isOk()).andDo(print()).andReturn().getResponse()
                .getContentAsString();
        logger.info("result : "+result);
    }

    @Transactional
    @Test
    public void 데이터를_저장하고_조회한다_년기준_2월한정()throws Exception{
        //given
        String year="2021";

        DiabetesDiary diary1=new DiabetesDiary.Builder()
                .fastingPlasmaGlucose(1)
                .breakfastBloodSugar(2)
                .lunchBloodSugar(5)
                .dinnerBloodSugar(6)
                .writtenTime(year,"02","11")
                .build();
        repository.save(diary1);

        DiabetesDiary diary2=new DiabetesDiary.Builder()
                .fastingPlasmaGlucose(3)
                .breakfastBloodSugar(5)
                .lunchBloodSugar(7)
                .dinnerBloodSugar(9)
                .writtenTime(year,"02","01")
                .build();
        repository.save(diary2);

        DiabetesDiary diary3=new DiabetesDiary.Builder()
                .fastingPlasmaGlucose(4)
                .breakfastBloodSugar(4)
                .lunchBloodSugar(2)
                .dinnerBloodSugar(6)
                .writtenTime(year,"02","27")
                .build();
        repository.save(diary3);

        DiabetesDiary diary4=new DiabetesDiary.Builder()
                .fastingPlasmaGlucose(5)
                .breakfastBloodSugar(10)
                .lunchBloodSugar(11)
                .dinnerBloodSugar(12)
                .writtenTime(year,"02","13")
                .build();
        repository.save(diary4);

        DiabetesDiary diary5=new DiabetesDiary.Builder()
                .fastingPlasmaGlucose(13)
                .breakfastBloodSugar(12)
                .lunchBloodSugar(7)
                .dinnerBloodSugar(7)
                .writtenTime(year,"02","05")
                .build();
        repository.save(diary5);


        //when
        LocalDateTime startDate=LocalDateTime.parse("2021-01-01T00:00:00");
        LocalDateTime endDate=LocalDateTime.parse("2021-12-31T00:00");

        //then

    }


}