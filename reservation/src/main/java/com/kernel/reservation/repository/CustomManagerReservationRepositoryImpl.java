package com.kernel.reservation.repository;

import com.kernel.global.common.enums.UserRole;
import com.kernel.global.domain.entity.QUser;
import com.kernel.member.domain.entity.QUserInfo;
import com.kernel.reservation.domain.entity.*;
import com.kernel.reservation.service.info.ManagerReservationDetailInfo;
import com.kernel.reservation.service.info.ManagerReservationSummaryInfo;
import com.kernel.reservation.service.request.ManagerReservationSearchCondDTO;
import com.kernel.sharedDomain.common.enums.ReservationStatus;
import com.kernel.sharedDomain.domain.entity.QReservation;
import com.kernel.sharedDomain.domain.entity.QServiceCategory;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomManagerReservationRepositoryImpl implements CustomManagerReservationRepository {

    private final JPQLQueryFactory jpaQueryFactory;
    private final QUser user = QUser.user;
    private final QUserInfo userInfo = QUserInfo.userInfo;
    private final QReservation reservation = QReservation.reservation;
    private final QReservationSchedule reservationSchedule = QReservationSchedule.reservationSchedule;
    private final QServiceCheckLog serviceCheckLog = QServiceCheckLog.serviceCheckLog;
    private final QReservationMatch reservationMatch = QReservationMatch.reservationMatch;
    private final QReservationCancel reservationCancel = QReservationCancel.reservationCancel;
    private final QServiceCategory serviceCategory = QServiceCategory.serviceCategory;
    private final QExtraService extraService = QExtraService.extraService;


    /**
     * 매니저에게 할당된 예약 목록 조회 (검색 조건 및 페이징 처리)
     * @param managerId 매니저ID
     * @param searchCondDTO 검색조건DTO
     * @param pageable 페이징
     * @return 조건에 맞는 예약 정보를 담은 Page 객체
     */
    @Override
    public Page<ManagerReservationSummaryInfo> searchManagerReservationsWithPaging(
            Long managerId, ManagerReservationSearchCondDTO searchCondDTO, Pageable pageable
    ) {
        // 전체 개수 조회
        long total = Optional.ofNullable(
            jpaQueryFactory
                .select(reservation.count())
                .from(reservation) // reservation을 루트로 설정
                .leftJoin(reservationSchedule).on(reservationSchedule.reservation.eq(reservation))
                .leftJoin(serviceCheckLog).on(serviceCheckLog.reservation.eq(reservation))
                .leftJoin(reservationMatch).on(reservationMatch.reservation.eq(reservation))
                .leftJoin(reservation.serviceCategory, serviceCategory)
                .leftJoin(user).on(reservation.user.eq(user))
                .leftJoin(userInfo).on(userInfo.user.eq(user))
                .where(
                    reservationMatch.manager.userId.eq(managerId),
                    RequestDateGoe(searchCondDTO.getFromRequestDate()),         // 청소 예약 날짜 >= 시작일
                    RequestDateLoe(searchCondDTO.getToRequestDate()),           // 청소 예약 날짜 <= 종료일
                    reservationStatus(searchCondDTO.getReservationStatus()),
                    isCheckedIn(searchCondDTO.getIsCheckedIn()),
                    isCheckedOut(searchCondDTO.getIsCheckedOut()),
                    customerNameContains(searchCondDTO.getCustomerNameKeyword()),
                    customerAddressContains(searchCondDTO.getCustomerAddressKeyword())
                )
                .fetchOne()
        ).orElse(0L);

        // 페이지 결과 조회
        List<ManagerReservationSummaryInfo> results = jpaQueryFactory
            .select(Projections.fields(ManagerReservationSummaryInfo.class,
                reservation.reservationId,
                reservationSchedule.requestDate,
                reservationSchedule.startTime,
                reservationSchedule.turnaround,
                user.userId,
                user.userName,
                reservation.serviceCategory.serviceName,
                reservation.status,
                serviceCheckLog.reservation,
                Expressions.booleanTemplate("CASE WHEN {0} IS NOT NULL THEN true ELSE false END", serviceCheckLog.inTime).as("isCheckedIn"),
                serviceCheckLog.inTime,
                Expressions.booleanTemplate("CASE WHEN {0} IS NOT NULL THEN true ELSE false END", serviceCheckLog.outTime).as("isCheckedOut"),
                serviceCheckLog.outTime,
                userInfo.roadAddress,
                userInfo.detailAddress
            ))
            .from(reservation)
            .leftJoin(reservationSchedule).on(reservationSchedule.reservation.eq(reservation))
            .leftJoin(serviceCheckLog).on(serviceCheckLog.reservation.eq(reservation))
            .leftJoin(reservationMatch).on(reservationMatch.reservation.eq(reservation))
            .leftJoin(reservation.serviceCategory, serviceCategory)
            .leftJoin(user).on(reservation.user.eq(user))
            .leftJoin(userInfo).on(userInfo.user.eq(user))
            .where(
                reservationMatch.manager.userId.eq(managerId),
                RequestDateGoe(searchCondDTO.getFromRequestDate()),
                RequestDateLoe(searchCondDTO.getToRequestDate()),
                reservationStatus(searchCondDTO.getReservationStatus()),
                isCheckedIn(searchCondDTO.getIsCheckedIn()),
                isCheckedOut(searchCondDTO.getIsCheckedOut()),
                customerNameContains(searchCondDTO.getCustomerNameKeyword()),
                customerAddressContains(searchCondDTO.getCustomerAddressKeyword())
            )
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(reservation.reservationId.desc())
            .fetch();

        return new PageImpl<>(results, pageable, total);
    }

    /**
     * 매니저에게 할당된 예약 상세 조회
     * @param managerId 매니저ID
     * @param reservationId 예약ID
     * @return 매니저 예약 상세 정보 DTO
     */
    @Override
    public ManagerReservationDetailInfo findByManagerIdAndReservationId(
        Long managerId, Long reservationId
    ) {

        // 추가 서비스 조회
        Expression<String> extraServiceNameExpr = ExpressionUtils.as(
            JPAExpressions
                .select(
                    Expressions.stringTemplate(
                        "COALESCE(GROUP_CONCAT({0}), '-')",
                        serviceCategory.serviceName
                    )
                )
                .from(extraService)
                // TODO: extraService와 serviceCategory의 관계를 명확히 정의해야 함
                //.leftJoin(serviceCategory).on(serviceCategory.serviceId.eq(extraService.serviceCategory.serviceId))
                .where(extraService.reservation.reservationId.eq(reservation.reservationId)),
            "extraServiceName"
        );

        return jpaQueryFactory
            .select(Projections.fields(ManagerReservationDetailInfo.class,
                reservation.reservationId,
                reservationSchedule.requestDate,
                reservationSchedule.startTime,
                reservationSchedule.turnaround,
                reservation.serviceCategory.serviceName,
                reservation.status,
                reservation.user.userId,
                //extraServiceNameExpr,
                reservation.memo,
                reservationCancel.cancelDate,
                reservationCancel.canceledById,
                reservationCancel.cancelReason,
                serviceCheckLog.reservation,
                serviceCheckLog.inTime,
                serviceCheckLog.inFileId,
                serviceCheckLog.outTime,
                serviceCheckLog.outFileId,
                user.userName,
                userInfo.roadAddress,
                userInfo.detailAddress
            ))
            .from(reservation)
            .leftJoin(reservationSchedule).on(reservationSchedule.reservation.eq(reservation))
            .leftJoin(serviceCheckLog).on(serviceCheckLog.reservation.eq(reservation))
            .leftJoin(reservationMatch).on(reservationMatch.reservation.eq(reservation))
            .leftJoin(reservation.serviceCategory, serviceCategory)
            .leftJoin(reservationCancel).on(reservationCancel.reservation.eq(reservation))
            .leftJoin(user).on(reservation.user.eq(user))
            .leftJoin(userInfo).on(userInfo.user.eq(user))
            .where(
                reservation.reservationId.eq(reservationId),
                reservationMatch.manager.userId.eq(managerId)
            )
            .fetchOne();
    }

    // 매니저 ID 일치 및 역할이 매니저인 경우
    private BooleanExpression managerEq(Long managerId) {
        return QUser.user.userId.eq(managerId);
                //.and(QUser.user.role.eq(UserRole.MANAGER));
    }

    // 청소 예약 날짜 >= 시작일
    private BooleanExpression RequestDateGoe(LocalDate from) {
        return from != null
            ? QReservationSchedule.reservationSchedule.requestDate.goe(from)
            : null;
    }

    // 청소 예약 날짜 <= 종료일
    private BooleanExpression RequestDateLoe(LocalDate to) {
        return to != null
            ? QReservationSchedule.reservationSchedule.requestDate.loe(to)
            : null;
    }

    // 예약 상태
    private BooleanExpression reservationStatus(List<ReservationStatus> statuses) {
        System.out.println("Reservation Statuses: " + statuses);
        return (statuses != null && !statuses.isEmpty())
            ? QReservation.reservation.status.in(statuses)
            : null;
    }

    // 체크인 여부
    private BooleanExpression isCheckedIn(Boolean isCheckedIn) {
        if (isCheckedIn == null) return null;
        return isCheckedIn
            ? QServiceCheckLog.serviceCheckLog.inTime.isNotNull()
            : QServiceCheckLog.serviceCheckLog.inTime.isNull();
    }

    // 체크아웃 여부
    private BooleanExpression isCheckedOut(Boolean isCheckedOut) {
        if (isCheckedOut == null) return null;
        return isCheckedOut
            ? QServiceCheckLog.serviceCheckLog.outTime.isNotNull()
            : QServiceCheckLog.serviceCheckLog.outTime.isNull();
    }

    /* // 리뷰 작성 여부 -> 클라이언트에서 review 조회 api를 사용해서 따로 조회
    private BooleanExpression isReviewed(Boolean isReviewed) {
        if (isReviewed == null) return null;
        return isReviewed
            ? QReview.review.reviewId.isNotNull()
            : QReview.review.reviewId.isNull();
    }*/

    // 고객명 검색어 포함 -> 검색된 이름 중에 role이 CUSTOMER인 경우만 조회 필요
    private BooleanExpression customerNameContains(String keyword) {
        return (keyword != null && !keyword.isBlank())
            ? QReservation.reservation.user.userName.contains(keyword)
                .and(QReservation.reservation.user.role.eq(UserRole.CUSTOMER))
            : null;
    }

    // 고객 주소 검색어 포함
    private BooleanExpression customerAddressContains(String keyword) {
        return (keyword != null && !keyword.isBlank())
                ? QUserInfo.userInfo.roadAddress.contains(keyword)
                .or(QUserInfo.userInfo.detailAddress.contains(keyword))
                : null;
    }
}
