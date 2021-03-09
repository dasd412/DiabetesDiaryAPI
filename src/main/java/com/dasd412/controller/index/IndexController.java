package com.dasd412.controller.index;

import com.dasd412.security.LoginUser;
import com.dasd412.security.SessionUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class IndexController {

    private final HttpSession httpSession;

    public IndexController(HttpSession httpSession) {
        this.httpSession = httpSession;
    }


    @GetMapping("/")
    public String viewResolve(Model model, @LoginUser SessionUser user){
        if(user!=null){
            model.addAttribute("userName",user.getName());
        }
        return "index";
    }
}
