package com.kernel.reservation.repository;

import com.kernel.global.common.enums.UserRole;
import com.kernel.global.domain.entity.QUser;
import com.kernel.reservation.common.enums.MatchStatus;
import com.kernel.reservation.domain.entity.*;
import com.kernel.reservation.domain.enums.ReservationStatus;
import com.kernel.reservation.service.info.ManagerReservationDetailInfo;
import com.kernel.reservation.service.info.ManagerReservationSummaryInfo;
import com.kernel.reservation.service.request.ManagerReservationSearchCondDTO;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQueryFactory;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomManagerReservationRepositoryImpl implements CustomManagerReservationRepository {

    private final JPQLQueryFactory jpaQueryFactory;

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
        QReservation reservation = QReservation.reservation;
        QReservationSchedule reservationSchedule = QReservationSchedule.reservationSchedule;
        QServiceCheckLog serviceCheckLog = QServiceCheckLog.serviceCheckLog;
        QReservationMatch reservationMatch = QReservationMatch.reservationMatch;

        // 전체 개수 조회
        long total = Optional.ofNullable(
            jpaQueryFactory
                .select(reservation.count())
                .from(reservation)
                .leftJoin(reservationSchedule).on(reservationSchedule.reservation.reservationId.eq(reservation.reservationId))
                .leftJoin(serviceCheckLog).on(serviceCheckLog.reservation.reservationId.eq(reservation.reservationId))
                .leftJoin(reservationMatch).on(reservationMatch.reservation.reservationId.eq(reservation.reservationId))
                .where(
                    managerEq(managerId, reservation.reservationId.longValue(), reservationMatch), // 매니저 ID 일치
                    RequestDateGoe(searchCondDTO.getFromRequestDate()),         // 청소 예약 날짜 >= 시작일
                    RequestDateLoe(searchCondDTO.getToRequestDate()),           // 청소 예약 날짜 <= 종료일
                    reservationStatus(searchCondDTO.getReservationStatus()),    // 예약 상태
                    isCheckedIn(searchCondDTO.getIsCheckedIn()),                // 체크인 여부
                    isCheckedOut(searchCondDTO.getIsCheckedOut()),              // 체크아웃 여부
                    customerNameLike(searchCondDTO.customerNameKeyword)         // 고객명 검색어 포함
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
                        reservation.user.userId,
                        reservation.serviceCategory.serviceName,
                        reservation.status,
                        serviceCheckLog.reservation,
                        Expressions.booleanTemplate("CASE WHEN {0} IS NOT NULL THEN true ELSE false END", serviceCheckLog.inTime).as("isCheckedIn"),
                        serviceCheckLog.inTime,
                        Expressions.booleanTemplate("CASE WHEN {0} IS NOT NULL THEN true ELSE false END", serviceCheckLog.outTime).as("isCheckedOut"),
                        serviceCheckLog.outTime
                ))
                .from(reservation)
                .leftJoin(reservationSchedule).on(reservationSchedule.reservation.reservationId.eq(reservation.reservationId))
                .leftJoin(serviceCheckLog).on(serviceCheckLog.reservation.reservationId.eq(reservation.reservationId))
                .leftJoin(reservationMatch).on(reservationMatch.reservation.reservationId.eq(reservation.reservationId))
                .where(
                        managerEq(managerId, reservation.reservationId.longValue(), reservationMatch),
                        RequestDateGoe(searchCondDTO.getFromRequestDate()),
                        RequestDateLoe(searchCondDTO.getToRequestDate()),
                        reservationStatus(searchCondDTO.getReservationStatus()),
                        isCheckedIn(searchCondDTO.getIsCheckedIn()),
                        isCheckedOut(searchCondDTO.getIsCheckedOut()),
                        customerNameLike(searchCondDTO.customerNameKeyword)
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
        QReservation reservation = QReservation.reservation;
        QReservationSchedule reservationSchedule = QReservationSchedule.reservationSchedule;
        QReservationMatch reservationMatch = QReservationMatch.reservationMatch;
        QServiceCheckLog serviceCheckLog = QServiceCheckLog.serviceCheckLog;
        QServiceCategory serviceCategory = QServiceCategory.serviceCategory;
        QExtraService extraService = QExtraService.extraService;


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
                //.leftJoin(serviceCategory).on(serviceCategory.serviceId.eq(extraService.serviceCategory.serviceId)) -> serviceCategory가 extraService에 포함되는 여부 판단 필요
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
                extraServiceNameExpr,
                reservation.memo,
                serviceCheckLog.reservation,
                serviceCheckLog.inTime,
                serviceCheckLog.inFileId,
                serviceCheckLog.outTime,
                serviceCheckLog.outFileId
            ))
            .from(reservation)
            .leftJoin(reservation.user).on(reservation.user.role.eq(UserRole.CUSTOMER))
            .leftJoin(reservationSchedule).on(reservationSchedule.reservation.reservationId.eq(reservation.reservationId))
            .leftJoin(serviceCheckLog).on(serviceCheckLog.reservation.reservationId.eq(reservation.reservationId))
            .leftJoin(reservationMatch).on(reservationMatch.reservation.reservationId.eq(reservation.reservationId))
            .where(
                reservation.reservationId.eq(reservationId),
                managerEq(managerId, reservation.reservationId, reservationMatch) // 예약 매치 테이블에서 예약 ID가 reservationId와 일치하고 매칭상태가 MATCHED인 매니저 ID가 필요
            )
            .fetchOne();
    }

    // 매니저 ID 일치 ->  예약 매치 테이블에서 예약 ID가 reservationId와 일치하고 매칭상태가 MATCHED인 매니저 ID가 필요
    private BooleanExpression managerEq(Long managerId, NumberExpression<Long> reservationId, QReservationMatch reservationMatch) {
        return QUser.user.userId.eq(managerId)
                .and(QUser.user.role.eq(UserRole.MANAGER))
                .and(reservationMatch.reservation.reservationId.eq(reservationId))
                .and(reservationMatch.status.eq(MatchStatus.MATCHED));
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
    private BooleanExpression customerNameLike(String keyword) {
        return (keyword != null && !keyword.isBlank())
            ? QReservation.reservation.user.userName.eq(keyword)
                .and(QReservation.reservation.user.role.eq(UserRole.CUSTOMER))
            : null;
    }
}
