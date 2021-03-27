package com.dasd412.controller.charts;

import com.dasd412.controller.ApiResult;
import com.dasd412.controller.diabetesDiary.DiaryListResponseDTO;
import com.dasd412.domain.diary.DiabetesDiary;
import com.dasd412.service.charts.ChartsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "차트 REST 컨트롤러")
public class ChartsRestController {

  private final ChartsService chartsService;

  public ChartsRestController(ChartsService chartsService) {
    this.chartsService = chartsService;
  }

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @GetMapping("/api/diabetes/charts/list")
  @ApiOperation(value = "시작 기간 ~ 끝 기간 사이에 존재하는 모든 혈당 일지의 일별 정보 조회")
  public ApiResult<List<DiaryListResponseDTO>> getDiaryListBetween(
      @RequestParam Map<String, Object> allParameters) {
        /*
        위 메소드의 파라미터인 Map<String, Object> allParameters는 2개의 (key,value)쌍을 갖는데, 다음과 같다.
        ("startDate" ,startDate) , ("endDate",endDate)
         */
    logger.info("chartRestController get diary list between days :" + allParameters.toString());

    List<DiabetesDiary> list = chartsService
        .getDiaryListBetween((String) allParameters.get("startDate"),
            (String) allParameters.get("endDate"));

    List<DiaryListResponseDTO> dtoList = list.stream()
        .map(DiaryListResponseDTO::new)
        .collect(Collectors.toList());

    return ApiResult.OK(dtoList);
  }

  @GetMapping("/api/diabetes/charts/average")
  @ApiOperation(value = "시작 기간 ~ 끝 기간 사이에 존재하는 모든 혈당 일지의 월별 정보 조회")
  public ApiResult<AverageChartDTO> getMonthlyAverageBetween(
      @RequestParam Map<String, Object> allParam) {
         /*
        위 메소드의 파라미터인 Map<String, Object> allParameters는 2개의 (key,value)쌍을 갖는데, 다음과 같다.
        ("startDate" ,startDate) , ("endDate",endDate)
         */
    logger.info("chartRestController get monthly average : " + allParam.toString());
    return ApiResult.OK(
        new AverageChartDTO(chartsService
            .getMonthlyAverageBetween((String) allParam.get("startDate"),
                (String) allParam.get("endDate")))
    );
  }

}
