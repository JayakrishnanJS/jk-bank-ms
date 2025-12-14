package com.jkbank.accounts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@ToString
public class BaseEntity {

    @Column(updatable = false) // createdAt should not be updated once set
    private LocalDateTime createdAt;

    @Column(updatable = false)
    private String createdBy;

    @Column(insertable = false) // updatedAt should not be set on insert
    private LocalDateTime updatedAt;

    @Column(insertable = false)
    private String updatedBy;
}
