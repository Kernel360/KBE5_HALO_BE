package com.kernel.reservation.service.info;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Getter
public class MatchedManagersInfo {

    /* User */
    // 매니저ID
    private Long managerId;

    // 매니저이름
    private String managerName;

    /* ManagerStatistic*/
    // 리뷰 수
    private Integer reviewCount;

    // 예약 수
    private Integer reservationCount;

    // 별점 평균
    private BigDecimal averageRating;

    /* Manager */
    // 매니저 프로필 사진
    private String profileImageId;

    // 매니저 한줄 소개
    private String bio;

    /* ReservationSchedule */
    // 최근 예약 일자
    private LocalDate recentReservationDate;



}
