package com.dasd412.controller.diabetesDiary;

import com.dasd412.controller.ApiResult;
import com.dasd412.domain.commons.Id;
import com.dasd412.domain.diary.DiabetesDiary;
import com.dasd412.service.diabetesDiary.DiabetesDiaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
public class DiabetesDiaryRestController {

    /*
    컨트롤러 계층에서만 DTO를 쓴다.
     */

    private final DiabetesDiaryService diaryService;

    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    public DiabetesDiaryRestController(DiabetesDiaryService diaryService) {
        this.diaryService = diaryService;
    }

    @PostMapping("/api/diabetes/diary/post")
    public ApiResult<DiaryResponseDTO> postDiary(@RequestBody DiabetesDiaryRequestDTO diaryDTO){
        return ApiResult.OK(
                new DiaryResponseDTO(diaryService.save(diaryDTO.toEntity()))
        );
    }

    @PutMapping("/api/diabetes/diary/{id}")
    public ApiResult<DiaryResponseDTO> update(@PathVariable Long id, @RequestBody DiabetesDiaryUpdateRequestDTO dto){
        return ApiResult.OK(
                new DiaryResponseDTO(diaryService.update(Id.of(DiabetesDiary.class,id)
                        ,dto.getFastingPlasmaGlucose(),dto.getBreakfastBloodSugar(),dto.getLunchBloodSugar(),dto.getDinnerBloodSugar(),dto.getRemark()))
        );
    }


}
