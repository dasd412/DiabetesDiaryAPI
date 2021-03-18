package com.dasd412.domain.diary;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DiabetesDiaryRepository extends JpaRepository<DiabetesDiary,Long> {

    @Query(value = "from DiabetesDiary dd where dd.writtenTime BETWEEN :startDate AND :endDate")
     List<DiabetesDiary> findAllBetweenDates(@Param("startDate")LocalDateTime startDate, @Param("endDate")LocalDateTime endDate);

    //List<Object[]>findMonthlyAverage(LocalDateTime startDate, LocalDateTime endDate);
}
