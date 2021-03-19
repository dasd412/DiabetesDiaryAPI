package com.dasd412.controller.charts;

import com.dasd412.security.LoginUser;
import com.dasd412.security.SessionUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class ChartsController {

    private final HttpSession httpSession;

    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    public ChartsController(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    @GetMapping("/api/diabetes/charts")
    public String viewResolve(Model model, @LoginUser SessionUser user){
        if(user!=null){
            model.addAttribute("userName",user.getName());
        }
        return "/charts/charts";
    }
}
