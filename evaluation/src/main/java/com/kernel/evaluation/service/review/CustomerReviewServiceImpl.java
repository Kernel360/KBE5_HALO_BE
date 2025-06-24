package com.kernel.evaluation.service.review;


import com.kernel.evaluation.domain.entity.Review;
import com.kernel.evaluation.domain.info.CustomerReviewInfo;
import com.kernel.evaluation.repository.review.CustomerReviewRepository;
import com.kernel.evaluation.service.review.dto.request.ReviewCreateReqDTO;
import com.kernel.evaluation.service.review.dto.request.ReviewUpdateReqDTO;
import com.kernel.evaluation.service.review.dto.response.CustomerReviewRspDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CustomerReviewServiceImpl implements CustomerReviewService {

    private final CustomerReviewRepository customerReviewRepository;

    /**
     * 수요자 리뷰 목록 조회
     * @param userId 로그인 유저 ID
     * @param pageable 페이징
     * @return reviewRspDTO
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CustomerReviewRspDTO> getCustomerReviews(Long userId, Pageable pageable) {

        Page<CustomerReviewInfo> result = customerReviewRepository.getCustomerReviews(userId,pageable);

        return result.map(CustomerReviewRspDTO::fromInfo);
    }

    /**
     * 수요자 리뷰 조회 by 예약ID
     * @param userId 로그인 유저 ID
     * @param reservationId 예약ID
     * @return reviewRspDTO
     */
    @Override
    @Transactional(readOnly = true)
    public CustomerReviewRspDTO getCustomerReviewsByReservationId(Long userId, Long reservationId) {

        CustomerReviewInfo result = customerReviewRepository.getCustomerReviewsByReservationId(userId, reservationId);

        return CustomerReviewRspDTO.fromInfo(result);
    }

    /**
     * 수요자 리뷰 등록
     * @param userId 로그인 유저 ID
     * @param reservationId 예약ID
     * @param createReqDTO 리뷰요청DTO
     * @return reviewRspDTO
     */
    @Override
    @Transactional
    public CustomerReviewRspDTO createCustomerReview(Long userId, Long reservationId, ReviewCreateReqDTO createReqDTO) {

        // TODO 수요자 예약 쪽 완성 후 이어주기 1. 리뷰 작성 가능 예약인지 확인
      /*  Optional<Reservation> foundReservation = customerReservationService.getCompletedReservationByUserIdAndReservationId(reservationId,userId)
                .orElseThrow(()-> new NoSuchElementException("리뷰를 작성할 수 있는 예약이 존재하지 않습니다."));

        // 2. 리뷰 타켓 ID 조회
        Optional<ReservationMatch> foundReservationMatch = customerReservationService.getReservationManagerId(reservationId)
                .orElseThrow(() -> new NoSuchElementException("해당 예약에 매칭된 매니저가 없습니다."));

        // 리뷰 저장
        customerReviewRepository.save( Review.builder()
                .reservation(foundReservation)
                .authorId(userId)
                .authorType(AuthorType.CUSTOMER)
                .targetId(foundReservationMatch.getManagerId())
                .rating(createReqDTO.getRating())
                .content(createReqDTO.getContent())

        );
*/
        // 리뷰 조회 후 반환
        CustomerReviewInfo result = customerReviewRepository.getCustomerReviewsByReservationId(userId, reservationId);

        return CustomerReviewRspDTO.fromInfo(result);
    }

    /**
     * 수요자 리뷰 수정
     * @param userId 로그인 유저 ID
     * @param reviewId 예약ID
     * @param updateReqDTO 리뷰요청DTO
     * @return reviewRspDTO
     */
    @Override
    @Transactional
    public CustomerReviewRspDTO updateCustomerReview(Long userId, Long reviewId, ReviewUpdateReqDTO updateReqDTO) {

        // TODO 수요자 예약 쪽 완성 후 이어주기 1. 리뷰 작성 가능 예약인지 확인
      /*  Optional<Reservation> foundReservation = customerReservationService.getCompletedReservationByUserIdAndReservationId(reservationId,userId)
                .orElseThrow(()-> new NoSuchElementException("리뷰를 작성할 수 있는 예약이 존재하지 않습니다."));
        */
        // 리뷰 존재 여부 확인
        Review foundReview = customerReviewRepository.findById(reviewId)
                .orElseThrow(() -> new NoSuchElementException("수정 가능한 리뷰가 존재하지 않습니다."));

        // 리뷰 수정
        foundReview.update(updateReqDTO.getRating(), updateReqDTO.getContent());

        // 리뷰 조회 후 반환
        CustomerReviewInfo result = customerReviewRepository.getCustomerReviewsByReservationId(userId, updateReqDTO.getReservationId());

        return CustomerReviewRspDTO.fromInfo(result);
    }



}
