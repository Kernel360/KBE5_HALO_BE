package com.kernel.settlement.repository;

import com.kernel.global.domain.entity.QUser;
import com.kernel.settlement.domain.QSettlement;
import com.kernel.settlement.domain.Settlement;
import com.kernel.settlement.service.info.AdminSettlementSummaryInfo;
import com.kernel.settlement.service.info.SettledAmountWithPlatformFeeInfo;
import com.kernel.sharedDomain.domain.entity.QReservation;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class CustomAdminSettleRepositoryImpl extends QuerydslRepositorySupport implements CustomAdminSettleRepository {

    private final JPAQueryFactory queryFactory;
    private final QSettlement settlement = QSettlement.settlement;
    private final QUser manager = new QUser("manager");
    private final QUser user = new QUser("user");
    private final QReservation reservation = QReservation.reservation;

    public CustomAdminSettleRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Settlement.class);
        this.queryFactory = queryFactory;
    }

    /**
     * 주급 정산 조회
     * @param reservationIds 조회 조건
     * @param pageable 페이징
     * @return 조회된 정산
     */
    @Override
    public Page<AdminSettlementSummaryInfo> getSettlementWithPaging(List<Long> reservationIds, Pageable pageable) {

        // 1. 정산 조회
        JPQLQuery<AdminSettlementSummaryInfo> query = queryFactory
                .select(Projections.fields(AdminSettlementSummaryInfo.class,
                        settlement.settlementId,
                        settlement.reservation.reservationId,
                        settlement.manager.userId.as("managerId"),
                        settlement.manager.userName.as("managerName"),
                        settlement.totalAmount,
                        settlement.platformFee,
                        settlement.status,
                        settlement.settledAt,
                        new CaseBuilder()
                                .when(settlement.settledBy.eq(0L)).then("자동")
                                .otherwise(user.userName).as("settledBy"),
                        reservation.serviceCategory.serviceName
                ))
                .from(settlement)
                .join(settlement.manager, manager)
                .join(settlement.reservation, reservation)
                .leftJoin(user).on(settlement.settledBy.eq(user.userId).and(settlement.settledBy.ne(0L)))
                .where(
                        settlement.reservation.reservationId.in(reservationIds)
                );

        // 2. 정렬
        getQuerydsl().applySorting(pageable.getSort(), query);

        // 3. 페이징 및 조회
        List<AdminSettlementSummaryInfo> content = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 4. 전체 개수 조회
        long total = Optional.ofNullable(
                queryFactory
                        .select(settlement.count())
                        .from(settlement)
                        .leftJoin(settlement.manager, manager)
                        .join(settlement.reservation, reservation)
                        .where(
                                settlement.reservation.reservationId.in(reservationIds)
                        )
                        .fetchOne()
        ).orElse(0L);

        return new PageImpl<>(content, pageable, total);
    }

    /**
     * 예약 ID 기반 정산 조회
     * @param reservationIds 조회 조건
     * @return 조회된 정산 금액
     */
    @Override
    public SettledAmountWithPlatformFeeInfo getSettlementByReservationIds(List<Long> reservationIds) {

        return queryFactory
                .select(Projections.constructor(SettledAmountWithPlatformFeeInfo.class,
                    settlement.totalAmount.sum().castToNum(Long.class).as("totalAmount"),
                    settlement.platformFee.sum().castToNum(Long.class).as("platformFee")
                ))
                .from(settlement)
                .where(
                        settlement.reservation.reservationId.in(reservationIds)
                )
                .fetchOne();
    }

    // 정산 시작 일시 조건
    private BooleanExpression requestDateGoe(LocalDate from) {
        return from != null?
                settlement.settledAt.goe(from.atStartOfDay()) : null;
    }

    // 정산 마감 일시 조건
    private BooleanExpression requestDateLoe(LocalDate to) {
        return to != null?
                settlement.settledAt.loe(to.plusDays(1).atStartOfDay()) : null;
    }

    // 매니저 이름 조건
    private BooleanExpression managerNameContains(String keyword) {
        return (keyword != null && !keyword.isBlank())
                ? settlement.manager.userName.contains(keyword) : null;
    }

}
