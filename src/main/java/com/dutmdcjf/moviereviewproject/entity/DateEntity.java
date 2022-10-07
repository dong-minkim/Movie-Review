package com.dutmdcjf.moviereviewproject.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class })
@Getter
abstract class DateEntity {

    @CreatedDate
    @Column(name = "registdate", updatable = false)
    private LocalDateTime registDate;

    @LastModifiedDate
    @Column(name = "modifydate")
    private LocalDateTime modifyDate;

}
