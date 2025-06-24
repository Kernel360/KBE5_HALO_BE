package com.kernel.evaluation.service.review.dto.response;

import com.kernel.evaluation.domain.info.CustomerReviewInfo;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class CustomerReviewRspDTO {

    // 리뷰ID
    private Long reviewId;

    // 예약ID
    private Long reservationId;

    // 서비스 요청 날짜
    private LocalDate requestDate;

    // 서비스 요청 시간
    private LocalTime startTime;

    // 서비스 소요 시간
    private Integer turnaround;

    // 서비스 카테고리명
    private String serviceCategoryName;

    // 리뷰 별점
    private Integer rating;

    // 리뷰 내용
    private String content;

    // 리뷰 작성 일자
    private LocalDateTime createdAt;


    // CustomerReviewInfo -> CustomerReviewRspDTO
    public static CustomerReviewRspDTO fromInfo(CustomerReviewInfo info) {
        return CustomerReviewRspDTO.builder()
                .reviewId(info.getReviewId())
                .reservationId(info.getReservationId())
                .requestDate(info.getRequestDate())
                .startTime(info.getStartTime())
                .turnaround(info.getTurnaround())
                .serviceCategoryName(info.getServiceCategoryName())
                .rating(info.getRating())
                .content(info.getContent())
                .createdAt(info.getCreatedAt())
                .build();
    }

}
