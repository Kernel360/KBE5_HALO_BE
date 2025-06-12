package com.kernel.common.customer.repository;

import com.kernel.common.customer.dto.response.CustomerReviewRspDTO;
import com.kernel.common.customer.entity.QCustomer;
import com.kernel.common.global.enums.AuthorType;
import com.kernel.common.manager.entity.QManager;
import com.kernel.common.reservation.entity.QReservation;
import com.kernel.common.reservation.entity.QReview;
import com.kernel.common.reservation.entity.QServiceCategory;
import com.kernel.common.reservation.enums.ReservationStatus;
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
    private final QReview review = QReview.review;
    private final QManager manager = QManager.manager;
    private final QReservation reservation = QReservation.reservation;
    private final QServiceCategory serviceCategory = QServiceCategory.serviceCategory;
    private final QCustomer customer = QCustomer.customer;

    /**
     * 수요자 리뷰 목록 조회
     * @param customerId 수요자ID
     * @param pageable 페이징
     * @return reviewRspDTO
     */
    @Override
    public Page<CustomerReviewRspDTO> getCustomerReviews(Long customerId, Pageable pageable) {

        // 리뷰 내역 조회
        List<Tuple> result = queryFactory
                .select(
                        review.reviewId,
                        review.rating,
                        review.content,
                        review.createdAt,
                        reservation.reservationId,
                        reservation.requestDate,
                        reservation.startTime,
                        reservation.turnaround,
                        serviceCategory.serviceName,
                        manager.managerId,
                        manager.userName
                )
                .from(reservation)
                .leftJoin(review).on(
                        review.reservation.eq(reservation)
                                .and(review.authorId.eq(customerId))
                                .and(review.authorType.eq(AuthorType.CUSTOMER))
                )
                .leftJoin(reservation.manager, manager)
                .leftJoin(reservation.serviceCategory, serviceCategory)
                .innerJoin(reservation.customer, customer)
                .where(
                        reservation.customer.customerId.eq(customerId),
                        reservation.status.eq(ReservationStatus.COMPLETED)
                )
                .orderBy(
                        reservation.requestDate.desc(),
                        reservation.startTime.desc()
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 리뷰 존재 여부 검사
        if(result.isEmpty()) {
            return Page.empty(pageable);
        }

        // tuple -> DTO 변환
        List<CustomerReviewRspDTO> content = result.stream()
                .map(tuple -> CustomerReviewRspDTO.builder()
                        .reviewId(tuple.get(review.reviewId))
                        .rating(tuple.get(review.rating))
                        .content(tuple.get(review.content))
                        .createdAt(tuple.get(review.createdAt))
                        .reservationId(tuple.get(reservation.reservationId))
                        .requestDate(tuple.get(reservation.requestDate))
                        .startTime(tuple.get(reservation.startTime))
                        .turnaround(tuple.get(reservation.turnaround))
                        .serviceCategoryName(tuple.get(serviceCategory.serviceName))
                        .managerId(tuple.get(manager.managerId))
                        .managerName(tuple.get(manager.userName))
                        .build()
                ).toList();

        // 전체 카운트 조회
        long total = Optional.ofNullable(
                queryFactory
                        .select(review.reviewId.count())
                        .from(reservation)
                        .leftJoin(review).on(
                                review.reservation.eq(reservation)
                                        .and(review.authorId.eq(customerId))
                                        .and(review.authorType.eq(AuthorType.CUSTOMER))
                        )
                        .where(
                                reservation.customer.customerId.eq(customerId),
                                reservation.status.eq(ReservationStatus.COMPLETED)
                        )
                        .fetchOne()
        ).orElse(0L);

        return new PageImpl<>(content, pageable, total);

    }

    /**
     * 수요자 리뷰 조회 by 예약ID
     * @param customerId 수요자ID
     * @param reservationId 예약ID
     * @return reviewRspDTO
     */
    @Override
    public CustomerReviewRspDTO getCustomerReviewsByReservationId(Long customerId, Long reservationId) {

        Tuple result = queryFactory
                .select(
                        review.reviewId,
                        review.rating,
                        review.content,
                        review.createdAt,
                        reservation.reservationId,
                        reservation.requestDate,
                        reservation.startTime,
                        reservation.turnaround,
                        serviceCategory.serviceName,
                        manager.managerId,
                        manager.userName
                )
                .from(reservation)
                .leftJoin(review).on(
                        review.reservation.eq(reservation)
                                .and(review.authorId.eq(customerId))
                                .and(review.authorType.eq(AuthorType.CUSTOMER))
                )
                .leftJoin(reservation.manager, manager)
                .leftJoin(reservation.serviceCategory, serviceCategory)
                .innerJoin(reservation.customer, customer)
                .where(
                        reservation.reservationId.eq(reservationId),
                        reservation.customer.customerId.eq(customerId),
                        reservation.status.eq(ReservationStatus.COMPLETED)
                )
                .fetchOne();

        if (result == null || result.get(reservation.reservationId) == null) {
            throw new NoSuchElementException("예약 정보를 찾을 수 없습니다.");
        }

        return CustomerReviewRspDTO.builder()
                .reviewId(result.get(review.reviewId))
                .rating(result.get(review.rating))
                .content(result.get(review.content))
                .createdAt(result.get(review.createdAt))
                .reservationId(result.get(reservation.reservationId))
                .requestDate(result.get(reservation.requestDate))
                .startTime(result.get(reservation.startTime))
                .turnaround(result.get(reservation.turnaround))
                .serviceCategoryName(result.get(serviceCategory.serviceName))
                .managerId(result.get(manager.managerId))
                .managerName(result.get(manager.userName))
                .build();
    }

}
