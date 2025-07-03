package com.kernel.member.repository;

import com.kernel.global.common.enums.UserRole;
import com.kernel.global.common.enums.UserStatus;
import com.kernel.global.domain.entity.QUser;
import com.kernel.member.domain.entity.QCustomer;
import com.kernel.member.domain.entity.QCustomerStatistic;
import com.kernel.member.domain.entity.QUserInfo;
import com.kernel.member.service.common.info.AdminCustomerSummaryInfo;
import com.kernel.member.service.request.AdminCustomerSearchReqDTO;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
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
public class CustomCustomerRepositoryImpl implements CustomCustomerRepository{

    private final JPQLQueryFactory jpaQueryFactory;
    private final QUser user = QUser.user;
    private final QUserInfo userInfo = QUserInfo.userInfo;
    private final QCustomer customer = QCustomer.customer;
    private final QCustomerStatistic customerStatistic = QCustomerStatistic.customerStatistic;

    /**
     * 관리자 고객 검색 기능을 구현합니다.
     *
     * @param request 검색 요청 DTO
     * @param pageable 페이징 정보
     * @return 고객 정보 페이지
     */
    @Override
    public Page<AdminCustomerSummaryInfo> searchCustomers(AdminCustomerSearchReqDTO request, Pageable pageable) {

        // QueryDSL을 사용하여 쿼리 실행
        List<AdminCustomerSummaryInfo> customers = applySorting(
                jpaQueryFactory
                .select(Projections.fields(AdminCustomerSummaryInfo.class,
                        user.userId,
                        user.userName,
                        user.phone,
                        user.email,
                        user.status,
                        customer.point))
                .from(user)
                .leftJoin(customer).on(customer.user.eq(user)).fetchJoin()
                .leftJoin(userInfo).on(userInfo.user.eq(user)).fetchJoin()
                .where(
                        userRoleEq(),
                        userLike(request.getUserName()),
                        phoneContains(request.getPhone()),
                        emailContains(request.getEmail()),
                        statusIn(request.getStatus())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()),
                pageable
        ).fetch();

        long total = jpaQueryFactory.selectFrom(user)
                .leftJoin(customer).on(customer.user.eq(user)).fetchJoin()
                .leftJoin(userInfo).on(userInfo.user.eq(user)).fetchJoin()
                .where(
                        userRoleEq(),
                        userLike(request.getUserName()),
                        phoneContains(request.getPhone()),
                        emailContains(request.getEmail()),
                        statusIn(request.getStatus())
                )
                .fetchCount();

        return new PageImpl<>(customers, pageable, total);
    }

    // 검색 조건을 위한 BooleanExpression 메소드들
    private BooleanExpression userRoleEq() {
        return user.role.eq(UserRole.CUSTOMER);
    }

    // 키워드가 포함된 사용자 이름, 전화번호, 이메일을 검색하도록 BooleanExpression 메소드들을 정의
    private BooleanExpression userLike(String userName) {
        return userName != null ? user.userName.contains(userName) : null;
    }

    private BooleanExpression phoneContains(String phone) {
        return phone != null ? user.phone.contains(phone) : null;
    }

    private BooleanExpression emailContains(String email) {
        return email != null ? user.email.contains(email) : null;
    }

    private BooleanExpression statusIn(List<UserStatus> status) {
        if (status != null && !status.isEmpty()) {
            return user.status.in(status);
        }
        return null;
    }

    private BooleanExpression averageRatingGoe(BigDecimal minRating) {
        return minRating != null ? customerStatistic.averageRating.goe(minRating) : null;
    }

    private BooleanExpression averageRatingLoe(BigDecimal maxRating) {
        return maxRating != null ? customerStatistic.averageRating.loe(maxRating) : null;
    }

    private JPQLQuery<AdminCustomerSummaryInfo> applySorting(JPQLQuery<AdminCustomerSummaryInfo> query, Pageable pageable) {
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
            case "point":
                return customer.point;
            default:
                return null;
        }
    }
}
