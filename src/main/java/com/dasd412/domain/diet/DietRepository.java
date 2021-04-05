package com.dasd412.domain.diet;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface DietRepository extends JpaRepository<Diet, Long>, QuerydslPredicateExecutor<Diet> {

  boolean existsDietByFoodName(String foodName);

  Optional<Diet> findByFoodName(String foodName);

  default Predicate makePredicate() {

    BooleanBuilder builder = new BooleanBuilder();
    QDiet diet = QDiet.diet;
    builder.and(diet.id.gt(0));
    return builder;
  }
}
