package com.kernel.member.repository;

import com.kernel.global.common.enums.ErrorCode;
import com.kernel.global.common.enums.UserRole;
import com.kernel.global.common.enums.UserStatus;
import com.kernel.global.common.exception.AuthException;
import com.kernel.global.domain.entity.QFile;
import com.kernel.global.domain.entity.QUser;
import com.kernel.member.common.enums.ContractStatus;
import com.kernel.member.domain.entity.*;
import com.kernel.member.service.common.info.AdminManagerDetailInfo;
import com.kernel.member.service.common.info.ManagerSummaryInfo;
import com.kernel.member.service.request.AdminManagerSearchReqDTO;


import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomManagerRepositoryImpl implements CustomManagerRepository{

    private final JPQLQueryFactory jpaQueryFactory;
    private final QUser user = QUser.user;
    private final QUserInfo userInfo = QUserInfo.userInfo;
    private final QManager manager = QManager.manager;
    private final QManagerStatistic managerStatistic = QManagerStatistic.managerStatistic;
    private final QAvailableTime availableTime = QAvailableTime.availableTime;
    private final QManagerTermination managerTermination = QManagerTermination.managerTermination;
    private final QFile file = QFile.file;

    /**
     * 매니저 목록을 검색하고 페이징 처리된 결과를 반환합니다.
     *
     * @param request 검색 조건을 포함하는 DTO
     * @param pageable 페이징 정보
     * @return 매니저 목록과 총 개수를 포함하는 Page 객체
     */
    @Override
    public Page<ManagerSummaryInfo> searchManagers(AdminManagerSearchReqDTO request, Pageable pageable) {

        // QueryDSL을 사용하여 쿼리 실행
        List<ManagerSummaryInfo> managers = applySorting(
                jpaQueryFactory
                .select(Projections.fields(ManagerSummaryInfo.class,
                        user.userId,
                        user.userName,
                        user.phone,
                        user.email,
                        managerStatistic.averageRating,
                        user.status,
                        manager.bio,
                        manager.contractStatus,
                        managerStatistic.reservationCount,
                        managerStatistic.reviewCount,
                        file.filePathsJson))
                .from(user)
                .leftJoin(manager).on(manager.user.eq(user)).fetchJoin()
                .leftJoin(userInfo).on(userInfo.user.eq(user)).fetchJoin()
                .leftJoin(managerStatistic).on(managerStatistic.user.eq(user)).fetchJoin()
                .leftJoin(file).on(file.fileId.eq(manager.profileImageFileId.fileId))
                .where(
                        authorEq(request.getUserName()),
                        authorRoleEq(),
                        phoneEq(request.getPhone()),
                        emailEq(request.getEmail()),
                        statusEq(request.getStatus()),
                        contractStatusIn(request.getContractStatus()),
                        statusNotIn(request.getExcludeStatus())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()),
                pageable
        ).fetch();

        long total = jpaQueryFactory.selectFrom(user)
                .leftJoin(manager).on(manager.user.eq(user)).fetchJoin()
                .leftJoin(userInfo).on(userInfo.user.eq(user)).fetchJoin()
                .leftJoin(managerStatistic).on(managerStatistic.user.eq(user)).fetchJoin()
                .where(
                        authorEq(request.getUserName()),
                        authorRoleEq(),
                        phoneEq(request.getPhone()),
                        emailEq(request.getEmail()),
                        statusEq(request.getStatus()),
                        contractStatusIn(request.getContractStatus()),
                        statusNotIn(request.getExcludeStatus())
                )
                .fetchCount();

        return new PageImpl<>(managers, pageable, total);
    }

    // 검색 조건을 위한 BooleanExpression 메소드들
    private BooleanExpression authorEq(String userName) {
        return userName != null ? user.userName.eq(userName) : null;
    }

    private BooleanExpression authorRoleEq() {
        return user.role.eq(UserRole.MANAGER);
    }

    private BooleanExpression phoneEq(String phone) {
        return phone != null ? user.phone.eq(phone) : null;
    }

    private BooleanExpression emailEq(String email) {
        return email != null ? user.email.eq(email) : null;
    }

    private BooleanExpression statusEq(UserStatus status) {
        return status != null ? user.status.eq(status) : null;
    }

    private BooleanExpression contractStatusIn(List<ContractStatus> contractStatuses) {
        if (contractStatuses != null && !contractStatuses.isEmpty()) {
            return manager.contractStatus.in(contractStatuses);
        }
        return null;
    }

    private BooleanExpression statusNotIn(List<UserStatus> excludeStatus) {
        if (excludeStatus != null && !excludeStatus.isEmpty()) {
            return user.status.notIn(excludeStatus);
        }
        // 만약 excludeStatus가 null이거나 비어있다면, 조건을 적용하지 않음
        return null;
    }

    private BooleanExpression averageRatingGoe(BigDecimal minRating) {
        return minRating != null ? managerStatistic.averageRating.goe(minRating) : null;
    }

    private BooleanExpression averageRatingLoe(BigDecimal maxRating) {
        return maxRating != null ? managerStatistic.averageRating.loe(maxRating) : null;
    }


    private JPQLQuery<ManagerSummaryInfo> applySorting(JPQLQuery<ManagerSummaryInfo> query, Pageable pageable) {
        if (pageable.getSort().isSorted()) {
            for (Sort.Order order : pageable.getSort()) {
                ComparableExpressionBase<?> path = getPath(order.getProperty());
                if (path != null) {
                    query.orderBy(order.isAscending() ? path.asc() : path.desc());
                }
            }
        }
        return query;
    }

    private ComparableExpressionBase<?> getPath(String property) {
        switch (property) {
            case "averageRating":
                return managerStatistic.averageRating;
            case "reservationCount":
                return managerStatistic.reservationCount;
            case "reviewCount":
                return managerStatistic.reviewCount;
            default:
                return null;
        }
    }

    @Override
    public AdminManagerDetailInfo getAdminManagerDetailInfo(Long managerId) {

        AdminManagerDetailInfo adminManagerDetailInfo = jpaQueryFactory
                .select(Projections.fields(AdminManagerDetailInfo.class,
                        user.userId,
                        user.userName,
                        user.phone,
                        user.email,
                        user.status,
                        userInfo.birthDate,
                        userInfo.gender,
                        userInfo.roadAddress,
                        userInfo.detailAddress,
                        manager.bio,
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(file.filePathsJson)
                                        .from(file)
                                        .where(file.fileId.eq(manager.profileImageFileId.fileId)),
                                "profileImageFilePath"),
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(file.filePathsJson)
                                        .from(file)
                                        .where(file.fileId.eq(manager.fileId.fileId)),
                                "filePaths"),
                        manager.contractStatus,
                        manager.contractDate,
                        managerStatistic.averageRating,
                        managerStatistic.reservationCount,
                        managerStatistic.reviewCount,
                        managerTermination.terminatedAt,
                        managerTermination.reason,
                        managerTermination.requestAt,
                        user.createdAt,
                        user.updatedAt
                ))
                .from(user)
                .leftJoin(userInfo).on(userInfo.user.eq(user))
                .leftJoin(manager).on(manager.user.eq(user))
                .leftJoin(managerStatistic).on(managerStatistic.user.eq(user))
                .leftJoin(managerTermination).on(managerTermination.manager.eq(manager))
                .where(user.userId.eq(managerId))
                .fetchOne();

        if (adminManagerDetailInfo == null) {
            throw new AuthException(ErrorCode.USER_NOT_FOUND);
        }

        return adminManagerDetailInfo;
    }
}
