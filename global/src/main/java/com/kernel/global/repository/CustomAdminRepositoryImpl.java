package com.kernel.global.repository;

import com.kernel.global.common.enums.UserRole;
import com.kernel.global.common.enums.UserStatus;
import com.kernel.global.domain.entity.QUser;
import com.kernel.global.domain.info.AdminUserSearchInfo;
import com.kernel.global.service.dto.condition.AdminUserSearchCondition;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomAdminRepositoryImpl implements CustomAdminRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<AdminUserSearchInfo> searchByConditionsWithPaging(AdminUserSearchCondition request, Pageable pageable) {
        QUser user = QUser.user;

        // 전체 개수
        Long total = Optional.ofNullable(
                queryFactory
                        .select(user.count())
                        .from(user)
                        .where(
                                user.userName.containsIgnoreCase(request.getUserName() != null ? request.getUserName() : ""),
                                user.email.containsIgnoreCase(request.getEmail() != null ? request.getEmail() : ""),
                                user.phone.containsIgnoreCase(request.getPhone() != null ? request.getPhone() : ""),
                                user.status.eq(request.getStatus() != null ? request.getStatus() : UserStatus.ACTIVE),
                                user.role.eq(UserRole.ADMIN) // 관리자만 조회
                        )
                        .fetchOne()
        ).orElse(0L);

        // 페이지 조회
        List<AdminUserSearchInfo> admins = queryFactory
                .select(Projections.fields(AdminUserSearchInfo.class,
                        user.userId,
                        user.userName,
                        user.phone,
                        user.email,
                        user.status,
                        user.createdAt
                ))
                .from(user)
                .where(
                        user.userName.containsIgnoreCase(request.getUserName() != null ? request.getUserName() : ""),
                        user.email.containsIgnoreCase(request.getEmail() != null ? request.getEmail() : ""),
                        user.phone.containsIgnoreCase(request.getPhone() != null ? request.getPhone() : ""),
                        user.status.eq(request.getStatus() != null ? request.getStatus() : UserStatus.ACTIVE),
                        user.role.eq(UserRole.ADMIN)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(user.userId.desc())
                .fetch();

        return new PageImpl<>(admins, pageable, total);
    }
}