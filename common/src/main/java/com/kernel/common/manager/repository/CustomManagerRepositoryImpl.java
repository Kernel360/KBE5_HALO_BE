package com.kernel.common.manager.repository;

import com.kernel.common.admin.dto.request.AdminManagerSearchReqDTO;
import com.kernel.common.global.enums.UserStatus;
import com.kernel.common.manager.entity.Manager;
import com.kernel.common.manager.entity.QManager;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomManagerRepositoryImpl implements CustomManagerRepository {

    private final JPQLQueryFactory jpaQueryFactory;
    private final QManager manager = QManager.manager;


    @Override
    public Page<Manager> searchManagers(AdminManagerSearchReqDTO request, Pageable pageable) {

        // QueryDSL을 사용하여 쿼리 실행
        List<Manager> managers = jpaQueryFactory.selectFrom(manager)
                .where(
                        authorEq(request.getUserName()),
                        phoneEq(request.getPhone()),
                        emailEq(request.getEmail()),
                        statusEq(request.getStatus()),
                        statusNotIn(request.getExcludeStatus()),
                        averageRatingGoe(request.getMinRating()),
                        averageRatingLoe(request.getMaxRating())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = jpaQueryFactory.selectFrom(manager)
                .where(
                        authorEq(request.getUserName()),
                        phoneEq(request.getPhone()),
                        emailEq(request.getEmail()),
                        statusEq(request.getStatus()),
                        statusNotIn(request.getExcludeStatus()),
                        averageRatingGoe(request.getMinRating()),
                        averageRatingLoe(request.getMaxRating())
                )
                .fetchCount();

        return new PageImpl<>(managers, pageable, total);
    }

    private BooleanExpression authorEq(String userName) {
        return userName != null ? QManager.manager.userName.eq(userName) : null;
    }

    private BooleanExpression phoneEq(String phone) {
        return phone != null ? QManager.manager.phone.eq(phone) : null;
    }

    private BooleanExpression emailEq(String email) {
        return email != null ? QManager.manager.email.eq(email) : null;
    }

    private BooleanExpression statusEq(String status) {
        if (status == null) {
            return null;
        }
        UserStatus userStatus = UserStatus.valueOf(status);
        return status != null ? QManager.manager.status.eq(userStatus) : null;
    }

    private BooleanExpression statusNotIn(List<String> excludeStatus) {
        if (excludeStatus == null || excludeStatus.isEmpty()) {
            return null;
        }
        return QManager.manager.status.notIn(excludeStatus.stream()
                .map(UserStatus::valueOf)
                .toList());
    }

    private BooleanExpression averageRatingGoe(BigDecimal minRating) {
        return minRating != null ? QManager.manager.averageRating.goe(minRating) : null;
    }

    private BooleanExpression averageRatingLoe(BigDecimal maxRating) {
        return maxRating != null ? QManager.manager.averageRating.loe(maxRating) : null;
    }
}