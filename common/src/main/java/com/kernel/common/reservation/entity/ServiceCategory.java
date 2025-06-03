package com.kernel.common.reservation.entity;

import com.kernel.common.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "service_categories")
@Getter
@NoArgsConstructor
@SuperBuilder
@ToString
// @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)    // application.yml에서 전역 설정했으므로 주석처리
public class ServiceCategory extends BaseEntity {
    // TODO: 서비스 카테고리 엔터티 정의

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serviceId;

    @Column(length = 100)
    private String serviceName;

    private Boolean isActive;

    private Integer serviceTime;

    private Integer depth;

    private Integer sortOrder; // 관리자가 직접 표시 우선 순위를 정하도록 -> 가능하면 나중에 사용패턴을 자동화 계획

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private ServiceCategory parentId;

    @PrePersist
    @PreUpdate
    private void calculateDepth() {
        if (parentId == null) {
            this.depth = 0; // 최상위 카테고리
        } else {
            this.depth = parentId.getDepth() + 1; // 부모 카테고리의 depth에 1을 더함
        }
    }
}
