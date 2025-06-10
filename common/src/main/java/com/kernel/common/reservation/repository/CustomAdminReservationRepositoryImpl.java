package com.kernel.common.reservation.repository;

import com.kernel.common.manager.entity.QManager;
import com.kernel.common.reservation.dto.response.AdminReservationDetailRspDTO;
import com.kernel.common.reservation.dto.response.AdminReservationListRspDTO;
import com.kernel.common.reservation.entity.QReservation;
import com.kernel.common.reservation.entity.QServiceCategory;
import com.kernel.common.reservation.enums.ReservationStatus;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CustomAdminReservationRepositoryImpl implements CustomAdminReservationRepository {

    private final JPAQueryFactory queryFactory;
    private final QReservation reservation = QReservation.reservation;
    private final QManager manager = QManager.manager;
    private final QServiceCategory serviceCategory = QServiceCategory.serviceCategory;

    @Override
    public Page<AdminReservationListRspDTO> findReservationList(ReservationStatus status, Pageable pageable) {
        BooleanExpression condition = status != null ? reservation.status.eq(status) : null;

        List<Tuple> tuples = queryFactory
                .select(
                        reservation.reservationId,
                        reservation.requestDate,
                        reservation.startTime,
                        reservation.roadAddress,
                        reservation.detailAddress,
                        manager.userName,
                        reservation.customer.userName,
                        reservation.status,
                        serviceCategory.serviceName
                )
                .from(reservation)
                .leftJoin(reservation.manager, manager)
                .leftJoin(reservation.serviceCategory, serviceCategory)
                .where(condition)
                .orderBy(reservation.requestDate.desc(), reservation.startTime.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<AdminReservationListRspDTO> content = tuples.stream()
                .map(tuple -> AdminReservationListRspDTO.builder()
                        .reservationId(tuple.get(reservation.reservationId))
                        .requestDate(tuple.get(reservation.requestDate))
                        .startTime(tuple.get(reservation.startTime))
                        .roadAddress(tuple.get(reservation.roadAddress))
                        .detailAddress(tuple.get(reservation.detailAddress))
                        .managerName(tuple.get(manager.userName))
                        .customerName(tuple.get(reservation.customer.userName))
                        .status(tuple.get(reservation.status))
                        .serviceName(tuple.get(serviceCategory.serviceName))
                        .build())
                .collect(Collectors.toList());

        long total = Optional.ofNullable(
                queryFactory.select(reservation.count())
                        .from(reservation)
                        .where(condition)
                        .fetchOne()
        ).orElse(0L);

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public AdminReservationDetailRspDTO findReservationDetail(Long reservationId) {
        Tuple tuple = queryFactory
                .select(
                        reservation.reservationId,
                        reservation.requestDate,
                        reservation.startTime,
                        reservation.roadAddress,
                        reservation.detailAddress,
                        manager.userName,
                        manager.phone,
                        reservation.customer.userName,
                        reservation.customer.phone,
                        reservation.status,
                        serviceCategory.serviceName,
                        reservation.price,
                        reservation.memo,
                        reservation.cancelReason
                )
                .from(reservation)
                .leftJoin(reservation.manager, manager)
                .leftJoin(reservation.serviceCategory, serviceCategory)
                .where(reservation.reservationId.eq(reservationId))
                .fetchOne();

        if (tuple == null) {
            throw new NoSuchElementException("해당 예약을 찾을 수 없습니다.");
        }

        return AdminReservationDetailRspDTO.builder()
                .reservationId(tuple.get(reservation.reservationId))
                .requestDate(tuple.get(reservation.requestDate))
                .startTime(tuple.get(reservation.startTime))
                .roadAddress(tuple.get(reservation.roadAddress))
                .detailAddress(tuple.get(reservation.detailAddress))
                .managerName(tuple.get(manager.userName))
                .managerPhone(tuple.get(manager.phone))
                .customerName(tuple.get(reservation.customer.userName))
                .customerPhone(tuple.get(reservation.customer.phone))
                .status(tuple.get(reservation.status))
                .serviceName(tuple.get(serviceCategory.serviceName))
                .price(tuple.get(reservation.price))
                .memo(tuple.get(reservation.memo))
                .cancelReason(Optional.ofNullable(tuple.get(reservation.cancelReason)).orElse(""))
                .build();
    }
}
