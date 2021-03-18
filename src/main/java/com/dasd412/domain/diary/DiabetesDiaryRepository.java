package com.dasd412.domain.diary;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.List;

import com.dasd412.domain.diet.HashTag;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public interface DiabetesDiaryRepository extends JpaRepository<DiabetesDiary,Long> {

    @Query(value = "from DiabetesDiary dd where dd.writtenTime BETWEEN :startDate AND :endDate")
     List<DiabetesDiary> findAllBetweenDates(@Param("startDate")LocalDateTime startDate, @Param("endDate")LocalDateTime endDate);

    default List<Object[]>findMonthlyAverage(LocalDateTime startDate, LocalDateTime endDate){
        QDiabetesDiary diabetesDiary=QDiabetesDiary.diabetesDiary;

        EntityManagerFactory emf= Persistence.createEntityManagerFactory("manager");
        EntityManager em=emf.createEntityManager();

        JPAQuery query=new JPAQuery(em);

        List<Object[]>found=query
                .select(diabetesDiary.writtenTime.month(),
                        (diabetesDiary.fastingPlasmaGlucose).add(diabetesDiary.breakfastBloodSugar).add(diabetesDiary.lunchBloodSugar).add(diabetesDiary.dinnerBloodSugar).divide(4.0).divide(diabetesDiary.writtenTime.month()) )
                .innerJoin()
                .on()
                .where(diabetesDiary.writtenTime.between(startDate,endDate))
                .groupBy(diabetesDiary.writtenTime.month());
        em.close();
        emf.close();
        return found;
    }
}
