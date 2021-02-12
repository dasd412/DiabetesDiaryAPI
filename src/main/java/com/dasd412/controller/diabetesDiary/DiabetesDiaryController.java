package com.dasd412.controller.diabetesDiary;

import com.dasd412.service.diabetesDiary.DiabetesDiaryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

}
