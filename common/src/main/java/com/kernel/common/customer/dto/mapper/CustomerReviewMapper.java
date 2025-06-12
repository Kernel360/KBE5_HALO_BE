package com.kernel.common.customer.dto.mapper;

import com.kernel.common.customer.dto.request.CustomerReviewReqDTO;
import com.kernel.common.global.enums.AuthorType;
import com.kernel.common.reservation.entity.Reservation;
import com.kernel.common.reservation.entity.Review;
import org.springframework.stereotype.Component;

@Component
public class CustomerReviewMapper {

    // reqDTO -> Entity (등록)
    public Review toEntity(Long customerId, Long reservationId, CustomerReviewReqDTO reqDTO) {
        return Review.builder()
                .reservation(Reservation.builder().reservationId(reservationId).build())
                .authorType(AuthorType.CUSTOMER)
                .authorId(customerId)
                .rating(reqDTO.getRating())
                .content(reqDTO.getContent())
                .build();
    }
}
