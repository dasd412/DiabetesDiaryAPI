package com.dasd412.service.diabetesDiary;

import com.dasd412.controller.diabetesDiary.DiabetesDiaryRequestDTO;
import com.dasd412.controller.diabetesDiary.DiabetesDiaryUpdateRequestDTO;
import com.dasd412.controller.diabetesDiary.DiaryResponseDTO;
import com.dasd412.controller.error.NotFoundException;
import com.dasd412.domain.diary.DiabetesDiary;
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

    @Transactional
    public Long update(Long id, DiabetesDiaryUpdateRequestDTO dto) {
        //todo 익셉션을 커스텀 익셉션으로 교체하기
        DiabetesDiary diary=diaryRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 일지가 존재하지 않습니다."+id));//<-커스텀 익셉션으로 교체할 것!
        diary.update(dto.getFastingPlasmaGlucose(),dto.getBreakfastBloodSugar(),dto.getLunchBloodSugar(),dto.getDinnerBloodSugar(),dto.getRemark());
        return id;
    }

    public DiaryResponseDTO findById(Long id) {
        //todo 익셉션을 커스텀 익셉션으로 교체하기
        DiabetesDiary entity=diaryRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 일지가 존재하지 않습니다."+id));

        return new DiaryResponseDTO(entity);
    }
}
