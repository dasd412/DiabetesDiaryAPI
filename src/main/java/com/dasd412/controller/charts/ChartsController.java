package com.dasd412.controller.charts;

import com.dasd412.security.LoginUser;
import com.dasd412.security.SessionUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
@Api(tags = "차트 뷰 리졸버")
public class ChartsController {

  private final HttpSession httpSession;

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  public ChartsController(HttpSession httpSession) {
    this.httpSession = httpSession;
  }

  @GetMapping("/api/diabetes/charts")
  @ApiOperation(value = "차트 뷰 리졸빙")
  public String viewResolve(Model model,
      @ApiParam(value = "로그인 유저의 세션 정보") @LoginUser SessionUser user) {
    if (user != null) {
      model.addAttribute("userName", user.getName());
    }
    return "/charts/charts";
  }
}
