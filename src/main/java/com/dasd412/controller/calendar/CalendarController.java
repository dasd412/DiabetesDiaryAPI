package com.dasd412.controller.calendar;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CalendarController {

    @GetMapping("/api/diabetes/calendar")
    public String viewResolve(){return "calendar/Calendar";}
}
