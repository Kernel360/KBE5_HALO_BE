package com.kernel.reservation.domain.entity;

import com.kernel.global.domain.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name="service_category")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ServiceCategory extends BaseEntity {

    // 서비스 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serviceId;

    // 부모 카테고리와의 관계 설정 -> 1:N
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private ServiceCategory parent;

    // 서비스 명
    @Column(nullable = false, length = 100)
    private String serviceName;

    // 서비스 시간
    @Column(nullable = false)
    private Integer serviceTime;

    // 가격
    @Column
    private Integer price;

    // 설명
    @Column(nullable = false, length = 100)
    private String description;

    // 카테로리 분류 깊이
    @Column(nullable = false)
    @Builder.Default
    private Integer depth = 0;

    // 사용 여부
    @Column(nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    // 엔터티 생성시 depth 계산 및 기본값 설정
    // Prepersist 어노테이션은 한 클래스에 하나만 사용할 수 있는 제약 조건이 있어서 하나의 메서드로 구현
    @PrePersist
    @PreUpdate
    private void perPersist() {

        // default 값 설정
        if (this.isActive == null) {
            this.isActive = true;
        }

        // depth 설정
        if (parent == null) {
            this.depth = 0; // 최상위 카테고리
        } else {
            this.depth = parent.getDepth() + 1; // 부모 카테고리 depth + 1
        }
    }

    // 서비스 카테고리 업데이트
    // TODO 서비스카테고리DTO 생성 시 주석 해제
   /* public void update(AdminServiceCategoryReqDTO request, ServiceCategory parentCategory) {

        if (request.getServiceName() != null) {
            this.serviceName = request.getServiceName();
        }
        if (request.getIsActive() != null) {
            this.isActive = request.getIsActive();
        }
        if (request.getSortOrder() != null) {
            this.sortOrder = request.getSortOrder();
        }
        if (request.getServiceTime() != null) {
            this.serviceTime = request.getServiceTime();
        }
        if (request.getParentId() != null) {
            this.parentId = parentCategory;
        }
    }
    */
    // 삭제
    public void delete() {
        this.isActive = false;
    }
}
