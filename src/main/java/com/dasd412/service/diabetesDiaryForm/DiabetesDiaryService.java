package com.dasd412.service.diabetesDiaryForm;

import com.dasd412.controller.diabetesDiary.DiaryResponseDTO;
import com.dasd412.controller.error.NotFoundException;
import com.dasd412.domain.commons.Id;
import com.dasd412.domain.diary.DiabetesDiary;
import com.dasd412.domain.diary.DiabetesDiaryRepository;
import com.dasd412.domain.diet.Diet;
import com.dasd412.domain.diet.DietRepository;
import com.dasd412.domain.diet.EatTime;
import com.dasd412.domain.diet.HashTag;
import com.dasd412.domain.diet.HashTagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class DiabetesDiaryService {

    /*
    서비스 계층에는 DTO를 쓰지 않는다.
     */

  private final DiabetesDiaryRepository diaryRepository;
  private final DietRepository dietRepository;
  private final HashTagRepository hashTagRepository;

  public DiabetesDiaryService(DiabetesDiaryRepository diaryRepository,
      DietRepository dietRepository, HashTagRepository hashTagRepository) {
    this.diaryRepository = diaryRepository;
    this.dietRepository = dietRepository;
    this.hashTagRepository = hashTagRepository;
  }

  @Transactional
  public DiabetesDiary save(DiabetesDiary diary) {
    return diaryRepository.save(diary);
  }

  @Transactional(readOnly = true)
  public DiabetesDiary findById(Id<DiabetesDiary, Long> id) {
    checkNotNull(id, "diary id must be provided");
    return diaryRepository.findById(id.value())
        .orElseThrow(() -> new NotFoundException("해당 게시글이 존재하지 않습니다."));
  }

  @Transactional
  public DiabetesDiary update(Id<DiabetesDiary, Long> id, int fastingPlasmaGlucose,
      int breakfastBloodSugar, int lunchBloodSugar, int dinnerBloodSugar, String remark) {
    checkNotNull(id, "diary id must be provided");

    DiabetesDiary diary = diaryRepository.findById(id.value())
        .orElseThrow(() -> new NotFoundException("해당 게시글이 존재하지 않습니다."));
    diary.update(fastingPlasmaGlucose, breakfastBloodSugar, lunchBloodSugar, dinnerBloodSugar,
        remark);
    return diary;
  }

  @Transactional
  public void delete(Long id) {
    checkNotNull(id, "diary id must be provided");

    DiabetesDiary target = diaryRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("해당 게시글이 존재하지 않습니다."));

    diaryRepository.delete(target);
  }

  @Transactional(readOnly = true)
  public List<DiabetesDiary> getDiaryList() {
    return diaryRepository.findAll();
  }

  @Transactional
  public Diet saveWithTags(DiabetesDiary diary, String foodName, EatTime eatTime) {
    //    diary는 repository에 저장되있어야 한다.
    Diet diet = new Diet(foodName, eatTime);
    dietRepository.save(diet);
    HashTag tag = new HashTag(diary, diet);//    생성자 안에 id 값을 넣는 걸로 바꾸는 게 효율적임.
    hashTagRepository.save(tag);
    return diet;
  }
}
