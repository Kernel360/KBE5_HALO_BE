package com.kernel.sharedDomain.service.response;

import com.kernel.sharedDomain.common.enums.MatchStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ScheduleAndMatchInfo {

    // 예약 ID
    private Long reservationId;

    // 서비스 요청 날짜
    private LocalDate requestDate;

    // 서비스 시작 시간
    private LocalTime startTime;

    // 소요 시간
    private Integer turnaround;

    // 매니저 ID
    private Long managerId;

    // 매칭 상태
    private MatchStatus status;

    // 확정 날짜
    private LocalDate confirmAt;

    // 매니저 이름
    private String managerName;

    // 매니저 프로필 path
    private String path;

}
