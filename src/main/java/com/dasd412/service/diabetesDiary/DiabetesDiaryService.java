package com.dasd412.service.diabetesDiary;

import com.dasd412.controller.diabetesDiary.DiabetesDiaryUpdateRequestDTO;
import com.dasd412.controller.diabetesDiary.DiaryResponseDTO;
import com.dasd412.controller.error.NotFoundException;
import com.dasd412.domain.diary.DiabetesDiary;
import com.dasd412.domain.diary.DiabetesDiaryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public Long update(Long id, DiabetesDiaryUpdateRequestDTO dto) {
        DiabetesDiary diary=diaryRepository.findById(id)
                .orElseThrow(()->new NotFoundException("해당 게시글이 존재하지 않습니다."));
        diary.update(dto.getFastingPlasmaGlucose(),dto.getBreakfastBloodSugar(),dto.getLunchBloodSugar(),dto.getDinnerBloodSugar(),dto.getRemark());
        return id;
    }

    public DiaryResponseDTO findById(Long id) {
        DiabetesDiary entity=diaryRepository.findById(id)
                .orElseThrow(()->new NotFoundException("해당 게시글이 존재하지 않습니다."));

        return new DiaryResponseDTO(entity);
    }
}
