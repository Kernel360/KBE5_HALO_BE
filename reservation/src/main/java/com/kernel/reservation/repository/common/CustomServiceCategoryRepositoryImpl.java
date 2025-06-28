package com.kernel.reservation.repository.common;



import com.kernel.reservation.service.info.ServiceCategoryTreeInfo;
import com.kernel.sharedDomain.domain.entity.QServiceCategory;
import com.kernel.sharedDomain.domain.entity.ServiceCategory;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CustomServiceCategoryRepositoryImpl implements CustomServiceCategoryRepository {

    private final JPAQueryFactory queryFactory;
    private final QServiceCategory serviceCategory = QServiceCategory.serviceCategory;

    /**
     * 서비스 카테고리 조회
     * @return 서비스 카테고리 tree
     */
    @Override
    public List<ServiceCategoryTreeInfo> getServiceCategoryTree() {

        // 1. 모든 카테고리 조회 (isActive 여부 상관없이 전체 필요)
        List<ServiceCategory> allCategories = queryFactory
                .selectFrom(serviceCategory)
                .orderBy(serviceCategory.createdAt.asc())
                .fetch();

        // 2. 활성 카테고리만 필터링
        List<ServiceCategory> validCategories = allCategories.stream()
                .filter(this::isFullyActive)
                .toList();

        // 3. 트리 구성 후 루트만 반환
        Map<Long, ServiceCategoryTreeInfo> treeMap = buildServiceCategoryTree(validCategories);

        return validCategories.stream()
                .filter(c -> c.getParent() == null)
                .map(c -> treeMap.get(c.getServiceId()))
                .collect(Collectors.toList());
    }

    @Override
    public ServiceCategoryTreeInfo getRequestServiceCategory(Long serviceId, List<Long> extraServiceIds) {

        // null 방지: null이면 빈 리스트로 초기화
        List<Long> safeExtraIds = Optional.ofNullable(extraServiceIds).orElse(Collections.emptyList());

        BooleanBuilder parentAndChildCategory = new BooleanBuilder()
                .and(serviceCategory.isActive.isTrue())
                .and(serviceCategory.price.ne(0));

        if (safeExtraIds.isEmpty()) {
            // extraServiceIds 없으면 부모만 조회
            parentAndChildCategory.and(serviceCategory.serviceId.eq(serviceId));
        } else {
            // 부모 + 선택한 자식만 조회
            parentAndChildCategory.and(
                    serviceCategory.serviceId.eq(serviceId)
                            .or(
                                    serviceCategory.parent.serviceId.eq(serviceId)
                                            .and(serviceCategory.serviceId.in(safeExtraIds))
                            )
            );
        }

        // 1. 요청 카테고리 + 하위 카테고리 조회
        List<ServiceCategory> filteredCategories = queryFactory
                .selectFrom(serviceCategory)
                .where(
                        parentAndChildCategory
                )
                .orderBy(serviceCategory.createdAt.asc())
                .fetch();

        if (filteredCategories == null || filteredCategories.isEmpty()) {
            return null;
        }


        // 2. 트리 구성 후 루트 반환
        Map<Long, ServiceCategoryTreeInfo> treeMap = buildServiceCategoryTree(filteredCategories);

        return filteredCategories.stream()
                .filter(c -> c.getParent() == null || !treeMap.containsKey(c.getParent().getServiceId()))
                .map(c -> treeMap.get(c.getServiceId()))
                .findFirst()
                .orElse(null);
    }

    /**
     * 공통 트리 구성 로직
     */
    private Map<Long, ServiceCategoryTreeInfo> buildServiceCategoryTree(List<ServiceCategory> categories) {
        // 1. Entity → DTO 변환
        Map<Long, ServiceCategoryTreeInfo> infoMap = categories.stream()
                .collect(Collectors.toMap(
                        ServiceCategory::getServiceId,
                        this::convertToTreeInfo,
                        (a, b) -> a,
                        LinkedHashMap::new
                ));

        // 2. 부모-자식 관계 설정
        categories.forEach(category -> {
            if (category.getParent() != null && infoMap.containsKey(category.getParent().getServiceId())) {
                ServiceCategoryTreeInfo parent = infoMap.get(category.getParent().getServiceId());
                ServiceCategoryTreeInfo child = infoMap.get(category.getServiceId());
                parent.getChildren().add(child);
            }
        });

        return infoMap;
    }

    /**
     * Entity → DTO 변환 로직
     */
    private ServiceCategoryTreeInfo convertToTreeInfo(ServiceCategory category) {
        return ServiceCategoryTreeInfo.builder()
                .serviceId(category.getServiceId())
                .serviceName(category.getServiceName())
                .serviceTime(category.getServiceTime())
                .depth(category.getDepth())
                .price(category.getPrice())
                .description(category.getDescription())
                .children(new ArrayList<>())
                .build();
    }

    /**
     * 부모 카테고리 isActive = False 확인
     * @return 활성 여부
     */
    private boolean isFullyActive(ServiceCategory category) {
        while (category != null) {
            if (category.getIsActive() == null || !category.getIsActive()) {
                return false;
            }
            category = category.getParent();
        }
        return true;
    }
}
