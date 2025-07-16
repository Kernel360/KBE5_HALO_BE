package com.kernel.settlement.repository;

import com.kernel.global.domain.entity.QUser;
import com.kernel.settlement.domain.QSettlement;
import com.kernel.settlement.domain.Settlement;
import com.kernel.settlement.service.info.ManagerSettlementSummaryInfo;
import com.kernel.sharedDomain.domain.entity.QReservation;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Optional;

public class CustomManagerSettleRepositoryImpl extends QuerydslRepositorySupport implements CustomManagerSettleRepository {

    private final JPAQueryFactory queryFactory;
    private final QSettlement settlement = QSettlement.settlement;
    private final QUser manager = QUser.user;
    private final QReservation reservation = QReservation.reservation;

    public CustomManagerSettleRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Settlement.class);
        this.queryFactory = queryFactory;
    }

    /**
     * 정산 조회
     * @param cond 조회 조건
     * @param pageable 페이징
     * @return 조회된 정산
     */
    @Override
    public Page<ManagerSettlementSummaryInfo> getSettlementWithPaging(List<Long> reservationIds, Pageable pageable, Long userId) {

        // 1. 정산 조회
        JPQLQuery<ManagerSettlementSummaryInfo> query = queryFactory
                .select(Projections.fields(ManagerSettlementSummaryInfo.class,
                        settlement.settlementId,
                        settlement.reservation.reservationId,
                        settlement.totalAmount,
                        settlement.status,
                        settlement.settledAt
                ))
                .from(settlement)
                .leftJoin(settlement.manager, manager)
                .join(settlement.reservation, reservation)
                .where(
                        reservation.reservationId.in(reservationIds),
                        settlement.manager.userId.eq(userId)
                );

        // 2. 정렬
        getQuerydsl().applySorting(pageable.getSort(), query);

        // 3. 페이징 및 조회
        List<ManagerSettlementSummaryInfo> content = query
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
                                reservation.reservationId.in(reservationIds),
                                settlement.manager.userId.eq(userId)
                        )
                        .fetchOne()
        ).orElse(0L);

        return new PageImpl<>(content, pageable, total);
    }

}
