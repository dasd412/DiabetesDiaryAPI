package com.dasd412.service.charts;

import com.dasd412.domain.diary.DiabetesDiary;
import com.dasd412.domain.diary.DiabetesDiaryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class ChartsService {

    private final DiabetesDiaryRepository diaryRepository;

    public ChartsService(DiabetesDiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    @Transactional(readOnly=true)
    public List<DiabetesDiary>getDiaryListBetween(LocalDateTime startDate, LocalDateTime endDate){ return diaryRepository.findAllBetweenDates(startDate,endDate); }

}
