package com.dasd412.domain.diary;

import java.util.Set;
import java.util.List;

import com.dasd412.domain.diet.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DiabetesDiaryRepository extends JpaRepository<DiabetesDiary,Long> {

}
