package com.kernel.common.reservation.entity;

import com.kernel.common.global.entity.BaseEntity;
import com.kernel.common.admin.dto.request.AdminServiceCategoryReqDTO;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "service_categories")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@ToString
public class ServiceCategory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serviceId;

    // 서비스 카테고리 이름
    @Column(nullable = false, length = 100)
    private String serviceName;

    // 서비스 제공 여부
    @Column(nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    // 서비스 제공 최소 시간
    @Column(nullable = false)
    private Integer serviceTime;

    // 카테로리 분류 깊이 -> 대분류, 중분류, 소분류 순으로 depth 증가 설정
    @Column(nullable = false)
    @Builder.Default
    private Integer depth = 0;

    // 관리자가 직접 표시 우선 순위 설정
    @Column(nullable = false)
    @Builder.Default
    private Integer sortOrder = 0;

    // 부모 카테고리와의 관계 설정 -> 1:N
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private ServiceCategory parentId;


    // 엔터티 생성시 depth를 계산 및 기본값 설정을 위한 메서드
    // Prepersist 어노테이션은 한 클래스에 하나만 사용할 수 있는 제약 조건이 있어서 하나의 메서드로 구현
    @PrePersist
    @PreUpdate
    private void perPersist() {
        // default 값 설정
        if (this.isActive == null) {
            this.isActive = true;
        }
        if (this.sortOrder == null) {
            this.sortOrder = 0;
        }

        // depth
        if (parentId == null) {
            this.depth = 0; // 최상위 카테고리
        } else {
            this.depth = parentId.getDepth() + 1; // 부모 카테고리의 depth에 1을 더함
        }
    }

    // 서비스 카테고리 정보 업데이트 메서드
    public void update(AdminServiceCategoryReqDTO request, ServiceCategory parentCategory) {

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

    // 서비스 카테고리 삭제 메서드
    public void delete() {

        this.isActive = false; // 상태 관리로 삭제 처리
    }
}
