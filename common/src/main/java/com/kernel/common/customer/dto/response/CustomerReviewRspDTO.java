package com.kernel.common.customer.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CustomerReviewRspDTO {

    // 예약ID
    private Long reservationId;

    // 매니저ID
    private Long managerId;

    // 매니저 이름
    private String managerName;

    // 서비스 요청 날짜
    private LocalDate requestDate;

    // 리뷰 별점
    private Integer rating;

    // 리뷰 내용
    private String content;

    // 리뷰 작성 일자
    private LocalDateTime createdAt;

}
