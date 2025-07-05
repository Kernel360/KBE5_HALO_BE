package com.kernel.evaluation.repository.review;


import com.kernel.evaluation.common.enums.ReviewAuthorType;
import com.kernel.evaluation.domain.entity.QReview;
import com.kernel.evaluation.domain.info.CustomerReviewInfo;
import com.kernel.global.domain.entity.QUser;
import com.kernel.sharedDomain.common.enums.ReservationStatus;
import com.kernel.sharedDomain.domain.entity.QReservation;
import com.kernel.sharedDomain.domain.entity.QServiceCategory;
import com.querydsl.core.types.Projections;
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
        List<CustomerReviewInfo> reviews = queryFactory
                .select(Projections.fields(CustomerReviewInfo.class,
                        review.reviewId,
                        review.rating,
                        review.content,
                        review.createdAt,
                        reservation.reservationId,
                        serviceCategory.serviceName
                ))
                .from(reservation)
                .leftJoin(review).on(
                        review.reservation.eq(reservation)
                                .and(review.authorId.eq(userId))
                                .and(review.reviewAuthorType.eq(ReviewAuthorType.CUSTOMER))
                )
                .leftJoin(reservation.serviceCategory, serviceCategory)
                .innerJoin(reservation.user, user)
                .where(
                        reservation.user.userId.eq(userId),
                        reservation.status.eq(ReservationStatus.COMPLETED)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 리뷰 존재 여부 검사
        if(reviews.isEmpty()) {
            return Page.empty(pageable);
        }

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

        return new PageImpl<>(reviews, pageable, total);

    }

    /**
     * 수요자 리뷰 조회 by 예약ID
     * @param userId 로그인 유저
     * @param reservationId 예약ID
     * @return reviewRspDTO
     */
    @Override
    public CustomerReviewInfo getCustomerReviewsByReservationId(Long userId, Long reservationId) {

        CustomerReviewInfo result = queryFactory
                .select(Projections.fields(CustomerReviewInfo.class,
                        reservation.reservationId,
                        serviceCategory.serviceName,
                        review.reviewId,
                        review.rating,
                        review.content,
                        review.createdAt
                ))
                .from(reservation)
                .leftJoin(review).on(
                        review.reservation.eq(reservation)
                                .and(review.authorId.eq(userId))
                                .and(review.reviewAuthorType.eq(ReviewAuthorType.CUSTOMER))
                )
                .leftJoin(reservation.serviceCategory, serviceCategory)
                .where(
                        reservation.reservationId.eq(reservationId),
                        reservation.user.userId.eq(userId),
                        reservation.status.eq(ReservationStatus.COMPLETED)
                )
                .fetchOne();

        if (result == null || result.getReservationId() == null) {
            throw new NoSuchElementException("예약 정보를 찾을 수 없습니다.");
        }

        return result;
    }
}
