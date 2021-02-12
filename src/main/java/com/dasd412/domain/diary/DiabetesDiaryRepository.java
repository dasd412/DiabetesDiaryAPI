package com.dasd412.domain.diary;

import java.util.Set;
import java.util.List;

import com.dasd412.domain.diet.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DiabetesDiaryRepository extends JpaRepository<DiabetesDiary,Long> {

//    @Query(value="SELECT dt.foodName FROM Diet dt WHERE dt.id IN "
//            +"(SELECT  h.diet FROM HashTag h LEFT OUTER JOIN DiabetesDiary dd ON dd.id=h.diabetesDiary WHERE dd.id>0 GROUP BY dd)")
//    public List<Object[]>findAllDietTags();

}
