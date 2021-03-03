package com.dasd412.controller.calendar;

import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CalendarController {

    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    @GetMapping("/api/diabetes/calendar")
    @ApiOperation(value="일정 뷰 리졸브")
    public String viewResolve(){return "calendar/Calendar";}
}
