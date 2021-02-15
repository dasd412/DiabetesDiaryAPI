package com.dasd412.controller.diabetesDiary;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DDFormController {


    @GetMapping("/api/diabetes/diary")
    public String viewResolve(){
        return "DDForm";
    }

}
