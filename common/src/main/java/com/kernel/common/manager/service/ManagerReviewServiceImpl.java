package com.kernel.common.manager.service;

import com.kernel.common.global.enums.AuthorType;
import com.kernel.common.manager.dto.mapper.ManagerReviewMapper;
import com.kernel.common.manager.dto.request.ManagerReviewReqDTO;
import com.kernel.common.manager.dto.response.ManagerReviewRspDTO;
import com.kernel.common.manager.repository.ManagerReviewRepository;
import com.kernel.common.reservation.entity.Reservation;
import com.kernel.common.reservation.entity.Review;
import com.kernel.common.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ManagerReviewServiceImpl implements ManagerReviewService {

    private final ManagerReviewRepository managerReviewRepository;
    private final ReservationRepository reservationRepository;
    private final ManagerReviewMapper managerReviewMapper;


    /**
     * 매니저 리뷰 등록
     * @param authorId 작성자ID(=매니저ID)
     * @param reservationId 예약ID
     * @param managerReviewReqDTO 매니저 리뷰 등록 요청 데이터
     * @return 작성된 리뷰 정보를 담은 응답
     */
    @Override
    public ManagerReviewRspDTO createManagerReview(Long authorId, Long reservationId, ManagerReviewReqDTO managerReviewReqDTO) {

        // 등록된 리뷰가 존재하는지 확인
        if (managerReviewRepository.existsByAuthorTypeAndAuthorIdAndReservation_ReservationId(AuthorType.MANAGER, authorId, reservationId)) {
            throw new IllegalStateException("이미 등록된 리뷰가 존재합니다.");
        }

        // RequestDTO -> Entity
        Reservation foundReservation = reservationRepository.findReservationByreservationId(reservationId);
        if (foundReservation == null) {
            throw new NoSuchElementException("존재하지 않는 예약입니다.");
        }
        Review reviewedEntity = managerReviewMapper.toEntity(authorId, foundReservation, managerReviewReqDTO);

        // 저장
        Review savedReview = managerReviewRepository.save(reviewedEntity);

        // Entity -> ResponseDTO 후, return
        return managerReviewMapper.toResponseDTO(savedReview);
    }
}
