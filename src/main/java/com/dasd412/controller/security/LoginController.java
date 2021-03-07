package com.dasd412.controller.security;

import com.dasd412.security.SessionUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    private final HttpSession httpSession;

    public LoginController(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    @GetMapping("/api/login")
    public String viewResolve(Model model){
        SessionUser user=(SessionUser)httpSession.getAttribute("user");//customUserService에서 로그인 성공시 세션에 SessionUser저장.
        if(user!=null){
            model.addAttribute("userName",user.getName());
        }
        return "/login/login";
    }
}
