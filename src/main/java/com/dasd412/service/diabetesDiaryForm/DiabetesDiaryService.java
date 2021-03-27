package com.dasd412.service.diabetesDiaryForm;

import com.dasd412.controller.diet.DietTagMapper;
import com.dasd412.controller.error.NotFoundException;
import com.dasd412.domain.commons.Id;
import com.dasd412.domain.diary.DiabetesDiary;
import com.dasd412.domain.diary.DiabetesDiaryRepository;
import com.dasd412.domain.diet.Diet;
import com.dasd412.domain.diet.DietRepository;
import com.dasd412.domain.diet.HashTag;
import com.dasd412.domain.diet.HashTagRepository;
import java.util.ArrayList;
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
  public List<DietTagMapper> saveDiaryWithDiet(DiabetesDiary diary, List<DietTagMapper> dietList) {

    diaryRepository.save(diary);

    List<DietTagMapper> saved = new ArrayList<>();

    for (DietTagMapper tagMapper : dietList) {

      //    식단 이름이 이미 있는가를 체크한 후 저장
      if (!dietRepository.existsDietByFoodName(tagMapper.getFoodName())) {
        Diet diet = new Diet(tagMapper.getFoodName());
        dietRepository.save(diet);

        HashTag tag = new HashTag(diary, diet, tagMapper.getEatTime());
        hashTagRepository.save(tag);
        saved.add(new DietTagMapper(diet.getFoodName(), tag.getEatTime()));
      } else {
        //todo Optional 관련 리팩토링 필요
        Diet exist = dietRepository.findByFoodName(tagMapper.getFoodName()).get();

        hashTagRepository.save(new HashTag(diary, exist, tagMapper.getEatTime()));

        saved.add(new DietTagMapper(exist.getFoodName(), tagMapper.getEatTime()));
      }
    }

    return saved;
  }
}
