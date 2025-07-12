package com.kernel.reservation.service.response.common;

import com.kernel.reservation.service.info.MatchedManagersInfo;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MatchedManagersRspDTO {

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

    // 매니저 프로필 사진 url
    private String profileImageUrl;

    // 매니저 특기
    private String specialty;

    // 매니저 한줄 소개
    private String bio;

    /* ReservationSchedule */
    // 최근 예약 일자
    private LocalDate recentReservationDate;

    public static List<MatchedManagersRspDTO> fromInfoList(List<MatchedManagersInfo> infoList){
        return infoList.stream()
                .map(MatchedManagersRspDTO::fromInfo)
                .collect(Collectors.toList());
    }

    public static MatchedManagersRspDTO fromInfo(MatchedManagersInfo info){
        return MatchedManagersRspDTO.builder()
                .managerId(info.getManagerId())
                .managerName(info.getManagerName())
                .reviewCount(info.getReviewCount())
                .reservationCount(info.getReservationCount())
                .averageRating(info.getAverageRating())
                .profileImageUrl(info.getProfileImageId())
                .bio(info.getBio())
                .recentReservationDate(info.getRecentReservationDate())
                .build();
    }


}
