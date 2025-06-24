package com.kernel.evaluation.service.feedback.dto.response;

import com.kernel.evaluation.common.enums.FeedbackType;
import com.kernel.evaluation.domain.info.CustomerFeedbackInfo;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
public class CustomerFeedbackRspDTO {

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

    public static CustomerFeedbackRspDTO fromInfo(CustomerFeedbackInfo info){
        return CustomerFeedbackRspDTO.builder()
                .feedbackId(info.getFeedbackId())
                .feedbackType(info.getFeedbackType())
                .managerId(info.getManagerId())
                .managerName(info.getManagerName())
                .build();
    }

}
