package com.kernel.reservation.service.response;

import com.kernel.reservation.service.response.common.StatisticRspDTO;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class MatchedManagersRspDTO {

    /* User */
    // 매니저ID
    private Long managerId;

    // 매니저이름
    private String managerName;

    /* ManagerStatistic */
    private StatisticRspDTO managerStatistic;

    /* Manager */
    // 매니저 프로필 사진
    private Long profileImageId;

    // 매니저 한줄 소개
    private String bio;

    /* ReservationSchedule */
    // 최근 예약 일자
    private LocalDate recentReservationDate;



}
