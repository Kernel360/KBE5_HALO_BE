package com.kernel.common.global.entity;

import com.kernel.common.admin.dto.request.AdminServiceCatReqDTO;
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

    @Column(nullable = false, length = 100)
    private String serviceName;

    // 서비스 제공 여부
    @Column(nullable = false, columnDefinition = "Boolean DEFAULT true")
    private Boolean isActive;

    // 서비스 제공 최소 시간
    @Column(nullable = false)
    private Integer serviceTime;

    // 카테로리 분류 깊이 -> 대분류, 중분류, 소분류 순으로 숫자가 커지도록 설정
    @Column(nullable = false)
    private Integer depth;

    // 관리자가 직접 표시 우선 순위를 정하도록 -> 가능하면 나중에 사용패턴을 자동화 계획
    @Column(nullable = false, columnDefinition = "Integer DEFAULT 0")
    private Integer sortOrder;

    // 부모 카테고리와의 관계 설정 -> 1:N
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

    public void update(AdminServiceCatReqDTO request) {
        // 서비스 카테고리 정보 업데이트 메서드
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
            this.parentId = request.getParentId();
        }
    }

    public void delete() {
        // 서비스 카테고리 삭제 메서드
        this.isActive = false; // 실제로는 삭제하지 않고, isActive를 false로 설정하여 비활성화 처리
    }
}
