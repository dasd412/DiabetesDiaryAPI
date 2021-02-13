package com.dasd412.controller.diabetesDiary;

import com.dasd412.controller.ApiResult;
import com.dasd412.service.diabetesDiary.DiabetesDiaryService;
import org.springframework.web.bind.annotation.*;

@RestController
public class DiabetesDiaryController {

    /*
    컨트롤러 계층에서만 DTO를 쓴다.
     */

    private final DiabetesDiaryService diaryService;

    public DiabetesDiaryController(DiabetesDiaryService diaryService) {
        this.diaryService = diaryService;
    }

    @PostMapping("/api/diabetes/diary/post")
    public ApiResult<DiaryResponseDTO> postDiary(@RequestBody DiabetesDiaryRequestDTO diaryDTO){
        return ApiResult.OK(
                new DiaryResponseDTO(diaryService.save(diaryDTO.toEntity()))
        );
    }

    @PutMapping("/api/diabetes/diary/{id}")
    public Long update(@PathVariable Long id, @RequestBody DiabetesDiaryUpdateRequestDTO dto){ return diaryService.update(id,dto); }

    @GetMapping("/api/diabetes/diary/{id}")
    public DiaryResponseDTO findById(@PathVariable Long id){return diaryService.findById(id);}

}
