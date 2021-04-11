package com.dasd412.controller.tag;

import com.dasd412.domain.diet.Diet;
import com.dasd412.security.LoginUser;
import com.dasd412.security.SessionUser;
import com.dasd412.service.tag.DietTagService;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class TagController {

  private final HttpSession httpSession;

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final DietTagService dietTagService;

  public TagController(HttpSession httpSession,
      DietTagService dietTagService) {
    this.httpSession = httpSession;
    this.dietTagService = dietTagService;
  }

  @GetMapping("/api/diabetes/tag")
  public String viewResolve(@ModelAttribute("pageVO") PageVO vo, Model model,
      @LoginUser SessionUser user) {
    if (user != null) {
      model.addAttribute("userName", user.getName());
    }

    Pageable pageable = vo.makePageable(SortDir.DESC, "id");

    Page<Diet> result = dietTagService.findAll(vo, pageable);

    logger.info("pageable : " + pageable);
    logger.info("Page<Diet> result : " + result);
    logger.info("Page vo : " + vo.toString());

    model.addAttribute("result", new PageMaker<>(result));

    return "/tag/tag";
  }

}
