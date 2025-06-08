package com.kernel.common.reservation.repository;

import com.kernel.common.reservation.dto.response.ServiceCategoryTreeDTO;
import com.kernel.common.reservation.entity.QServiceCategory;
import com.kernel.common.reservation.entity.ServiceCategory;
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
    public List<ServiceCategoryTreeDTO> getServiceCategoryTree() {

        // 1. 모든 카테고리 조회 (isActive 여부 상관없이 전체 필요)
        List<ServiceCategory> allCategories = queryFactory
                .selectFrom(serviceCategory)
                .orderBy(serviceCategory.sortOrder.asc())
                .fetch();

        // 2. ID → 엔티티 Map
        Map<Long, ServiceCategory> entityMap = allCategories.stream()
                .collect(Collectors.toMap(ServiceCategory::getServiceId, c -> c));

        // 3. 실제로 포함할 카테고리만 필터링 (자기 자신과 조상들이 모두 isActive == true여야 포함)
        List<ServiceCategory> validCategories = allCategories.stream()
                .filter(this::isFullyActive)
                .toList();

        // 4. DTO Map 생성
        Map<Long, ServiceCategoryTreeDTO> dtoMap = validCategories.stream()
                .collect(Collectors.toMap(
                        ServiceCategory::getServiceId,
                        c -> ServiceCategoryTreeDTO.builder()
                                .serviceId(c.getServiceId())
                                .serviceName(c.getServiceName())
                                .serviceTime(c.getServiceTime())
                                .depth(c.getDepth())
                                .price(c.getPrice())
                                .description(c.getDescription())
                                .children(new ArrayList<>())
                                .build(),
                        (a, b) -> a,
                        LinkedHashMap::new
                ));

        // 5. 트리 구성
        validCategories.forEach(c -> {
            if (c.getParentId() != null && dtoMap.containsKey(c.getParentId().getServiceId())) {
                ServiceCategoryTreeDTO parent = dtoMap.get(c.getParentId().getServiceId());
                ServiceCategoryTreeDTO child = dtoMap.get(c.getServiceId());
                parent.getChildren().add(child);
            }
        });

        // 6. 최상위 루트만 반환
        return validCategories.stream()
                .filter(c -> c.getParentId() == null)
                .map(c -> dtoMap.get(c.getServiceId()))
                .collect(Collectors.toList());
    }

    @Override
    public ServiceCategoryTreeDTO getRequestServiceCategory(Long serviceId, List<Long> extraServiceIds) {

        // null 방지: null이면 빈 리스트로 초기화
        List<Long> safeExtraIds = Optional.ofNullable(extraServiceIds).orElse(Collections.emptyList());

        BooleanBuilder parantAndChildCategory = new BooleanBuilder()
                .and(serviceCategory.isActive.isTrue())
                .and(serviceCategory.price.ne(0));

        if (safeExtraIds.isEmpty()) {
            // extraServiceIds 없으면 부모만 조회
            parantAndChildCategory.and(serviceCategory.serviceId.eq(serviceId));
        } else {
            // 부모 + 선택한 자식만 조회
            parantAndChildCategory.and(
                    serviceCategory.serviceId.eq(serviceId)
                            .or(
                                    serviceCategory.parentId.serviceId.eq(serviceId)
                                            .and(serviceCategory.serviceId.in(safeExtraIds))
                            )
            );
        }

        // 1. 요청 카테고리 + 하위 카테고리 조회
        List<ServiceCategory> filteredCategories = queryFactory
                .selectFrom(serviceCategory)
                .where(
                        parantAndChildCategory
                )
                .orderBy(serviceCategory.sortOrder.asc())
                .fetch();

        if (filteredCategories == null || filteredCategories.isEmpty()) {
            return null;
        }

        // 2. Entity → DTO 변환
        Map<Long, ServiceCategoryTreeDTO> dtoMap = new LinkedHashMap<>();
        Map<Long, Long> parentMap = new HashMap<>();

        for (ServiceCategory category : filteredCategories) {
            if (category == null || category.getServiceId() == null) continue;

            ServiceCategoryTreeDTO dto = ServiceCategoryTreeDTO.builder()
                    .serviceId(category.getServiceId())
                    .serviceName(category.getServiceName())
                    .serviceTime(category.getServiceTime())
                    .depth(category.getDepth())
                    .price(category.getPrice())
                    .description(category.getDescription())
                    .children(new ArrayList<>())
                    .build();

            dtoMap.put(category.getServiceId(), dto);
            parentMap.put(
                    category.getServiceId(),
                    category.getParentId() != null ? category.getParentId().getServiceId() : null
            );
        }

        // 3. 부모-자식 연결
        ServiceCategoryTreeDTO root = null;

        for (Map.Entry<Long, ServiceCategoryTreeDTO> entry : dtoMap.entrySet()) {
            Long serviceCategoryId = entry.getKey();
            ServiceCategoryTreeDTO dto = entry.getValue();
            Long parentId = parentMap.get(serviceCategoryId);

            if (parentId != null && dtoMap.containsKey(parentId)) {
                dtoMap.get(parentId).getChildren().add(dto);
            } else {
                root = dto; // 최상위 루트
            }
        }

        return root;
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
            category = category.getParentId();
        }
        return true;
    }
}
