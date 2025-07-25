package com.kernel.evaluation.service.review;


import com.kernel.evaluation.common.enums.ReviewAuthorType;
import com.kernel.evaluation.common.enums.ReviewErrorCode;
import com.kernel.evaluation.common.exception.ReviewException;
import com.kernel.evaluation.domain.entity.Review;
import com.kernel.evaluation.domain.info.ManagerReviewInfo;
import com.kernel.evaluation.repository.review.ManagerReviewRepository;
import com.kernel.evaluation.service.review.dto.request.ManagerReviewSearchCondDTO;
import com.kernel.evaluation.service.review.dto.request.ReviewCreateReqDTO;
import com.kernel.evaluation.service.review.dto.request.ReviewUpdateReqDTO;
import com.kernel.evaluation.service.review.dto.response.ManagerReviewPageRspDTO;
import com.kernel.evaluation.service.review.dto.response.ManagerReviewRspDTO;
import com.kernel.member.common.enums.MemberStatisticErrorCode;
import com.kernel.member.common.exception.MemberStatisticException;
import com.kernel.member.domain.entity.CustomerStatistic;
import com.kernel.member.repository.CustomerStatisticRepository;
import com.kernel.sharedDomain.common.enums.ReservationStatus;
import com.kernel.sharedDomain.domain.entity.Reservation;
import com.kernel.sharedDomain.service.ReservationQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ManagerReviewServiceImpl implements ManagerReviewService {

    private final ManagerReviewRepository managerReviewRepository;
    private final ReservationQueryPort reservationQueryPort;
    private final EvaluationStatisticUpdateService evaluationStatisticUpdateService;
    private final CustomerStatisticRepository customerStatisticRepository;

    /**
     * 매니저 리뷰 목록 조회 (검색 조건 및 페이징 처리)
     * @param managerId 매니저ID
     * @param searchCondDTO 검색조건DTO
     * @param pageable 페이징
     * @return 조건에 맞는 리뷰 정보를 담은 Page 객체
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ManagerReviewPageRspDTO> searchManagerReviewsWithPaging(
        Long managerId,
        ManagerReviewSearchCondDTO searchCondDTO,
        Pageable pageable
    ) {

        // 조건 및 페이징 포함된 매니저 리뷰 목록 조회
        Page<ManagerReviewInfo> searchedReviewsPage = managerReviewRepository.searchManagerReviewWithPaging(
            managerId,
            searchCondDTO.getFromDateTime(),
            searchCondDTO.getToDateTime(),
            searchCondDTO.getRatingOption(),
            searchCondDTO.getCustomerNameKeyword(),
            searchCondDTO.getContentKeyword(),
            pageable
        );

        // info -> DTO 매핑
        return searchedReviewsPage.map(ManagerReviewPageRspDTO::fromInfo);
    }

    /**
     * 매니저 리뷰 등록
     * @param userId 로그인한 유저ID
     * @param reservationId 예약ID
     * @param createReqDTO 매니저 리뷰 등록 요청 데이터
     * @return 작성된 리뷰 정보를 담은 응답
     */
    @Override
    @Transactional
    public ManagerReviewRspDTO createManagerReview(Long userId, Long reservationId, ReviewCreateReqDTO createReqDTO) {

        // 1. 해당 예약건의 매니저가 맞는지 체크
        Reservation reservation = reservationQueryPort.findReservationByReservationIdAndManagerId(reservationId, userId);
        if (reservation == null) {
            throw new ReviewException(ReviewErrorCode.UNAUTHORIZED);
        }

        // 2. 등록된 리뷰가 존재하는지 확인
        if (managerReviewRepository.existsByReviewAuthorTypeAndAuthorIdAndReservation_ReservationId(ReviewAuthorType.MANAGER, userId, reservationId)) {
            throw new ReviewException(ReviewErrorCode.ALREADY_EXISTS_REVIEW);
        }

        // 3. 예약건이 완료 상태인지 확인
        if (reservation.getStatus() != ReservationStatus.COMPLETED) {
            throw new ReviewException(ReviewErrorCode.NOT_COMPLETED_RESERVATION);
        }

        // 4. 리뷰 객체 빌드
        Review managerReview = Review.builder()
                .reservation(reservation)
                .authorId(userId)
                .reviewAuthorType(ReviewAuthorType.MANAGER)
                .targetId(reservation.getUser().getUserId())
                .rating(createReqDTO.getRating())
                .content(createReqDTO.getContent())
                .build();

        // 5. 리뷰 저장
        Review savedReview = managerReviewRepository.save(managerReview);

        // 6. 수요자 통계 업데이트
        CustomerStatistic customerStatistic = customerStatisticRepository.findById(reservation.getUser().getUserId())
                .orElseThrow(() -> new MemberStatisticException(MemberStatisticErrorCode.CUSTOMER_STATISTIC_NOT_FOUND));

        evaluationStatisticUpdateService.updateCustomerReviewStatisticOnCreate(customerStatistic, createReqDTO);

        // Entity -> ResponseDTO 후, return
        return ManagerReviewRspDTO.fromEntity(savedReview);
    }

    /**
     * 수요자 리뷰 수정
     * @param userId 로그인 유저 ID
     * @param reviewId 예약ID
     * @param updateReqDTO 리뷰요청DTO
     * @return reviewRspDTO
     */
    @Override
    public ManagerReviewRspDTO updateManagerReview(Long userId, Long reviewId, ReviewUpdateReqDTO updateReqDTO) {

        // 1. 예약 조회
        Reservation reservation = reservationQueryPort.findReservationByReservationIdAndManagerId(updateReqDTO.getReservationId(), userId);
        if (reservation == null) {
            throw new ReviewException(ReviewErrorCode.UNAUTHORIZED);
        }

        // 2. 리뷰 존재 여부 확인
        Review foundReview = managerReviewRepository.findById(reviewId)
                .orElseThrow(() -> new NoSuchElementException("수정 가능한 리뷰가 존재하지 않습니다."));

        // 3. 리뷰 수정
        foundReview.update(updateReqDTO.getRating(), updateReqDTO.getContent());

        // 6. 수요자 통계 업데이트
        CustomerStatistic customerStatistic = customerStatisticRepository.findById(reservation.getUser().getUserId())
                .orElseThrow(() -> new MemberStatisticException(MemberStatisticErrorCode.CUSTOMER_STATISTIC_NOT_FOUND));

        evaluationStatisticUpdateService.updateCustomerReviewStatisticOnUpdate(customerStatistic, updateReqDTO);


        // Entity -> ResponseDTO 후, return
        return ManagerReviewRspDTO.fromEntity(foundReview);
    }
}
