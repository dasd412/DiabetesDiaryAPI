package com.dasd412.controller.tag;

import com.dasd412.security.LoginUser;
import com.dasd412.security.SessionUser;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TagController {

  private final HttpSession httpSession;

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  public TagController(HttpSession httpSession) {
    this.httpSession = httpSession;
  }

  @GetMapping("/api/diabetes/tag")
  public String viewResolve(Model model, @LoginUser SessionUser user){
    if (user != null) {
      model.addAttribute("userName", user.getName());
    }
    return "/tag/tag";
  }
}
