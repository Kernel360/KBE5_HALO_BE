package com.kernel.reservation.repository;

import com.kernel.global.common.enums.UserRole;
import com.kernel.member.domain.entity.QManager;
import com.kernel.payment.common.enums.PaymentStatus;
import com.kernel.payment.domain.QPayment;
import com.kernel.reservation.domain.entity.QReservationLocation;
import com.kernel.reservation.domain.entity.QReservationMatch;
import com.kernel.reservation.domain.entity.QReservationSchedule;
import com.kernel.reservation.domain.entity.QServiceCheckLog;
import com.kernel.reservation.service.info.AdminReservationDetailInfo;
import com.kernel.reservation.service.info.AdminReservationSummaryInfo;
import com.kernel.reservation.service.request.AdminReservationSearchCondDTO;
import com.kernel.sharedDomain.common.enums.ReservationStatus;
import com.kernel.sharedDomain.domain.entity.QReservation;
import com.kernel.sharedDomain.domain.entity.QServiceCategory;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
public class CustomAdminReservationRepositoryImpl implements CustomAdminReservationRepository {

    private final JPAQueryFactory queryFactory;
    private final QManager manager = QManager.manager;
    private final QReservation reservation = QReservation.reservation;
    private final QReservationMatch match = QReservationMatch.reservationMatch;
    private final QReservationSchedule schedule = QReservationSchedule.reservationSchedule;
    private final QReservationLocation location = QReservationLocation.reservationLocation;
    private final QServiceCheckLog checkLog = QServiceCheckLog.serviceCheckLog;
    private final QServiceCategory serviceCategory = QServiceCategory.serviceCategory;
    private final QPayment payment = QPayment.payment;

    /**
     * 전체 예약 조회(검색 조건 및 페이징 처리)
     * @param searchCondDTO 검색조건DTO
     * @param pageable 페이징
     * @return 검색 조건에 따른 매니저에게 할당된 예약 목록을 응답 (페이징 포함)
     */
    @Override
    public Page<AdminReservationSummaryInfo> findReservationList(AdminReservationSearchCondDTO searchCondDTO, Pageable pageable) {

        List<AdminReservationSummaryInfo> content = queryFactory
                .select(Projections.fields(AdminReservationSummaryInfo.class,
                        reservation.reservationId,
                        schedule.requestDate,
                        schedule.startTime,
                        location.roadAddress,
                        match.manager.userId.as("managerId"),
                        match.manager.userName.as("managerName"),
                        reservation.user.userId.as("customerId"),
                        reservation.user.userName.as("customerName"),
                        reservation.status,
                        serviceCategory.serviceName,
                        reservation.price,
                        payment.status.as("paymentStatus")
                ))
                .from(reservation)
                .leftJoin(schedule).on(schedule.reservation.reservationId.eq(reservation.reservationId))
                .leftJoin(location).on(location.reservation.reservationId.eq(reservation.reservationId))
                .leftJoin(match).on(match.reservation.reservationId.eq(reservation.reservationId))
                .leftJoin(serviceCategory).on(serviceCategory.serviceId.eq(reservation.serviceCategory.serviceId))
                .leftJoin(payment).on(payment.reservation.reservationId.eq(reservation.reservationId))
                .where(
                        RequestDateGoe(searchCondDTO.getFromRequestDate()),
                        RequestDateLoe(searchCondDTO.getToRequestDate()),
                        reservationStatus(searchCondDTO.getReservationStatus()),
                        managerIdEq(searchCondDTO.getManagerId()),
                        managerNameLike(searchCondDTO.getManagerNameKeyword()),
                        customerNameLike(searchCondDTO.getCustomerNameKeyword()),
                        paymentStatus(searchCondDTO.getPaymentStatus())
                )
                .orderBy(schedule.requestDate.desc(), schedule.startTime.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = Optional.ofNullable(
                queryFactory.select(reservation.count())
                        .from(reservation)
                        .leftJoin(schedule).on(schedule.reservation.reservationId.eq(reservation.reservationId))
                        .leftJoin(match).on(match.reservation.reservationId.eq(reservation.reservationId))
                        .leftJoin(payment).on(payment.reservation.reservationId.eq(reservation.reservationId))
                        .where(
                                RequestDateGoe(searchCondDTO.getFromRequestDate()),
                                RequestDateLoe(searchCondDTO.getToRequestDate()),
                                reservationStatus(searchCondDTO.getReservationStatus()),
                                managerIdEq(searchCondDTO.getManagerId()),
                                managerNameLike(searchCondDTO.getManagerNameKeyword()),
                                customerNameLike(searchCondDTO.getCustomerNameKeyword()),
                                paymentStatus(searchCondDTO.getPaymentStatus())
                        )
                        .fetchOne()
        ).orElse(0L);

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public AdminReservationDetailInfo findReservationDetail(Long reservationId) {
        AdminReservationDetailInfo result = queryFactory
                .select(Projections.fields(AdminReservationDetailInfo.class,
                        reservation.reservationId,
                        schedule.requestDate,
                        schedule.startTime,
                        location.roadAddress,
                        location.detailAddress,
                        match.manager.userId.as("managerId"),
                        match.manager.userName.as("managerName"),
                        match.manager.phone.as("managerPhone"),
                        reservation.user.userId.as("customerId"),
                        reservation.user.userName.as("customerName"),
                        reservation.user.phone.as("customerPhone"),
                        reservation.status.as("reservationStatus"),
                        serviceCategory.serviceName,
                        reservation.price,
                        reservation.memo,
                        payment.amount,
                        payment.paymentMethod,
                        payment.status.as("paymentStatus"),
                        payment.paidAt
                ))
                .from(reservation)
                .leftJoin(schedule).on(schedule.reservation.reservationId.eq(reservation.reservationId))
                .leftJoin(location).on(location.reservation.reservationId.eq(reservation.reservationId))
                .leftJoin(match).on(match.reservation.reservationId.eq(reservation.reservationId))
                .leftJoin(serviceCategory).on(serviceCategory.serviceId.eq(reservation.serviceCategory.serviceId))
                .leftJoin(payment).on(payment.reservation.reservationId.eq(reservation.reservationId))
                .where(reservation.reservationId.eq(reservationId))
                .fetchOne();

        if (result == null) {
            throw new NoSuchElementException("해당 예약을 찾을 수 없습니다.");
        }

        return result;
    }

    // 청소 예약 날짜 >= 시작일
    private BooleanExpression RequestDateGoe(LocalDate from) {
        return from != null
                ? schedule.requestDate.goe(from)
                : null;
    }

    // 청소 예약 날짜 <= 종료일
    private BooleanExpression RequestDateLoe(LocalDate to) {
        return to != null
                ? schedule.requestDate.loe(to)
                : null;
    }

    // 예약 상태
    private BooleanExpression reservationStatus(List<ReservationStatus> statuses) {
        return (statuses != null && !statuses.isEmpty())
                ? reservation.status.in(statuses)
                : null;
    }

    // 결제 상태
    private BooleanExpression paymentStatus(List<PaymentStatus> paymentStatuses) {
        return (paymentStatuses != null && !paymentStatuses.isEmpty())
                ? payment.status.in(paymentStatuses)
                : null;
    }

    // 매니저 검색어 포함 -> 매니저가 존재하고 검색된 이름 중에 role이 MANAGER인 경우만 조회
    private BooleanExpression managerNameLike(String keyword) {
        return (keyword != null && !keyword.isBlank())
                ? match.manager.isNotNull()
                .and(match.manager.userName.contains(keyword))
                .and(match.manager.role.eq(UserRole.MANAGER))
                : null;
    }

    // 고객명 검색어 포함 -> 고객이 존재하고 검색된 이름 중에 role이 CUSTOMER인 경우만 조회
    private BooleanExpression customerNameLike(String keyword) {
        return (keyword != null && !keyword.isBlank())
                ? reservation.user.isNotNull()
                .and(reservation.user.userName.contains(keyword))
                .and(reservation.user.role.eq(UserRole.CUSTOMER))
                : null;
    }

    // 매니저 ID로 조회
    private BooleanExpression managerIdEq(Long managerId) {
        return (managerId != null)
                ? match.manager.userId.eq(managerId)
                : null;
    }
}
