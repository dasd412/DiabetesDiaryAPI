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
    public List<DiabetesDiary>getDiaryListBetween(String startDate, String endDate){
        /*
        좋은 방법은 아니지만, 어노테이션이 적용이 안되는 관계로 서비스 레이어에서 파싱...
         */
        LocalDateTime s=LocalDateTime.parse(startDate);
        LocalDateTime e=LocalDateTime.parse(endDate);

        return diaryRepository.findAllBetweenDates(s,e);
    }

}
