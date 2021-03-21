package com.dasd412.domain.diary;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DiabetesDiaryRepository extends JpaRepository<DiabetesDiary, Long> {

  @Query(value = "from DiabetesDiary dd where dd.writtenTime BETWEEN :startDate AND :endDate")
  List<DiabetesDiary> findAllBetweenDates(@Param("startDate") LocalDateTime startDate,
      @Param("endDate") LocalDateTime endDate);

  //Object[0]=month(dd.written_time), Object[1]=sum(avgSugar) / count(month(dd.written_time))
  @Query(value = "select month(dd.written_time), sum(avgSugar) / count(month(dd.written_time)) from diabetes_diary dd inner join (select temp.id  tempId, (temp.FASTING_PLASMA_GLUCOSE + temp.BREAKFAST_BLOOD_SUGAR + temp.LUNCH_BLOOD_SUGAR + temp.DINNER_BLOOD_SUGAR) / 4.0 avgSugar, temp.written_time from diabetes_diary temp) as selfdd on dd.id = selfdd.tempId where dd.written_time between ?1 and ?2 group by month(dd.written_time)", nativeQuery = true)
  List<Object[]> findMonthlyAverage(LocalDateTime startDate, LocalDateTime endDate);
}
