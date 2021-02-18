package com.dasd412.controller.diabetesDiary;

import com.dasd412.domain.commons.Id;
import com.dasd412.domain.diary.DiabetesDiary;
import com.dasd412.service.diabetesDiary.DiabetesDiaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@Api(tags="혈당 일지 폼 컨트롤러")
public class DDFormController {

    private final DiabetesDiaryService diaryService;

    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    public DDFormController(DiabetesDiaryService diaryService) {this.diaryService = diaryService; }

    @GetMapping("/api/diabetes/diary")
    @ApiOperation(value="혈당 일지 폼 뷰 리졸빙")
    public String viewResolve(){return "DDForm"; }


    @GetMapping("/api/diabetes/diary/{id}")
    @ApiOperation(value="혈당 일지 조회")
    public String findById(@PathVariable @ApiParam(value="혈당 일지 PK", example = "1")Long id, Model model){

        DiaryResponseDTO dto=diaryService.findById(Id.of(DiabetesDiary.class, id));
        logger.info("DDFormController find by : "+dto.toString());
        model.addAttribute("bloodSugar",dto);

        return "DDFormSelect";
    }


}
