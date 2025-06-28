package com.kernel.reservation.service.response.common;

import com.kernel.reservation.service.info.ReviewInfo;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewRspDTO {

    // 리뷰ID
    private Long reviewId;

    // 리뷰 내용
    private String content;

    // 리뷰 별점
    private Integer rating;

    // 리뷰 작성일자
    private LocalDateTime reviewDate;

    public static ReviewRspDTO fromInfo(ReviewInfo info){
        return ReviewRspDTO.builder()
                .reviewId(info.getReviewId())
                .content(info.getContent())
                .rating(info.getRating())
                .reviewDate(info.getCreatedAt())
                .build();
    }
}
