package com.dasd412.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

  @CreatedDate
  private LocalDateTime createAt;

  @LastModifiedDate
  private LocalDateTime updatedAt;

  public LocalDateTime getCreateAt() {
    return createAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }
}
