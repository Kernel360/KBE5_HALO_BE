package com.kernel.common.global.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Getter
@MappedSuperclass
@SuperBuilder
@NoArgsConstructor
// @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class) // application.yml에서 전역 설정했으므로 주석처리
public class BaseEntity { // 중복되는 엔티티들 모아놓음

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(updatable = false)
    @CreatedBy
    private Long createdBy;

    @Column
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column
    @LastModifiedBy
    private Long updatedBy;
}
