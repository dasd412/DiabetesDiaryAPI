package com.dasd412.controller.diabetesDiary;

import com.dasd412.domain.commons.Id;
import com.dasd412.domain.diary.DiabetesDiary;
import com.dasd412.service.diabetesDiary.DiabetesDiaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class DDFormController {

    private final DiabetesDiaryService diaryService;

    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    public DDFormController(DiabetesDiaryService diaryService) {this.diaryService = diaryService; }

    @GetMapping("/api/diabetes/diary")
    public String viewResolve(){return "DDForm"; }


    @GetMapping("/api/diabetes/diary/{id}")
    public String findById(@PathVariable Long id, Model model){

        DiaryResponseDTO dto=diaryService.findById(Id.of(DiabetesDiary.class, id));
        logger.info("DDFormController find by : "+dto.toString());
        model.addAttribute("bloodSugar",dto);

        return "DDFormSelect";
    }


}
