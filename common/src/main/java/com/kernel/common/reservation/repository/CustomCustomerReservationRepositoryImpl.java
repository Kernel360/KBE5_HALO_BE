package com.kernel.common.reservation.repository;

import com.kernel.common.global.enums.AuthorType;
import com.kernel.common.manager.entity.QManager;
import com.kernel.common.reservation.dto.response.CustomerReservationDetailRspDTO;
import com.kernel.common.reservation.dto.response.CustomerReservationRspDTO;
import com.kernel.common.reservation.dto.response.ExtraServiceRspDTO;
import com.kernel.common.reservation.entity.QExtraService;
import com.kernel.common.reservation.entity.QReservation;
import com.kernel.common.reservation.entity.QReview;
import com.kernel.common.reservation.entity.QServiceCategory;
import com.kernel.common.reservation.enums.ReservationStatus;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CustomCustomerReservationRepositoryImpl implements CustomCustomerReservationRepository {

    private final JPAQueryFactory queryFactory;
    private final QReservation reservation = QReservation.reservation;
    private final QManager manager = QManager.manager;
    private final QServiceCategory serviceCategory = QServiceCategory.serviceCategory;
    private final QExtraService extraService = QExtraService.extraService;
    private final QReview review = QReview.review;

    /**
     * 수요자 예약 내역 조회
     * @param status 예약 상태
     * @param customerId 수요자ID
     * @param pageable 페이징 정보
     * @return 검색 조건에 따른 예약 목록(페이징 포함)
     */
    @Override
    public Page<CustomerReservationRspDTO> getCustomerReservationsByStatus(Long customerId, ReservationStatus status, Pageable pageable) {

        BooleanExpression byCustomerIdAndStatus = customerIdAndStatus(customerId, status);

        // 수요자 예약 내역 조회
        List<Tuple> tuples = queryFactory
                .select(
                        reservation.reservationId,      // 예약ID
                        reservation.roadAddress,        // 도로명 주소
                        reservation.detailAddress,      // 상세 주소
                        manager.userName,               // 매니저 이름
                        reservation.status,             // 예약 상태
                        serviceCategory.serviceName,    // 서비스 카테고리 이름
                        reservation.requestDate,        // 서비스 요청 날짜
                        reservation.startTime,          // 서비스 시작 시간
                        reservation.turnaround,         // 서비스 소요 시간
                        reservation.price               // 예약 금액
                )
                .from (reservation)
                .leftJoin(reservation.manager, manager)
                .leftJoin(reservation.serviceCategory, serviceCategory)
                .where(byCustomerIdAndStatus)
                .orderBy(
                        reservation.requestDate.desc(),     // 예약 날짜 최신순
                        reservation.startTime.desc()        // 예약 시간 최신순
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 예약 ID 조회
        List<Long> reservationIds = tuples.stream()
                .map(tuple -> tuple.get(reservation.reservationId))
                .toList();

        // 리뷰 ID 조회
        Map<Long, Long> reviewIdByReservationIds = new HashMap<>();
        if (!reservationIds.isEmpty()) {
            reviewIdByReservationIds = queryFactory
                    .select(
                            review.reservation.reservationId,
                            review.reviewId
                    )
                    .from(review)
                    .where(
                            review.reservation.reservationId.in(reservationIds),
                            review.authorId.eq(customerId),
                            review.authorType.eq(AuthorType.CUSTOMER)
                    )
                    .fetch()
                    .stream()
                    .filter(tuple ->
                            Objects.nonNull(tuple.get(review.reservation.reservationId)) &&
                            Objects.nonNull(tuple.get(review.reviewId))
                    )
                    .collect(Collectors.toMap(
                            tuple -> tuple.get(review.reservation.reservationId),
                            tuple -> tuple.get(review.reviewId)
                    ));
        }

        // Make reservationIdToReviewId effectively final for lambda
        Map<Long, Long> finalReviewIdByReservationIds = reviewIdByReservationIds;

        // Tuple -> DTO 로 변환
        List<CustomerReservationRspDTO> content = tuples.stream()
                .map(tuple -> {
                    Long resId = tuple.get(reservation.reservationId);
                    return CustomerReservationRspDTO.builder()
                            .reservationId(resId)
                            .roadAddress(tuple.get(reservation.roadAddress))
                            .detailAddress(tuple.get(reservation.detailAddress))
                            .managerName(tuple.get(manager.userName))
                            .reservationStatus(tuple.get(reservation.status))
                            .serviceName(tuple.get(serviceCategory.serviceName))
                            .requestDate(tuple.get(reservation.requestDate))
                            .startTime(tuple.get(reservation.startTime))
                            .turnaround(tuple.get(reservation.turnaround))
                            .price(tuple.get(reservation.price))
                            .reviewId(finalReviewIdByReservationIds.get(resId)) // ← 없는 경우 null 반환됨
                            .build();
                })
                .collect(Collectors.toList());

        // 전체 개수 조회
        long total = Optional.ofNullable(
                queryFactory
                        .select(reservation.count())
                        .from(reservation)
                        .where(byCustomerIdAndStatus)
                        .fetchOne()
        ).orElse(0L);

        return  new PageImpl<>(content, pageable, total);
    }

    /**
     * 예약 내역 상세 조회
     * @param reservationId 예약 ID
     * @param customerId 수요자 ID
     * @return 조회된 예약 상세 정보
     */
    @Override
    public CustomerReservationDetailRspDTO getCustomerReservationDetail(Long customerId, Long reservationId) {

        // 예약 내역 상세 조회
        Tuple tuple = queryFactory
                .select(
                        reservation.reservationId,      // 예약 ID
                        reservation.phone,              // 핸드폰 번호
                        reservation.roadAddress,        // 도로명 주소
                        reservation.detailAddress,      // 상세 주소
                        reservation.status,             // 예약상태
                        reservation.requestDate,        // 요청 날짜
                        reservation.startTime,          // 시작 시간
                        reservation.turnaround,         // 총 소요 시간
                        reservation.price,              // 총 가격
                        reservation.cancelReason,       // 취소 사유
                        reservation.cancelDate,         // 취소 일자
                        reservation.memo,               // 메모
                        reservation.serviceCategory.serviceId, // 서비스 ID
                        serviceCategory.serviceName,    // 서비스 이름(대분류)
                        serviceCategory.serviceTime,    // 서비스 시간
                        manager.userName,               // 매니저 이름
                        manager.bio,                    // 매니저 한줄 소개
                        manager.averageRating,          // 매니저 평점 평균
                        manager.reviewCount             // 리뷰 수
                )
                .from(reservation)
                .leftJoin(reservation.manager, manager)
                .leftJoin(reservation.serviceCategory, serviceCategory)
                .where(
                        reservation.reservationId.eq(reservationId),
                        reservation.customer.customerId.eq(customerId)
                )
                .fetchOne();

        // detailRspDTO null 체크
        if(tuple == null) {
            throw new NoSuchElementException("해당 예약을 찾을 수 없습니다.");
        }

        // 추가 서비스 조회
        List<ExtraServiceRspDTO> extraServiceList = queryFactory
                .select(Projections.constructor(ExtraServiceRspDTO.class,
                        serviceCategory.serviceId,
                        serviceCategory.serviceName,        // 카테고리 이름
                        serviceCategory.price,               // 카테고리 가격
                        serviceCategory.serviceTime        // 카테고리 소요 시간
                ))
                .from(extraService)
                .leftJoin(extraService.serviceCategory, serviceCategory)
                .where(extraService.reservation.reservationId.eq(reservationId))
                .fetch();
        // null-safe for extraServiceList
        List<ExtraServiceRspDTO> extraServices = extraServiceList != null ? extraServiceList : Collections.emptyList();

        // 수요자 리뷰 조회
        Tuple foundReview = queryFactory
                .select(
                        review.reviewId,
                        review.rating,
                        review.content,
                        review.createdAt
                )
                .from(review)
                .where(
                        review.reservation.reservationId.eq(reservationId),
                        review.authorId.eq(customerId),
                        review.authorType.eq(AuthorType.CUSTOMER)
                )
                .fetchOne();


        // DTO 생성
        CustomerReservationDetailRspDTO detailRspDTO = new CustomerReservationDetailRspDTO(
                tuple.get(reservation.reservationId),
                tuple.get(reservation.phone),
                tuple.get(reservation.roadAddress),
                tuple.get(reservation.detailAddress),
                tuple.get(reservation.status),
                tuple.get(reservation.requestDate),
                tuple.get(reservation.startTime),
                tuple.get(reservation.turnaround),
                tuple.get(reservation.price),
                tuple.get(reservation.memo),
                tuple.get(reservation.serviceCategory.serviceId),
                tuple.get(serviceCategory.serviceName),
                tuple.get(serviceCategory.serviceTime),
                extraServices,
                tuple.get(manager.userName),
                tuple.get(manager.bio),
                tuple.get(manager.averageRating),
                tuple.get(manager.reviewCount),
                tuple.get(reservation.cancelReason),
                tuple.get(reservation.cancelDate),
                Optional.ofNullable(foundReview).map(m -> m.get(review.reviewId)).orElse(null),
                Optional.ofNullable(foundReview).map(m -> m.get(review.content)).orElse(null),
                Optional.ofNullable(foundReview).map(m -> m.get(review.rating)).orElse(null),
                Optional.ofNullable(foundReview).map(m -> m.get(review.createdAt)).orElse(null)
        );
        return detailRspDTO;
    }

    /**
     * 수요자 예약 조회
     * @param customerId 수요자ID
     * @param reservationId 페이징 정보
     * @return 조회된 예약 내용
     */
    @Override
    public CustomerReservationRspDTO getCustomerReservations(Long customerId, Long reservationId) {

        // 수요자 예약 내역 조회
        Tuple tuple = queryFactory
                .select(
                        reservation.reservationId,      // 예약ID
                        manager.userName,               // 매니저 이름
                        reservation.status,             // 예약 상태
                        serviceCategory.serviceName,    // 서비스 카테고리 이름
                        serviceCategory.serviceId,   // 서비스 카테고리ID
                        reservation.requestDate,        // 서비스 요청 날짜
                        reservation.startTime,          // 서비스 시작 시간
                        reservation.turnaround,         // 서비스 소요 시간
                        reservation.price,               // 예약 금액
                        reservation.roadAddress,
                        reservation.detailAddress
                )
                .from (reservation)
                .leftJoin(reservation.manager, manager)
                .leftJoin(reservation.serviceCategory, serviceCategory)
                .where(
                        reservation.reservationId.eq(reservationId),
                        reservation.customer.customerId.eq(customerId)
                )
                .fetchOne();

        // Tuple -> DTO 로 변환
        return CustomerReservationRspDTO.builder()
                .reservationId(tuple.get(reservation.reservationId))
                .managerName(tuple.get(manager.userName))
                .reservationStatus(tuple.get(reservation.status))
                .serviceName(tuple.get(serviceCategory.serviceName))
                .serviceCategoryId(tuple.get(serviceCategory.serviceId))
                .requestDate(tuple.get(reservation.requestDate))
                .startTime(tuple.get(reservation.startTime))
                .turnaround(tuple.get(reservation.turnaround))
                .price(tuple.get(reservation.price))
                .roadAddress(tuple.get(reservation.roadAddress))
                .detailAddress(tuple.get(reservation.detailAddress))
                .build();
    }

    /**
     * 예약내역 검색 조건 (수요자 ID + 예약상태)
     * @param customerId 수요자 ID
     * @param status 예약 상태
     * @return BooleanExpression 조건
     */
    private BooleanExpression customerIdAndStatus(Long customerId, ReservationStatus status) {
        BooleanExpression condition = reservation.customer.customerId.eq(customerId);

        if (status != null) {
            condition = condition.and(reservation.status.eq(status));
        }

        condition = condition.and(reservation.status.ne(ReservationStatus.PRE_CANCELED));

        return condition;
    }
}
