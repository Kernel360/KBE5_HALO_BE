package com.kernel.common.admin.repository;

import com.kernel.common.admin.dto.request.AdminSearchReqDTO;
import com.kernel.common.admin.entity.Admin;
import com.kernel.common.admin.entity.QAdmin;
import com.kernel.common.global.enums.UserStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class CustomAdminRepositoryImpl implements CustomAdminRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Admin> searchByConditionsWithPaging(AdminSearchReqDTO request, Pageable pageable) {
        QAdmin admin = QAdmin.admin;

        // 조건 추가
        var query = queryFactory.selectFrom(admin)
                .where(
                        admin.userName.containsIgnoreCase(request.getUserName() != null ? request.getUserName() : ""),
                        admin.email.containsIgnoreCase(request.getEmail() != null ? request.getEmail() : ""),
                        admin.phone.containsIgnoreCase(request.getPhone() != null ? request.getPhone() : ""),
                        admin.status.eq(request.getStatus() != null ? request.getStatus() : UserStatus.ACTIVE)
                )
                .orderBy(admin.adminId.desc()); // 생성일 기준 내림차순 정렬

        // 페이징 처리
        List<Admin> admins = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 전체 개수
        long total = query.fetch().size();

        return new PageImpl<>(admins, pageable, total);
    }
}