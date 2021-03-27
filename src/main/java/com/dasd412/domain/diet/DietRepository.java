package com.dasd412.domain.diet;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DietRepository extends JpaRepository<Diet, Long> {

  boolean existsDietByFoodName(String foodName);

  Optional<Diet> findByFoodName(String foodName);
}
