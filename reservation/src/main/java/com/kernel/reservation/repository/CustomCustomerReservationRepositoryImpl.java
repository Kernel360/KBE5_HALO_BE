package com.kernel.reservation.repository;

import com.kernel.evaluation.common.enums.ReviewAuthorType;
import com.kernel.evaluation.domain.entity.QReview;
import com.kernel.global.common.enums.UserRole;
import com.kernel.global.domain.entity.QUser;
import com.kernel.member.domain.entity.QManager;
import com.kernel.member.domain.entity.QManagerStatistic;
import com.kernel.payment.domain.QPayment;
import com.kernel.reservation.domain.entity.*;
import com.kernel.reservation.service.info.*;
import com.kernel.reservation.service.request.CustomerReservationSearchCondDTO;
import com.kernel.sharedDomain.common.enums.ReservationStatus;
import com.kernel.sharedDomain.domain.entity.QReservation;
import com.kernel.sharedDomain.domain.entity.QServiceCategory;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
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
    private final QPayment payment = QPayment.payment;

    /**
     * 수요자 예약 내역 조회
     * @param userId 수요자ID
     * @param searchCondDTO 검색조건
     * @param pageable 페이징 정보
     * @return 검색 조건에 따른 예약 목록(페이징 포함)
     */
    @Override
    public Page<CustomerReservationSummaryInfo> getCustomerReservations(Long userId, CustomerReservationSearchCondDTO searchCondDTO, Pageable pageable) {

        // 수요자 예약 내역 조회
        List<CustomerReservationSummaryInfo> content = queryFactory
                .select(Projections.fields(CustomerReservationSummaryInfo.class,
                        reservation.reservationId,
                        reservation.serviceCategory.serviceId.as("serviceCategoryId"),
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
                .where(
                        reservation.user.userId.eq(userId),
                        RequestDateGoe(searchCondDTO.getFromRequestDate()),
                        RequestDateLoe(searchCondDTO.getToRequestDate()),
                        reservationStatus(searchCondDTO.getReservationStatus()),
                        managerNameContains(searchCondDTO.getManagerNameKeyword())
                )
                .orderBy(
                        schedule.requestDate.desc(),
                        schedule.startTime.desc(),
                        reservation.createdAt.desc()
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 전체 개수 조회
        long total = Optional.ofNullable(
                queryFactory
                        .select(reservation.count())
                        .from(reservation)
                        .leftJoin(reservation.serviceCategory, serviceCategory)
                        .leftJoin(schedule).on(schedule.reservation.eq(reservation))
                        .leftJoin(match).on(match.reservation.eq(reservation))
                        .leftJoin(user).on(match.manager.eq(user))
                        .leftJoin(review).on(
                                review.reservation.eq(reservation),
                                review.authorId.eq(userId),
                                review.reviewAuthorType.eq(ReviewAuthorType.CUSTOMER)
                        )
                        .where(
                                reservation.user.userId.eq(userId),
                                RequestDateGoe(searchCondDTO.getFromRequestDate()),
                                RequestDateLoe(searchCondDTO.getToRequestDate()),
                                reservationStatus(searchCondDTO.getReservationStatus()),
                                managerNameContains(searchCondDTO.getManagerNameKeyword())
                        )
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
     * 예약 확정 내역 조회
     * @param reservationId 예약 ID
     * @return 조회된 예약 정보
     */
    @Override
    public CustomerReservationConfirmInfo getConfirmReservation(Long reservationId) {

        CustomerReservationConfirmInfo confirmInfo = queryFactory
                .select(Projections.fields(CustomerReservationConfirmInfo.class,
                        reservation.reservationId,
                        reservation.serviceCategory.serviceId.as("serviceCategoryId"),
                        reservation.serviceCategory.serviceName,
                        schedule.requestDate,
                        schedule.startTime,
                        schedule.turnaround,
                        location.roadAddress,
                        location.detailAddress,
                        match.manager.userName.as("managerName"),
                    //    manager.profileImageFileId.filePathsJson.as("profileImagePath"),
                        payment.paymentMethod,
                        payment.amount
                ))
                .from(reservation)
                .leftJoin(location).on(location.reservation.eq(reservation))
                .leftJoin(schedule).on(schedule.reservation.eq(reservation))
                .leftJoin(match).on(match.reservation.eq(reservation))
                .leftJoin(manager).on(match.manager.eq(manager.user))
                .leftJoin(payment).on(payment.reservation.eq(reservation))
                .where(reservation.reservationId.eq(reservationId))
                .fetchOne();

        if (confirmInfo == null) {
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

        if(extraServiceList != null)
            confirmInfo.initExtraService(extraServiceList);

        return confirmInfo;
    }

    // 청소 예약 날짜 >= 시작일
    private BooleanExpression RequestDateGoe(LocalDate from) {
        return from != null
                ? schedule.requestDate.goe(from)
                : null;
    }

    // 청소 예약 날짜 <= 종료일
    private BooleanExpression RequestDateLoe(LocalDate to) {
        return to != null
                ? schedule.requestDate.loe(to)
                : null;
    }

    // 예약 상태
    private BooleanExpression reservationStatus(List<ReservationStatus> statuses) {
        return (statuses != null && !statuses.isEmpty())
                ? reservation.status.in(statuses)
                : reservation.status.ne(ReservationStatus.PRE_CANCELED);
    }

    // 매니저 검색어 포함 -> 검색된 이름 중에 role이 MANAGER인 경우만 조회 필요
    private BooleanExpression managerNameContains(String keyword) {
        return (keyword != null && !keyword.isBlank())
                ? match.manager.userName.contains(keyword)
                .and(reservation.user.role.eq(UserRole.MANAGER))
                : null;
    }
}
