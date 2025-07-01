package com.kernel.reservation.repository;

import com.kernel.evaluation.common.enums.ReviewAuthorType;
import com.kernel.evaluation.domain.entity.QReview;
import com.kernel.global.domain.entity.QUser;
import com.kernel.member.domain.entity.QManager;
import com.kernel.member.domain.entity.QManagerStatistic;
import com.kernel.reservation.domain.entity.*;
import com.kernel.reservation.service.info.*;
import com.kernel.sharedDomain.common.enums.ReservationStatus;
import com.kernel.sharedDomain.domain.entity.QReservation;
import com.kernel.sharedDomain.domain.entity.QServiceCategory;
import com.kernel.sharedDomain.domain.entity.Reservation;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
public class CustomCustomerReservationRepositoryImpl implements CustomCustomerReservationRepository {

    private final JPAQueryFactory queryFactory;
    private final QUser user = QUser.user;
    private final QReview review = QReview.review;
    private final QManager manager = QManager.manager;
    private final QExtraService extraService = QExtraService.extraService;
    private final QManagerStatistic managerStatistic = QManagerStatistic.managerStatistic;
    private final QServiceCategory serviceCategory = QServiceCategory.serviceCategory;
    private final QReservation reservation = QReservation.reservation;
    private final QReservationMatch match = QReservationMatch.reservationMatch;
    private final QReservationLocation location = QReservationLocation.reservationLocation;
    private final QReservationSchedule schedule = QReservationSchedule.reservationSchedule;
    private final QReservationCancel reservationCancel = QReservationCancel.reservationCancel;

    /**
     * 수요자 예약 내역 조회
     * @param userId 수요자ID
     * @param status 예약 상태
     * @param pageable 페이징 정보
     * @return 검색 조건에 따른 예약 목록(페이징 포함)
     */
    @Override
    public Page<CustomerReservationSummaryInfo> getCustomerReservationsByStatus(Long userId, ReservationStatus status, Pageable pageable) {

        BooleanExpression byCustomerIdAndStatus = customerIdAndStatus(userId, status);

        // 수요자 예약 내역 조회
        List<CustomerReservationSummaryInfo> content = queryFactory
                .select(Projections.fields(CustomerReservationSummaryInfo.class,
                        reservation.reservationId,
                        reservation.status,
                        reservation.price,
                        location.roadAddress,
                        location.detailAddress,
                        match.manager.userName,
                        serviceCategory.serviceName,
                        schedule.requestDate,
                        schedule.startTime,
                        schedule.turnaround,
                        review.reviewId
                ))
                .from(reservation)
                .leftJoin(reservation.serviceCategory, serviceCategory)
                .leftJoin(location).on(location.reservation.eq(reservation))
                .leftJoin(schedule).on(schedule.reservation.eq(reservation))
                .leftJoin(match).on(match.reservation.eq(reservation))
                .leftJoin(user).on(match.manager.eq(user))
                .leftJoin(review).on(
                        review.reservation.eq(reservation),
                        review.authorId.eq(userId),
                        review.reviewAuthorType.eq(ReviewAuthorType.CUSTOMER)
                )
                .where(byCustomerIdAndStatus)
                .orderBy(
                        schedule.requestDate.desc(),
                        schedule.startTime.desc()
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

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
     * @param userId 수요자 ID
     * @param reservationId 예약 ID
     * @return 조회된 예약 상세 정보
     */
    @Override
    public CustomerReservationDetailInfo getCustomerReservationDetail(Long userId, Long reservationId) {

        // 1. 예약 내역 상세 조회
        CustomerReservationDetailInfo detailInfo = queryFactory
                .select(Projections.fields(CustomerReservationDetailInfo.class,
                        reservation.reservationId,            // 예약 ID
                        reservation.phone,                    // 핸드폰 번호
                        reservation.status,                   // 예약상태
                        reservation.price,                    // 총 가격
                        reservation.memo,                     // 메모
                        location.roadAddress,                 // 도로명 주소
                        location.detailAddress,               // 상세 주소
                        schedule.requestDate,                 // 요청 날짜
                        schedule.startTime,                   // 시작 시간
                        schedule.turnaround,                  // 총 소요 시간
                        reservation.serviceCategory.serviceId, // 서비스 ID
                        serviceCategory.serviceName,           // 서비스 이름(대분류)
                        serviceCategory.serviceTime,           // 서비스 시간
                        match.manager.userName.as("managerName"),                // 매니저 이름
                        manager.bio,                          // 매니저 한줄 소개
                        managerStatistic.averageRating,        // 매니저 평점 평균
                        managerStatistic.reviewCount,           // 리뷰 수
                        managerStatistic.reservationCount       // 예약 건수
                ))
                .from(reservation)
                .leftJoin(location).on(location.reservation.eq(reservation))
                .leftJoin(schedule).on(schedule.reservation.eq(reservation))
                .leftJoin(match).on(match.reservation.eq(reservation)
                        .and(match.matchId.eq(
                                JPAExpressions.select(match.matchId.max())
                                        .from(match)
                                        .where(match.reservation.eq(reservation))
                        ))
                )
                .leftJoin(manager).on(match.manager.eq(manager.user))
                .leftJoin(managerStatistic).on(managerStatistic.user.eq(manager.user))
                .leftJoin(serviceCategory).on(reservation.serviceCategory.eq(serviceCategory))
                .where(
                        reservation.reservationId.eq(reservationId),
                        reservation.user.userId.eq(userId)
                )
                .fetchOne();

        // 2. null 체크
        if(detailInfo == null) {
            throw new NoSuchElementException("해당 예약을 찾을 수 없습니다.");
        }

        // 3. 추가 서비스 조회 및 설정
        List<ExtraServiceInfo> extraServiceList = queryFactory
                .select(Projections.fields(ExtraServiceInfo.class,
                        extraService.serviceCategory.serviceId,
                        serviceCategory.serviceName,        // 카테고리 이름
                        extraService.price,               // 카테고리 가격
                        extraService.serviceTime        // 카테고리 소요 시간
                ))
                .from(extraService)
                .leftJoin(extraService.serviceCategory, serviceCategory)
                .where(extraService.reservation.reservationId.eq(reservationId))
                .fetch();

        if(extraServiceList != null) {
            detailInfo.initExtraServiceList(extraServiceList);
        }

        // 4. 예약 취소인 경우 예약 취소 정보 조회 및 설정
        if(ReservationStatus.CANCELED.equals(detailInfo.getStatus()))
        {
            ReservationCancelInfo cancelInfo = queryFactory
                    .select(Projections.fields(ReservationCancelInfo.class,
                            reservationCancel.cancelReason,
                            reservationCancel.createdAt
                    ))
                    .from(reservationCancel)
                    .leftJoin(user).on(user.userId.eq(reservationCancel.canceledById))
                    .where(reservationCancel.reservation.reservationId.eq(reservationId))
                    .fetchOne();

            detailInfo.initCancelInfo(cancelInfo);
        }

        // 5. 수요자 리뷰 조회 및 설정
        ReviewInfo foundReview = queryFactory
                .select(Projections.fields(ReviewInfo.class,
                        review.reviewId,
                        review.rating,
                        review.content,
                        review.createdAt
                ))
                .from(review)
                .where(
                        review.reservation.reservationId.eq(reservationId),
                        review.authorId.eq(userId),
                        review.reviewAuthorType.eq(ReviewAuthorType.CUSTOMER)
                )
                .fetchOne();

        if(foundReview != null) {
            detailInfo.initReview(foundReview);
        }

        return detailInfo;
    }

    /**
     * 예약내역 검색 조건 (수요자 ID + 예약상태)
     * @param userId 수요자 ID
     * @param status 예약 상태
     * @return BooleanExpression 조건
     */
    private BooleanExpression customerIdAndStatus(Long userId, ReservationStatus status) {
        BooleanExpression condition = reservation.user.userId.eq(userId);

        if (status != null) {
            condition = condition.and(reservation.status.eq(status));
        }

        condition = condition.and(reservation.status.ne(ReservationStatus.PRE_CANCELED));

        return condition;
    }
}
