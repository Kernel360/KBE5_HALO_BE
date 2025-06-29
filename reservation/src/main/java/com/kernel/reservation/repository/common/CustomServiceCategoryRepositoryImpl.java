package com.kernel.reservation.repository.common;

import com.kernel.sharedDomain.domain.entity.QServiceCategory;
import com.kernel.sharedDomain.domain.entity.ServiceCategory;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class CustomServiceCategoryRepositoryImpl implements CustomServiceCategoryRepository {

    private final JPAQueryFactory queryFactory;
    private final QServiceCategory serviceCategory = QServiceCategory.serviceCategory;

    @Override
    public List<ServiceCategory> getRequestServiceCategory(Long serviceId, List<Long> extraServiceIds) {

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

        return filteredCategories;

    }


}
