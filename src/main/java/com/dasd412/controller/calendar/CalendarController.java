package com.dasd412.controller.calendar;

import com.dasd412.security.LoginUser;
import com.dasd412.security.SessionUser;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class CalendarController {

    private final HttpSession httpSession;

    public CalendarController(HttpSession httpSession) {
        this.httpSession = httpSession;
    }


    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    @GetMapping("/api/diabetes/calendar")
    @ApiOperation(value="일정 뷰 리졸브")
    public String viewResolve(Model model, @LoginUser SessionUser user){
        if(user!=null){
            model.addAttribute("userName",user.getName());
        }
        return "calendar/Calendar";
    }
}
