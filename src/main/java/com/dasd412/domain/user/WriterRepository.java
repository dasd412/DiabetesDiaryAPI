package com.dasd412.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WriterRepository extends JpaRepository<Writer, Long> {

  Optional<Writer> findByEmail(String email);

}
