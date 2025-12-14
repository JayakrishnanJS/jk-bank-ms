package com.jkbank.accounts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class) // to enable auditing for this entity
@Getter
@Setter
@ToString
public class BaseEntity {

    @CreatedDate // automatically set when the entity is created
    @Column(updatable = false) // createdAt should not be updated once set
    private LocalDateTime createdAt;

    @CreatedBy // automatically set when the entity is created
    @Column(updatable = false) // createdBy should not be updated once set
    private String createdBy;

    @LastModifiedDate // automatically set when the entity is updated
    @Column(insertable = false) // updatedAt should not be set on insert
    private LocalDateTime updatedAt;

    @LastModifiedBy // automatically set when the entity is updated
    @Column(insertable = false) // updatedBy should not be set on insert
    private String updatedBy;
}
