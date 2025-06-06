package com.kernel.common.customer.repository;

import com.kernel.common.customer.dto.response.CustomerReviewRspDTO;
import com.kernel.common.global.enums.AuthorType;
import com.kernel.common.manager.entity.QManager;
import com.kernel.common.reservation.entity.QReservation;
import com.kernel.common.reservation.entity.QReview;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CustomCustomerReviewRepositoryImpl implements CustomCustomerReviewRepository {

    private final JPAQueryFactory queryFactory;
    private final QReview review = QReview.review;
    private final QManager manager = QManager.manager;
    private final QReservation reservation = QReservation.reservation;

    /**
     * 수요자 리뷰 목록 조회
     * @param customerId 수요자ID
     * @param pageable 페이징
     * @return reviewRspDTO
     */
    @Override
    public Page<CustomerReviewRspDTO> getCustomerReviews(Long customerId, Pageable pageable) {

        // 리뷰 내역 조회
        List<Tuple> reviews = queryFactory
                .select(
                        review.reservation.reservationId,
                        review.rating,
                        review.content,
                        reservation.requestDate,
                        manager.managerId,
                        manager.userName
                )
                .from(review)
                .leftJoin(review.reservation, reservation)
                .leftJoin(reservation.manager, manager)
                .where(
                        review.authorId.eq(customerId),
                        review.authorType.eq(AuthorType.CUSTOMER)
                )
                .fetch();

        // 리뷰 존재 여부 검사
        if(reviews.isEmpty()) {
            return Page.empty(pageable);
        }

        // tuple -> DTO 변환
        List<CustomerReviewRspDTO> content = reviews.stream()
                .map(tuple -> CustomerReviewRspDTO.builder()
                        .reservationId(tuple.get(review.reservation.reservationId))
                        .managerId(tuple.get(manager.managerId))
                        .managerName(tuple.get(manager.userName))
                        .requestDate(tuple.get(reservation.requestDate))
                        .rating(tuple.get(review.rating))
                        .content(tuple.get(review.content))
                        .build()
                ).toList();

        // 전체 카운트 조회
        long total = Optional.ofNullable(
                queryFactory
                        .select(review.count())
                        .from(review)
                        .where(
                                review.authorId.eq(customerId),
                                review.authorType.eq(AuthorType.CUSTOMER)
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

        // 리뷰 내역 조회
        Tuple result = queryFactory
                    .select(
                            review.reservation.reservationId,
                            review.rating,
                            review.content,
                            reservation.requestDate,
                            manager.managerId,
                            manager.userName
                    )
                    .from(review)
                    .leftJoin(review.reservation, reservation)
                    .leftJoin(reservation.manager, manager)
                    .where(
                            review.authorId.eq(customerId),
                            review.authorType.eq(AuthorType.CUSTOMER),
                            review.reservation.reservationId.eq(reservationId)
                    )
                    .fetchOne();

        if(result == null) {
            return null;
        }

        return CustomerReviewRspDTO.builder()
                .reservationId(result.get(review.reservation.reservationId))
                .managerId(result.get(manager.managerId))
                .managerName(result.get(manager.userName))
                .requestDate(result.get(reservation.requestDate))
                .rating(result.get(review.rating))
                .content(result.get(review.content))
                .build();
    }

}
