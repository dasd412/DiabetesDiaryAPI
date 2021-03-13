package com.dasd412.service.charts;

import com.dasd412.domain.diary.DiabetesDiaryRepository;
import org.springframework.stereotype.Service;

@Service
public class ChartsService {

    private final DiabetesDiaryRepository diaryRepository;

    public ChartsService(DiabetesDiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

}
