package com.dasd412.controller.index;

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
@Api(tags = "Index 뷰 리졸버")
public class IndexController {

  private final HttpSession httpSession;

  public IndexController(HttpSession httpSession) {
    this.httpSession = httpSession;
  }


  @GetMapping("/")
  @ApiOperation(value = "Index 뷰 리졸빙")
  public String viewResolve(Model model,
      @ApiParam(value = "로그인 유저의 세션 정보") @LoginUser SessionUser user) {
    if (user != null) {
      model.addAttribute("userName", user.getName());
    }
    return "index";
  }
}
