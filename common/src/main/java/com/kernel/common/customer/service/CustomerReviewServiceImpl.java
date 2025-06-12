package com.kernel.common.customer.service;

import com.kernel.common.customer.dto.mapper.CustomerReviewMapper;
import com.kernel.common.customer.dto.request.CustomerReviewReqDTO;
import com.kernel.common.customer.dto.response.CustomerReviewRspDTO;
import com.kernel.common.customer.repository.CustomerReviewRepository;
import com.kernel.common.reservation.entity.Review;
import com.kernel.common.global.enums.AuthorType;
import com.kernel.common.reservation.enums.ReservationStatus;
import com.kernel.common.reservation.repository.CustomerReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CustomerReviewServiceImpl implements CustomerReviewService {

    private final CustomerReviewRepository reviewRepository;
    private final CustomerReservationRepository reservationRepository;
    private final CustomerReviewMapper reviewMapper;

    /**
     * 수요자 리뷰 목록 조회
     * @param customerId 수요자ID
     * @param pageable 페이징
     * @return reviewRspDTO
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CustomerReviewRspDTO> getCustomerReviews(Long customerId,Pageable pageable) {

        return reviewRepository.getCustomerReviews(customerId,pageable);
    }

    /**
     * 수요자 리뷰 조회 by 예약ID
     * @param customerId 수요자ID
     * @param reservationId 예약ID
     * @return reviewRspDTO
     */
    @Override
    @Transactional(readOnly = true)
    public CustomerReviewRspDTO getCustomerReviewsByReservationId(Long customerId, Long reservationId) {

        return reviewRepository.getCustomerReviewsByReservationId(customerId, reservationId);

    }

    /**
     * 수요자 리뷰 등록/수정
     * @param customerId 수요자ID
     * @param reservationId 예약ID
     * @param reviewReqDTO 리뷰요청DTO
     * @return reviewRspDTO
     */
    @Override
    @Transactional
    public CustomerReviewRspDTO createOrUpdateCustomerReview(Long customerId, Long reservationId, CustomerReviewReqDTO reviewReqDTO) {

        // 예약 존재 여부 확인
        exitsReservation(reservationId, customerId, ReservationStatus.COMPLETED);

        // 등록 리뷰 존재 확인
        Review foundReview = reviewRepository.findByReservation_ReservationIdAndAuthorIdAndAuthorType(reservationId, customerId, AuthorType.CUSTOMER);

        if(foundReview == null) {
            // 리뷰 저장
            reviewRepository.save(reviewMapper.toEntity(customerId, reservationId, reviewReqDTO));
        }else{
            // 리뷰 수정
            foundReview.update(reviewReqDTO.getRating(), reviewReqDTO.getContent());
        }

        // 리뷰 조회 후 반환
        return reviewRepository.getCustomerReviewsByReservationId(customerId, reservationId);
    }

     /**
     * 예약 존재 확인
     * @param customerId 수요자ID
     * @param reservationId 예약ID
     * @param status 예약상태
     */
    private void exitsReservation(Long reservationId, Long customerId, ReservationStatus status) {
         if(!reservationRepository.existsByReservationIdAndCustomer_CustomerIdAndStatus(reservationId, customerId, status))
            throw new NoSuchElementException("리뷰 작성이 가능한 예약이 없습니다.");
    }
}
