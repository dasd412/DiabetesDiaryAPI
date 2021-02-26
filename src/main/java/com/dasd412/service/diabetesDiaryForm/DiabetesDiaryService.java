package com.dasd412.service.diabetesDiaryForm;

import com.dasd412.controller.diabetesDiary.DiaryResponseDTO;
import com.dasd412.controller.error.NotFoundException;
import com.dasd412.domain.commons.Id;
import com.dasd412.domain.diary.DiabetesDiary;
import com.dasd412.domain.diary.DiabetesDiaryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class DiabetesDiaryService {

    /*
    서비스 계층에는 DTO를 쓰지 않는다.
     */

    private final DiabetesDiaryRepository diaryRepository;

    public DiabetesDiaryService(DiabetesDiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    @Transactional
    public DiabetesDiary save(DiabetesDiary diary) {
        return diaryRepository.save(diary);
    }

    @Transactional(readOnly = true)
    public DiaryResponseDTO findById(Id<DiabetesDiary,Long> id) {
        checkNotNull(id,"diary id must be provided");
        DiabetesDiary entity=diaryRepository.findById(id.value())
                .orElseThrow(()->new NotFoundException("해당 게시글이 존재하지 않습니다."));
        return new DiaryResponseDTO(entity);
    }

    @Transactional
    public DiabetesDiary update(Id<DiabetesDiary,Long>id, int fastingPlasmaGlucose, int breakfastBloodSugar, int lunchBloodSugar, int dinnerBloodSugar, String remark) {
        checkNotNull(id,"diary id must be provided");

        DiabetesDiary diary=diaryRepository.findById(id.value())
                .orElseThrow(()->new NotFoundException("해당 게시글이 존재하지 않습니다."));
        diary.update(fastingPlasmaGlucose,breakfastBloodSugar,lunchBloodSugar,dinnerBloodSugar,remark);
        return diary;
    }

    @Transactional
    public void delete(Long id) {
        checkNotNull(id,"diary id must be provided");

        DiabetesDiary target=diaryRepository.findById(id)
                .orElseThrow(()->new NotFoundException("해당 게시글이 존재하지 않습니다."));

        diaryRepository.delete(target);
    }
}
