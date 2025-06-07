package com.kernel.common.matching.dto;

import com.kernel.common.global.enums.FeedbackType;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Getter
public class ManagerMatchingRspDTO {

    // 매니저ID
    private Long managerId;

    // 매니저이름
    private String managerName;

    // 매니저 평균 별점
    private BigDecimal averageRating;

    // 매니저 리뷰 수
    private Integer reviewCount;

    // 매니저 프로필 사진
    private Long profileImageId;

    // 매니저 한줄 소개
    private String bio;

    // 매니저 좋아요/아쉬워요 여부
    private FeedbackType feedbackType;

    // 최근 예약 일자
    private LocalDate recentReservationDate;



}
