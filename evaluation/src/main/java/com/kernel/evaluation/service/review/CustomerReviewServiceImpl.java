package com.kernel.evaluation.service.review;


import com.kernel.evaluation.common.enums.ReviewAuthorType;
import com.kernel.evaluation.domain.entity.Review;
import com.kernel.evaluation.domain.info.CustomerReviewInfo;
import com.kernel.evaluation.repository.review.CustomerReviewRepository;
import com.kernel.evaluation.service.review.dto.request.ReviewCreateReqDTO;
import com.kernel.evaluation.service.review.dto.request.ReviewSearchReqDTO;
import com.kernel.evaluation.service.review.dto.request.ReviewUpdateReqDTO;
import com.kernel.evaluation.service.review.dto.response.CustomerReviewRspDTO;
import com.kernel.member.common.enums.MemberStatisticErrorCode;
import com.kernel.member.common.exception.MemberStatisticException;
import com.kernel.member.domain.entity.ManagerStatistic;
import com.kernel.member.repository.ManagerStatisticRepository;
import com.kernel.sharedDomain.domain.entity.Reservation;
import com.kernel.sharedDomain.service.ReservationQueryPort;
import com.kernel.sharedDomain.service.response.ScheduleAndMatchInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerReviewServiceImpl implements CustomerReviewService {

    private final CustomerReviewRepository customerReviewRepository;
    private final ReservationQueryPort reservationQueryPort;
    private final ManagerStatisticRepository managerStatisticRepository;
    private final EvaluationStatisticUpdateService evaluationStatisticUpdateService;

    /**
     * 수요자 리뷰 목록 조회
     * @param userId 로그인 유저 ID
     * @param pageable 페이징
     * @return reviewRspDTO
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CustomerReviewRspDTO> getCustomerReviews(Long userId, ReviewSearchReqDTO searchReqDTO, Pageable pageable) {

        Page<CustomerReviewInfo> reviewInfoList;

        // 1. 리뷰 조회
        if(searchReqDTO.getRating() == null) {
            reviewInfoList = customerReviewRepository.getCustomerReviewsAll(userId, pageable);
        }else if (searchReqDTO.getRating() == 0){
            reviewInfoList = customerReviewRepository.getReservationsWithoutReview(userId, pageable);
        }else{
            reviewInfoList = customerReviewRepository.getCustomerReviewsByRating(userId, searchReqDTO, pageable);
        }

        // 2. 예약ID 추출
        List<Long> reservationIds = reviewInfoList.getContent().stream().map(CustomerReviewInfo::getReservationId).toList();

        // 3. 예약 정보 조회
        List<ScheduleAndMatchInfo> scheduleAndMatchList =
                reservationQueryPort.findSchedulesAndMatchesByUserIdAndReservationIds(userId, reservationIds);

        // 4. 예약 ID → 일정/매칭 정보 매칭
        Map<Long, ScheduleAndMatchInfo> scheduleAndMatchInfoMap = scheduleAndMatchList.stream()
                .collect(Collectors.toMap(ScheduleAndMatchInfo::getReservationId, info -> info));

        // 5. 일정/매칭 정보 + 리뷰 정보를 DTO로 변환
        List<CustomerReviewRspDTO> content = reviewInfoList.getContent().stream()
                .map(reviewInfo -> {
                    ScheduleAndMatchInfo scheduleAndMatchInfo = scheduleAndMatchInfoMap.get(reviewInfo.getReservationId());
                    return CustomerReviewRspDTO.fromInfo(reviewInfo, scheduleAndMatchInfo);
                })
                .toList();

        // 5. 페이징 처리
        return new PageImpl<>(content, pageable, reviewInfoList.getTotalElements());
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

        // 1. 리뷰 정보 조회
        CustomerReviewInfo reviewInfo = customerReviewRepository.getCustomerReviewsByReservationId(userId, reservationId);

        // 2. 예약 일정 및 매칭 조회
        ScheduleAndMatchInfo scheduleAndMatchInfo = reservationQueryPort.findScheduleAndMatchByReservationIdAndUserId(reservationId, userId);

        return CustomerReviewRspDTO.fromInfo(reviewInfo, scheduleAndMatchInfo);
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

        // 1. 예약 조회
        Reservation foundReservation = reservationQueryPort.findReservationByReservationIdAndUserId(reservationId, userId);

        // 2. 예약 일정 및 매칭 조회
        ScheduleAndMatchInfo scheduleAndMatchInfo = reservationQueryPort.findScheduleAndMatchByReservationIdAndUserId(reservationId, userId);

        // 3. 리뷰 저장
        customerReviewRepository.save(Review.builder()
                .reservation(foundReservation)
                .authorId(userId)
                .reviewAuthorType(ReviewAuthorType.CUSTOMER)
                .targetId(scheduleAndMatchInfo.getManagerId())
                .rating(createReqDTO.getRating())
                .content(createReqDTO.getContent())
                .build());

        // 4. 리뷰 정보 조회
        CustomerReviewInfo reviewInfo = customerReviewRepository.getCustomerReviewsByReservationId(userId, reservationId);

        // 5. 매니저 통계 업데이트
        ManagerStatistic managerStatistic = managerStatisticRepository.findById(scheduleAndMatchInfo.getManagerId())
                .orElseThrow(() -> new MemberStatisticException(MemberStatisticErrorCode.MANAGER_STATISTIC_NOT_FOUND));

        evaluationStatisticUpdateService.updateManagerReviewStatistic(managerStatistic);

        return CustomerReviewRspDTO.fromInfo(reviewInfo, scheduleAndMatchInfo);
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

        // 1. 예약 조회
        reservationQueryPort.findReservationByReservationIdAndUserId(updateReqDTO.getReservationId(), userId);

        // 2. 리뷰 존재 여부 확인
        Review foundReview = customerReviewRepository.findById(reviewId)
                .orElseThrow(() -> new NoSuchElementException("수정 가능한 리뷰가 존재하지 않습니다."));

        // 3. 리뷰 수정
        foundReview.update(updateReqDTO.getRating(), updateReqDTO.getContent());

        // 4. 리뷰 조회 후 반환
        CustomerReviewInfo reviewInfo = customerReviewRepository.getCustomerReviewsByReservationId(userId, updateReqDTO.getReservationId());

        // 5. 예약 일정 및 매칭 조회
        ScheduleAndMatchInfo scheduleAndMatchInfo = reservationQueryPort.findScheduleAndMatchByReservationIdAndUserId(updateReqDTO.getReservationId(), userId);

        return CustomerReviewRspDTO.fromInfo(reviewInfo, scheduleAndMatchInfo);
    }



}
