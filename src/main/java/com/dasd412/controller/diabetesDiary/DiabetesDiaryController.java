package com.dasd412.controller.diabetesDiary;

import com.dasd412.service.diabetesDiary.DiabetesDiaryService;
import org.springframework.web.bind.annotation.*;

@RestController
public class DiabetesDiaryController {

    private final DiabetesDiaryService diaryService;

    public DiabetesDiaryController(DiabetesDiaryService diaryService) {
        this.diaryService = diaryService;
    }

    @PostMapping("/api/diabetes/diary/post")
    public Long postDiary(@RequestBody DiabetesDiaryRequestDTO diaryDTO){
        return diaryService.save(diaryDTO);
    }

    @PutMapping("/api/diabetes/diary/{id}")
    public Long update(@PathVariable Long id, @RequestBody DiabetesDiaryUpdateRequestDTO dto){ return diaryService.update(id,dto); }

    @GetMapping("/api/diabetes/diary/{id}")
    public DiaryResponseDTO findById(@PathVariable Long id){return diaryService.findById(id);}

}
