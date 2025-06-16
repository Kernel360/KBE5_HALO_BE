package com.kernel.common.manager.repository;

import com.kernel.common.reservation.entity.QExtraService;
import com.kernel.common.reservation.entity.QReservation;
import com.kernel.common.reservation.entity.QServiceCategory;
import com.kernel.common.reservation.enums.ReservationStatus;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQueryFactory;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomManagerPaymentRepositoryImpl implements CustomManagerPaymentRepositroy {

    private final JPQLQueryFactory jpaQueryFactory;
    private final QReservation reservation = QReservation.reservation;
    private final QExtraService extraService = QExtraService.extraService;
    QServiceCategory baseServiceCategory = new QServiceCategory("baseServiceCategory");   // 기본 서비스용
    QServiceCategory extraServiceCategory = new QServiceCategory("extraServiceCategory"); // 추가 서비스용

    /**
     * 매니저 주급 정산 조회
     * @param managerId 매니저ID
     * @param startDate 주차시작일
     * @param endDate 주차종료일
     * @return 매니저 주급 정산 내역을 담은 리스트
     */
    @Override
    public List<Tuple> getManagerPayments(Long managerId, LocalDate startDate, LocalDate endDate) {

        return jpaQueryFactory
            .select(
                reservation.reservationId,
                reservation.customer.userName,
                reservation.requestDate,
                reservation.startTime,
                reservation.turnaround,
                baseServiceCategory.serviceName,
                reservation.price,
                extraService.extraServiceId,
                extraServiceCategory.serviceName.as("extraServiceName"),
                extraService.price.as("extraPrice")
            )
            .from(reservation)
            .leftJoin(reservation.serviceCategory, baseServiceCategory) // 기본 서비스 이름용
            .leftJoin(extraService).on(extraService.reservation.eq(reservation))
            .leftJoin(extraService.serviceCategory, extraServiceCategory) // 추가 서비스 이름용
            .where(
                managerEq(managerId),
                reservationStatus(ReservationStatus.COMPLETED),
                requestDateBetween(startDate, endDate)
            )
            .orderBy(
                reservation.requestDate.asc(),
                reservation.startTime.asc(),
                extraService.extraServiceId.asc()
            )
            .fetch();
    }

    // 매니저 ID 일치
    private BooleanExpression managerEq(Long managerId) {
        return QReservation.reservation.manager.managerId.eq(managerId);
    }

    // 예약 상태
    private BooleanExpression reservationStatus(ReservationStatus reservationStatus) {
        return reservationStatus != null
            ? QReservation.reservation.status.eq(reservationStatus)
            : null;
    }

    // 해당 주차수에 포함되는 요청일자
    private BooleanExpression requestDateBetween(LocalDate startDate, LocalDate endDate) {
        return !(startDate == null || endDate == null)
            ? QReservation.reservation.requestDate.between(startDate, endDate)
            : null;
    }
}
