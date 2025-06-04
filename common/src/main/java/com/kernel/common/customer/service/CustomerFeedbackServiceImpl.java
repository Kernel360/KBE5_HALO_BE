package com.kernel.common.customer.service;

import com.kernel.common.customer.dto.mapper.CustomerFeedbackMapper;
import com.kernel.common.customer.dto.request.CustomerFeedbackReqDTO;
import com.kernel.common.customer.dto.response.CustomerFeedbackRspDTO;
import com.kernel.common.customer.dto.response.CustomerFeedbackUpdateRspDTO;
import com.kernel.common.customer.repository.CustomerFeedbackRepository;
import com.kernel.common.global.entity.Feedback;
import com.kernel.common.global.enums.FeedbackType;
import com.kernel.common.reservation.entity.Reservation;
import com.kernel.common.reservation.repository.CustomerReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerFeedbackServiceImpl implements CustomerFeedbackService {

    private final CustomerFeedbackRepository customerFeedbackRepository;
    private final CustomerReservationRepository reservationRepository;
    private final CustomerFeedbackMapper mapper;

    /**
     * 수요자 피드백 조회 및 검색
     * @param customerId 수요자ID
     * @param feedbackType 피드백 타입
     * @param pageable 페이징 정보
     * @return 검색된 피드백 목록
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CustomerFeedbackRspDTO> searchCustomerFeedbackByFeedbackType(Long customerId, FeedbackType feedbackType, Pageable pageable) {

        // 수요자 피드백 조회 및 검색(page, type)
        Page<CustomerFeedbackRspDTO> foundFeedbackPage
                = customerFeedbackRepository.searchCustomerFeedbackByFeedbackType(customerId, feedbackType, pageable);

        return foundFeedbackPage;
    }

    /**
     * 수요자 피드백 등록(취소, 타입 변경)
     * @param customerId 수요자ID
     * @param reqDTO 피드백 요청 DTO
     * @return 검색된 피드백 목록
     */
    @Override
    @Transactional
    public CustomerFeedbackUpdateRspDTO proceedCustomerFeedback(Long customerId, CustomerFeedbackReqDTO reqDTO) {

        // 예약 존재 여부
        Reservation foundReservation
                = reservationRepository.findByCustomer_CustomerIdAndManager_ManagerId(customerId, reqDTO.getManagerId())
                .orElseThrow(() -> new NoSuchElementException("리뷰 등록이 가능한 예약이 없습니다."));

        // 피드백 존재 여부
        Optional<Feedback> foundFeedbackOpt
                = customerFeedbackRepository.findByCustomer_CustomerIdAndManager_ManagerId(customerId, reqDTO.getManagerId());

        Feedback savedFeedback;

        // 피드백이 없다면 새로 저장
        if(foundFeedbackOpt.isEmpty()) {
            savedFeedback = customerFeedbackRepository.save(mapper.toEntity(customerId, reqDTO));
        } else{
            Feedback foundFeedback = foundFeedbackOpt.get();
            // 타입 비교(타입이 다르면 타입 수정)
            if(!foundFeedback.getType().equals(reqDTO.getType())) {
                foundFeedback.changeType(reqDTO.getType());
                // 활성화
                foundFeedback.deleted(false);
            }else {
                // 타입이 같으면 삭제 처리
                foundFeedback.deleted(true);
            }
            // 재조회 없이 수정된 객체를 받기 위해 save 명시
            savedFeedback = customerFeedbackRepository.save(foundFeedback);
        }

        return mapper.toRspDTO(savedFeedback);
    }
}
