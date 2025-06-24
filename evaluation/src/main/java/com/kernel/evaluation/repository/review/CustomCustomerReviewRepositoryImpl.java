package com.kernel.evaluation.repository.review;


import com.kernel.evaluation.common.enums.ReviewAuthorType;
import com.kernel.evaluation.domain.entity.QReview;
import com.kernel.evaluation.domain.info.CustomerReviewInfo;
import com.kernel.global.domain.entity.QUser;
import com.kernel.reservation.common.enums.MatchStatus;
import com.kernel.reservation.domain.entity.QReservation;
import com.kernel.reservation.domain.entity.QReservationMatch;
import com.kernel.reservation.domain.entity.QReservationSchedule;
import com.kernel.reservation.domain.entity.QServiceCategory;
import com.kernel.reservation.domain.enumerate.ReservationStatus;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
public class CustomCustomerReviewRepositoryImpl implements CustomCustomerReviewRepository {

    private final JPAQueryFactory queryFactory;
    private final QUser user = QUser.user;
    private final QReview review = QReview.review;
    private final QReservation reservation = QReservation.reservation;
    private final QReservationMatch reservationMatch = QReservationMatch.reservationMatch;
    private final QReservationSchedule reservationSchedule = QReservationSchedule.reservationSchedule;
    private final QServiceCategory serviceCategory = QServiceCategory.serviceCategory;

    /**
     * 수요자 리뷰 목록 조회
     * @param userId 로그인 유저
     * @param pageable 페이징
     * @return reviewRspDTO
     */
    @Override
    public Page<CustomerReviewInfo> getCustomerReviews(Long userId, Pageable pageable) {

        // 리뷰 내역 조회
        List<Tuple> result = queryFactory
                .select(
                        review.reviewId,
                        review.rating,
                        review.content,
                        review.createdAt,
                        reservation.reservationId,
                        reservationSchedule.requestDate,
                        reservationSchedule.startTime,
                        reservationSchedule.turnaround,
                        serviceCategory.serviceName,
                        reservationMatch.manager.userId,
                        reservationMatch.manager.userName
                )
                .from(reservation)
                .leftJoin(review).on(
                        review.reservation.eq(reservation)
                                .and(review.authorId.eq(userId))
                                .and(review.reviewAuthorType.eq(ReviewAuthorType.CUSTOMER))
                )
                .leftJoin(reservationSchedule).on(
                        reservationSchedule.reservationId.eq(reservation.reservationId)
                )
                .leftJoin(reservation.serviceCategory, serviceCategory)
                .leftJoin(reservationMatch).on(
                        reservationMatch.reservation.reservationId.eq(reservation.reservationId)
                                .and(reservationMatch.manager.userId.eq(review.targetId))
                                .and(reservationMatch.status.eq(MatchStatus.MATCHED))
                )
                .innerJoin(reservation.user, user)
                .where(
                        reservation.user.userId.eq(userId),
                        reservation.status.eq(ReservationStatus.COMPLETED)
                )
                .orderBy(
                        reservationSchedule.requestDate.desc(),
                        reservationSchedule.startTime.desc()
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 리뷰 존재 여부 검사
        if(result.isEmpty()) {
            return Page.empty(pageable);
        }

        // tuple -> info 변환
        List<CustomerReviewInfo> content = result.stream()
                .map(tuple -> CustomerReviewInfo.builder()
                        .reviewId(tuple.get(review.reviewId))
                        .rating(tuple.get(review.rating))
                        .content(tuple.get(review.content))
                        .createdAt(tuple.get(review.createdAt))
                        .reservationId(tuple.get(reservation.reservationId))
                        .requestDate(tuple.get(reservationSchedule.requestDate))
                        .startTime(tuple.get(reservationSchedule.startTime))
                        .turnaround(tuple.get(reservationSchedule.turnaround))
                        .serviceCategoryName(tuple.get(serviceCategory.serviceName))
                        .managerId(tuple.get(reservationMatch.manager.userId))
                        .managerName(tuple.get(reservationMatch.manager.userName))
                        .build()
                ).toList();

        // 전체 카운트 조회
        long total = Optional.ofNullable(
                queryFactory
                        .select(review.reviewId.count())
                        .from(reservation)
                        .leftJoin(review).on(
                                review.reservation.eq(reservation)
                                        .and(review.authorId.eq(userId))
                                        .and(review.reviewAuthorType.eq(ReviewAuthorType.CUSTOMER))
                        )
                        .where(
                                reservation.user.userId.eq(userId),
                                reservation.status.eq(ReservationStatus.COMPLETED)
                        )
                        .fetchOne()
        ).orElse(0L);

        return new PageImpl<>(content, pageable, total);

    }

    /**
     * 수요자 리뷰 조회 by 예약ID
     * @param userId 로그인 유저
     * @param reservationId 예약ID
     * @return reviewRspDTO
     */
    @Override
    public CustomerReviewInfo getCustomerReviewsByReservationId(Long userId, Long reservationId) {

        Tuple result = queryFactory
                .select(
                        review.reviewId,
                        review.rating,
                        review.content,
                        review.createdAt,
                        reservation.reservationId,
                        reservationSchedule.requestDate,
                        reservationSchedule.startTime,
                        reservationSchedule.turnaround,
                        serviceCategory.serviceName,
                        reservationMatch.manager.userId,
                        reservationMatch.manager.userName
                )
                .from(reservation)
                .leftJoin(review).on(
                        review.reservation.eq(reservation)
                                .and(review.authorId.eq(userId))
                                .and(review.reviewAuthorType.eq(ReviewAuthorType.CUSTOMER))
                )
                .leftJoin(reservationSchedule).on(
                        reservationSchedule.reservationId.eq(reservationId)
                )
                .leftJoin(reservation.serviceCategory, serviceCategory)
                .innerJoin(reservationMatch)
                .where(
                        reservation.reservationId.eq(reservationId),
                        reservation.user.userId.eq(userId),
                        reservation.status.eq(ReservationStatus.COMPLETED)
                )
                .fetchOne();

        if (result == null || result.get(reservation.reservationId) == null) {
            throw new NoSuchElementException("예약 정보를 찾을 수 없습니다.");
        }

        return CustomerReviewInfo.builder()
                .reviewId(result.get(review.reviewId))
                .rating(result.get(review.rating))
                .content(result.get(review.content))
                .createdAt(result.get(review.createdAt))
                .reservationId(result.get(reservation.reservationId))
                .requestDate(result.get(reservationSchedule.requestDate))
                .startTime(result.get(reservationSchedule.startTime))
                .turnaround(result.get(reservationSchedule.turnaround))
                .serviceCategoryName(result.get(serviceCategory.serviceName))
                .managerId(result.get(reservationMatch.manager.userId))
                .managerName(result.get(reservationMatch.manager.userName))
                .build();
    }
}
