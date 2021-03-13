package com.dasd412.controller.charts;

import com.dasd412.controller.ApiResult;
import com.dasd412.domain.diary.DiabetesDiary;
import com.dasd412.service.charts.ChartsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class ChartsRestController {

    private final ChartsService chartsService;

    public ChartsRestController(ChartsService chartsService) {
        this.chartsService = chartsService;
    }

    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    @GetMapping("/api/diabetes/charts/list")
    public ApiResult<List<DiabetesDiary>>getDiaryListBetween(@RequestBody DayChartRequestDTO dto){
        logger.info("chartRestController get diary list between : "+dto.toString());
        return ApiResult.OK(
                chartsService.getDiaryListBetween(dto.getStartDate(),dto.getEndDate())
        );
    }

}
