package com.kernel.evaluation.domain.info;

import com.kernel.evaluation.common.enums.FeedbackType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerFeedbackInfo {

    // 피드백 ID
    private Long feedbackId;

    // 피드백 타입
    private FeedbackType feedbackType;

    // 매니저 ID
    private Long managerId;

    // 매니저 이름
    private String managerName;

    // 매니저 평균 별점
    private BigDecimal averageRating;

    // 매니저 리뷰 수
    private Integer reviewCount;

    // 최근 예약 날짜
    private LocalDate requestDate;
}
