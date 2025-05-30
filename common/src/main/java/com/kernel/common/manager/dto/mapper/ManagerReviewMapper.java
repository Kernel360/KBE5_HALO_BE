package com.kernel.common.manager.dto.mapper;

import com.kernel.common.global.enums.AuthorType;
import com.kernel.common.manager.dto.request.ManagerReviewReqDTO;
import com.kernel.common.manager.dto.response.ManagerReviewRspDTO;
import com.kernel.common.reservation.entity.Review;
import org.springframework.stereotype.Component;

@Component
public class ManagerReviewMapper {

    // CreateRequestDTO -> Entity
    public Review toEntity(Long authorId, Long reservationId, ManagerReviewReqDTO requestDTO) {
        return Review.builder()
            .reservationId(reservationId)       // 예약ID
            .authorType(AuthorType.MANAGER)     // 작성자유형
            .authorId(authorId)                 // 작성자ID
            .rating(requestDTO.getRating())     // 리뷰 평점
            .content(requestDTO.getContent())   // 리뷰 내용
            .build();
    }

    // Entity -> ResponseDTO
    public ManagerReviewRspDTO toResponseDTO(Review review) {
        return ManagerReviewRspDTO.builder()
            .reviewId(review.getReviewId())             // 리뷰ID
            .reservationId(review.getReservationId())   // 예약ID
            .authorId(review.getAuthorId())             // 작성자ID
            .rating(review.getRating())                 // 리뷰 평점
            .content(review.getContent())               // 리뷰 내용
            .build();
    }
}
