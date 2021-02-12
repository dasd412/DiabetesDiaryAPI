package com.dasd412.service.diabetesDiary;

import com.dasd412.controller.diabetesDiary.DiabetesDiaryRequestDTO;
import com.dasd412.domain.diary.DiabetesDiaryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DiabetesDiaryService {

    private final DiabetesDiaryRepository diaryRepository;

    public DiabetesDiaryService(DiabetesDiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    @Transactional
    public Long save(DiabetesDiaryRequestDTO diaryDTO) {
        return diaryRepository.save(diaryDTO.toEntity()).getId();
    }
}
