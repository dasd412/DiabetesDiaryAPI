package com.dasd412.controller.charts;

import com.dasd412.controller.ApiResult;
import com.dasd412.domain.diary.DiabetesDiary;
import com.dasd412.service.charts.ChartsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

@RestController
public class ChartsRestController {

    private final ChartsService chartsService;

    public ChartsRestController(ChartsService chartsService) {
        this.chartsService = chartsService;
    }

    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    @GetMapping("/api/diabetes/charts/list")
    public ApiResult<List<DiabetesDiary>>getDiaryListBetween( @RequestParam Map<String, Object> allParameters){
        logger.info("chartRestController get diary list between days :"+allParameters.toString());
        return ApiResult.OK(
                chartsService.getDiaryListBetween((String)allParameters.get("startDate"),(String)allParameters.get("endDate"))
        );
    }

    @GetMapping("/api/diabetes/charts/average")
    public ApiResult<AverageChartDTO>getMonthlyAverageBetween(@RequestParam Map<String,Object>allParam){
        logger.info("chartRestController get monthly average : "+allParam.toString());
        return ApiResult.OK(
          new AverageChartDTO(chartsService.getMonthlyAverageBetween((String)allParam.get("startDate"),(String)allParam.get("endDate")))
        );
    }

}
