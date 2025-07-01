package com.kernel.common.manager.dto.mapper;

import com.kernel.common.global.enums.AuthorType;
import com.kernel.common.manager.dto.request.ManagerReviewReqDTO;
import com.kernel.common.manager.dto.response.ManagerReviewRspDTO;
import com.kernel.common.reservation.entity.Reservation;
import com.kernel.common.reservation.entity.Review;
import org.springframework.stereotype.Component;

@Component
public class ManagerReviewMapper {

    // CreateRequestDTO -> Entity
    public Review toEntity(Long authorId, Reservation reservation, ManagerReviewReqDTO requestDTO) {
        return Review.builder()
            .reservation(reservation)           // 예약
            .authorType(AuthorType.MANAGER)     // 작성자유형
            .authorId(authorId)                 // 작성자ID
            .rating(requestDTO.getRating())     // 리뷰 평점
            .content(requestDTO.getContent())   // 리뷰 내용
            .build();
    }

    // Entity -> ResponseDTO
    public ManagerReviewRspDTO toResponseDTO(Review review) {
        return ManagerReviewRspDTO.builder()
            .reviewId(review.getReviewId())                              // 리뷰ID
            .reservationId(review.getReservation().getReservationId())   // 예약
            .rating(review.getRating())                                  // 리뷰 평점
            .content(review.getContent())                                // 리뷰 내용
            .createdAt(review.getCreatedAt())                            // 리뷰 작성일시
            .build();
    }
}
