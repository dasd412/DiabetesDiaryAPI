package com.dasd412.service.tag;

import com.dasd412.controller.tag.PageVO;
import com.dasd412.domain.diet.Diet;
import com.dasd412.domain.diet.DietQuery;
import com.dasd412.domain.diet.DietRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DietTagService {

  private final DietRepository dietRepository;

  public DietTagService(DietRepository dietRepository) {
    this.dietRepository = dietRepository;
  }

  public Page<Diet> findAll(PageVO vo,Pageable pageable) {
    return dietRepository.findAll(dietRepository.makePredicate(new DietQuery(vo.getType(),vo.getKeyword())),pageable);
  }

  public boolean save(String foodName){
    if(!dietRepository.existsDietByFoodName(foodName)){
      dietRepository.save(new Diet(foodName));
      return true;
    }
    else return false;
  }
}
