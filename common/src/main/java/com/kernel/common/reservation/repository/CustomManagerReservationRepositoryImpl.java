package com.kernel.common.reservation.repository;

import com.kernel.common.customer.entity.QCustomer;
import com.kernel.common.global.enums.AuthorType;
import com.kernel.common.manager.entity.QCleaningLog;
import com.kernel.common.manager.entity.QManager;
import com.kernel.common.reservation.entity.QReservation;
import com.kernel.common.reservation.entity.QReview;
import com.kernel.common.reservation.enums.ReservationStatus;
import com.kernel.common.reservation.dto.request.ManagerReservationSearchCondDTO;
import com.kernel.common.reservation.dto.response.ManagerReservationRspDTO;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
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

    @Override
    public Page<Tuple> searchManagerReservationsWithPaging(
        Long managerId, ManagerReservationSearchCondDTO searchCondDTO, Pageable pageable
    ) {
        QReservation reservation = QReservation.reservation;
        QManager manager = QManager.manager;
        QCleaningLog cleaningLog = QCleaningLog.cleaningLog;
        QReview review = QReview.review;

        // 전체 개수 조회
        long total = Optional.ofNullable(
            jpaQueryFactory
                .select(reservation.count())
                .from(reservation)
                .leftJoin(cleaningLog).on(cleaningLog.reservation.reservationId.eq(reservation.reservationId))
                .leftJoin(review).on(
                    review.reservation.reservationId.eq(reservation.reservationId)
                        .and(review.authorType.eq(AuthorType.MANAGER))
                        .and(review.authorId.eq(managerId))
                )
                .where(
                    managerEq(managerId),                                       // 매니저 ID 일치
                    RequestDateGoe(searchCondDTO.getFromRequestDate()),         // 청소 예약 날짜 >= 시작일
                    RequestDateLoe(searchCondDTO.getToRequestDate()),           // 청소 예약 날짜 <= 종료일
                    reservationStatus(searchCondDTO.getReservationStatus()),    // 예약 상태
                    isCheckedIn(searchCondDTO.getIsCheckedIn()),                // 체크인 여부
                    isCheckedOut(searchCondDTO.getIsCheckedOut()),              // 체크아웃 여부
                    isReviewed(searchCondDTO.getIsReviewed()),                  // 리뷰 작성 여부
                    customerNameLike(searchCondDTO.customerNameKeyword)         // 고객명 검색어 포함
                )
                .fetchOne()
        ).orElse(0L);

        // 페이지 결과 조회
        List<Tuple> results = jpaQueryFactory
            .select(
                // 예약 ID
                reservation.reservationId.as("reservationId"),

                // 요청 날짜
                reservation.requestDate.as("requestDate"),

                // 고객명
                reservation.customer.userName.as("customerName"),

                // 고객 주소
                Expressions.stringTemplate(
                    "CONCAT({0}, ', ', {1})",
                    reservation.customer.roadAddress,
                    reservation.customer.detailAddress
                ).as("customerAddress"),

                // 서비스명
                reservation.serviceCategory.serviceName.as("serviceName"),

                // 상태
                reservation.status.as("status"),

                // 체크 ID
                cleaningLog.checkId.as("checkId"),

                // 체크인 여부
                new CaseBuilder()
                    .when(cleaningLog.inTime.isNotNull()).then(true)
                    .otherwise(false)
                    .as("isCheckedIn"),

                // 체크인 일시
                cleaningLog.inTime.as("inTime"),

                // 체크아웃 여부
                new CaseBuilder()
                    .when(cleaningLog.outTime.isNotNull()).then(true)
                    .otherwise(false)
                    .as("isCheckedOut"),

                // 체크아웃 일시
                cleaningLog.outTime.as("outTime"),

                // 리뷰 ID
                review.reviewId.as("managerReviewId"),

                // 리뷰 여부
                new CaseBuilder()
                    .when(review.reviewId.isNotNull()).then(true)
                    .otherwise(false)
                    .as("isReviewed")
            )
            .from(reservation)
            .leftJoin(cleaningLog).on(cleaningLog.reservation.reservationId.eq(reservation.reservationId))
            .leftJoin(review).on(
                review.reservation.reservationId.eq(reservation.reservationId)
                    .and(review.authorType.eq(AuthorType.MANAGER))
                    .and(review.authorId.eq(managerId))
            )
            .where(
                managerEq(managerId),
                RequestDateGoe(searchCondDTO.getFromRequestDate()),
                RequestDateLoe(searchCondDTO.getToRequestDate()),
                reservationStatus(searchCondDTO.getReservationStatus()),
                isCheckedIn(searchCondDTO.getIsCheckedIn()),
                isCheckedOut(searchCondDTO.getIsCheckedOut()),
                isReviewed(searchCondDTO.getIsReviewed()),
                customerNameLike(searchCondDTO.customerNameKeyword)
            )
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(reservation.reservationId.desc())
            .fetch();

        return new PageImpl<>(results, pageable, total);
    }

    @Override
    public ManagerReservationRspDTO findByManagerIdAndReservationId(
        Long managerId, Long reservationId
    )
    {
        QReservation reservation = QReservation.reservation;
        QCustomer customer = QCustomer.customer;
        QCleaningLog cleaningLog = QCleaningLog.cleaningLog;
        QReview customerReview = new QReview("customerReview");
        QReview managerReview = new QReview("managerReview");

        return jpaQueryFactory
            .select(Projections.bean(ManagerReservationRspDTO.class,
                // 예약ID
                reservation.reservationId.stringValue().as("reservationId"),
                // 청소 요청 날짜
                reservation.requestDate.stringValue().as("requestDate"),
                // 서비스명
                reservation.serviceCategory.serviceName.as("serviceName"),
                // 상태
                reservation.status.as("status"),

                // 고객명
                customer.userName.as("customerName"),
                // 고객 연락처
                customer.phone.as("customerPhone"),
                // 고객 주소
                Expressions.stringTemplate("CONCAT({0}, ', ', {1})",
                    customer.roadAddress, customer.detailAddress).as("customerAddress"),

                // 고객 요청사항
                reservation.memo.as("memo"),

                // 체크ID
                cleaningLog.checkId.as("checkId"),
                // 체크인 일시
                cleaningLog.inTime.as("inTime"),
                // 체크인 첨부파일
                cleaningLog.inFileId.as("inFileId"),
                // 체크아웃 일시
                cleaningLog.outTime.as("outTime"),
                // 체크아웃 첨부파일
                cleaningLog.outFileId.as("outFileId"),

                // 수요자 리뷰ID
                customerReview.reviewId.as("customerReviewId"),
                // 수요자 리뷰 평점
                customerReview.rating.as("customerRating"),
                // 수요자 리뷰 내용
                customerReview.content.as("customerContent"),
                // 수요자 리뷰 작성일시
                customerReview.createdAt.as("customerCreateAt"),

                // 매니저 리뷰ID
                managerReview.reviewId.as("managerReviewId"),
                // 매니저 리뷰 평점
                managerReview.rating.as("managerRating"),
                // 매니저 리뷰 내용
                managerReview.content.as("managerContent"),
                // 매니저 리뷰 작성일시
                managerReview.createdAt.as("managerCreateAt")
            ))
            .from(reservation)
            .leftJoin(reservation.customer, customer)
            .leftJoin(cleaningLog)
            .on(cleaningLog.reservation.reservationId.eq(reservation.reservationId))
            .leftJoin(customerReview).on(
                customerReview.reservation.reservationId.eq(reservation.reservationId)
                    .and(customerReview.authorType.eq(AuthorType.CUSTOMER))
            )
            .leftJoin(managerReview).on(
                managerReview.reservation.reservationId.eq(reservation.reservationId)
                    .and(managerReview.authorType.eq(AuthorType.MANAGER))
                    .and(managerReview.authorId.eq(managerId))
            )
            .where(
                reservation.reservationId.eq(reservationId),
                reservation.manager.managerId.eq(managerId)
            )
            .fetchOne();
    }

    // 매니저ID 일치
    private BooleanExpression managerEq(Long managerId) {
        return QReservation.reservation.manager.managerId.eq(managerId);
    }

    // 청소 예약 날짜 >= 시작일
    private BooleanExpression RequestDateGoe(LocalDate from) {
        return from != null
            ? QReservation.reservation.requestDate.goe(from)
            : null;
    }

    // 청소 예약 날짜 <= 종료일
    private BooleanExpression RequestDateLoe(LocalDate to) {
        return to != null
            ? QReservation.reservation.requestDate.loe(to)
            : null;
    }

    // 예약 상태
    private BooleanExpression reservationStatus(ReservationStatus reservationStatus) {
        return reservationStatus != null
            ? QReservation.reservation.status.eq(reservationStatus)
            : null;
    }

    // 체크인 여부
    private BooleanExpression isCheckedIn(Boolean isCheckedIn) {
        if (isCheckedIn == null) return null;
        return isCheckedIn
            ? QCleaningLog.cleaningLog.inTime.isNotNull()
            : QCleaningLog.cleaningLog.inTime.isNull();
    }

    // 체크아웃 여부
    private BooleanExpression isCheckedOut(Boolean isCheckedOut) {
        if (isCheckedOut == null) return null;
        return isCheckedOut
            ? QCleaningLog.cleaningLog.outTime.isNotNull()
            : QCleaningLog.cleaningLog.outTime.isNull();
    }

    // 리뷰 작성 여부
    private BooleanExpression isReviewed(Boolean isReviewed) {
        if (isReviewed == null) return null;
        return isReviewed
            ? QReview.review.reviewId.isNotNull()
            : QReview.review.reviewId.isNull();
    }

    // 고객명 검색어 포함
    private BooleanExpression customerNameLike(String keyword) {
        return (keyword != null && !keyword.isBlank())
            ? QCustomer.customer.userName.containsIgnoreCase(keyword)
            : null;
    }
}
