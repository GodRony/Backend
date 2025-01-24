package com.codingrecipe.board.entity;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public class BaseEntity {
    @CreationTimestamp // 생성되었을때 시간
    @Column(updatable = false) // 수정시에는 반영하지 마라
    private LocalDateTime createdTime;

    @UpdateTimestamp // 수정시에 시간
    @Column(insertable = false) // 입력할때는 반영하지마라
    private LocalDateTime updatedTime;
}
