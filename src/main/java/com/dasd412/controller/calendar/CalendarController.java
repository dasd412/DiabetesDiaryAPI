package com.dasd412.controller.calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CalendarController {

    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    @GetMapping("/api/diabetes/calendar")
    public String viewResolve(){return "calendar/Calendar";}
}
