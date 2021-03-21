package com.dasd412.controller.security;

import com.dasd412.security.LoginUser;
import com.dasd412.security.SessionUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
@Api(tags = "로그인 뷰 리졸버")
public class LoginController {

  private final HttpSession httpSession;

  public LoginController(HttpSession httpSession) {
    this.httpSession = httpSession;
  }

  @GetMapping("/api/login")
  @ApiOperation(value = "로그인 뷰 리졸빙")
  public String viewResolve(Model model,
      @ApiParam(value = "로그인 유저의 세션 정보") @LoginUser SessionUser user) { //customUserService에서 로그인 성공시 세션에 SessionUser저장.
    if (user != null) {
      model.addAttribute("userName", user.getName());
    }
    return "/login/login";
  }
}
