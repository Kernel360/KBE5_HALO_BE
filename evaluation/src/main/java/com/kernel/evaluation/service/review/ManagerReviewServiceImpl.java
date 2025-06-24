package com.kernel.evaluation.service.review;


import com.kernel.evaluation.common.enums.ReviewAuthorType;
import com.kernel.evaluation.domain.entity.Review;
import com.kernel.evaluation.domain.info.ManagerReviewInfo;
import com.kernel.evaluation.repository.review.ManagerReviewRepository;
import com.kernel.evaluation.service.review.dto.request.ManagerReviewSearchCondDTO;
import com.kernel.evaluation.service.review.dto.request.ReviewCreateReqDTO;
import com.kernel.evaluation.service.review.dto.response.ManagerReviewPageRspDTO;
import com.kernel.evaluation.service.review.dto.response.ManagerReviewRspDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManagerReviewServiceImpl implements ManagerReviewService {

    private final ManagerReviewRepository managerReviewRepository;

    /**
     * 매니저 리뷰 목록 조회 (검색 조건 및 페이징 처리)
     * @param managerId 매니저ID
     * @param searchCondDTO 검색조건DTO
     * @param pageable 페이징
     * @return 조건에 맞는 리뷰 정보를 담은 Page 객체
     */
    @Override
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
        return new PageImpl<>(
                    searchedReviewsPage.getContent().stream()
                    .map(ManagerReviewPageRspDTO::fromInfo)
                    .toList(),
                    pageable,
                    searchedReviewsPage.getTotalElements()
            );
    }

    /**
     * 매니저 리뷰 등록
     * @param userId 로그인한 유저ID
     * @param reservationId 예약ID
     * @param createReqDTO 매니저 리뷰 등록 요청 데이터
     * @return 작성된 리뷰 정보를 담은 응답
     */
    @Override
    public ManagerReviewRspDTO createManagerReview(Long userId, Long reservationId, ReviewCreateReqDTO createReqDTO) {

        /* TODO 예약 모듈 정리되면 연결
        // 1. 해당 예약건의 매니저가 맞는지 체크
        if (!reservationRepository.existsByReservationIdAndManager_ManagerId(reservationId, userId)) {
            throw new IllegalStateException("해당 예약건의 매니저가 아닙니다.");
        }
*/
        // 2. 등록된 리뷰가 존재하는지 확인
        if (managerReviewRepository.existsByAuthorTypeAndAuthorIdAndReservation_ReservationId(ReviewAuthorType.MANAGER, userId, reservationId)) {
            throw new IllegalStateException("이미 등록된 리뷰가 존재합니다.");
        }

        /* TODO 예약 모듈 정리되면 연결
        // 3. RequestDTO -> Entity
        Reservation foundReservation = reservationRepository.findReservationByReservationId(reservationId);
        if (foundReservation == null) {
            throw new NoSuchElementException("존재하지 않는 예약입니다.");
        }
        */


        // 저장
        Review savedReview = managerReviewRepository.save(
                            Review.builder()
                      //      .reservation(foundReservation)
                            .authorId(userId)
                            .reviewAuthorType(ReviewAuthorType.MANAGER)
                      //      .targetId(foundReservation.getUser().getUserId())
                            .rating(createReqDTO.getRating())
                            .content(createReqDTO.getContent())
                            .build()
                    );

        // Entity -> ResponseDTO 후, return
        return ManagerReviewRspDTO.fromEntity(savedReview);
    }
}
