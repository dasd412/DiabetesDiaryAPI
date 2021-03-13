package com.dasd412.controller.charts;

import com.dasd412.service.charts.ChartsService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChartsRestController {

    private final ChartsService chartsService;

    public ChartsRestController(ChartsService chartsService) {
        this.chartsService = chartsService;
    }


}
