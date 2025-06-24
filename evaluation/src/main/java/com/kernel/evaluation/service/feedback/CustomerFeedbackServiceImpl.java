package com.kernel.evaluation.service.feedback;


import com.kernel.evaluation.common.enums.FeedbackType;
import com.kernel.evaluation.domain.entity.Feedback;
import com.kernel.evaluation.domain.info.CustomerFeedbackInfo;
import com.kernel.evaluation.repository.feedback.CustomerFeedbackRepository;
import com.kernel.evaluation.service.feedback.dto.request.CustomerFeedbackReqDTO;
import com.kernel.evaluation.service.feedback.dto.response.CustomerFeedbackRspDTO;
import com.kernel.evaluation.service.feedback.dto.response.CustomerFeedbackUpdateRspDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerFeedbackServiceImpl implements CustomerFeedbackService {

    private final CustomerFeedbackRepository customerFeedbackRepository;

    /**
     * 수요자 피드백 조회 및 검색
     * @param userId 로그인한 유저
     * @param feedbackType 피드백 타입
     * @param pageable 페이징 정보
     * @return 검색된 피드백 목록
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CustomerFeedbackRspDTO> searchCustomerFeedbackByFeedbackType(Long userId, FeedbackType feedbackType, Pageable pageable) {

        // 수요자 피드백 조회 및 검색(page, type)
        Page<CustomerFeedbackInfo> foundFeedbackPage
                = customerFeedbackRepository.searchFeedbackByFeedbackType(userId, feedbackType, pageable);

        return foundFeedbackPage.map(CustomerFeedbackRspDTO::fromInfo);
    }

    /**
     * 수요자 피드백 등록(취소, 타입 변경)
     * @param userId 로그인한 유저
     * @param reqDTO 피드백 요청 DTO
     * @return 검색된 피드백 목록
     */
    @Override
    @Transactional
    public CustomerFeedbackUpdateRspDTO proceedCustomerFeedback(Long userId, CustomerFeedbackReqDTO reqDTO) {

        // 예약 존재 여부 TODO 예약 모듈 정리 후 연결
      /*  Reservation foundReservation
                = reservationRepository.findByCustomer_CustomerIdAndManager_ManagerId(userId, reqDTO.getManagerId())
                .orElseThrow(() -> new NoSuchElementException("리뷰 등록이 가능한 예약이 없습니다."));
*/
        // 피드백 존재 여부
        Optional<Feedback> foundFeedbackOpt
                = customerFeedbackRepository.findByCustomer_CustomerIdAndManager_ManagerId(userId, reqDTO.getManagerId());

        Feedback savedFeedback;

        // 피드백이 없다면 새로 저장
        if(foundFeedbackOpt.isEmpty()) {
            savedFeedback = customerFeedbackRepository.save(reqDTO.toEntity(userId, reqDTO));
        } else{
            Feedback foundFeedback = foundFeedbackOpt.get();
            // 타입 비교(타입이 다르면 타입 수정)
            if(!foundFeedback.getType().equals(reqDTO.getType())) {
                foundFeedback.changeType(reqDTO.getType());
                // 활성화
                foundFeedback.changeDeleted(false);
            }else {
                // 타입이 같으면 삭제 처리
                foundFeedback.changeDeleted(true);
            }
            // 재조회 없이 수정된 객체를 받기 위해 save 명시
            savedFeedback = customerFeedbackRepository.save(foundFeedback);
        }

        return CustomerFeedbackUpdateRspDTO.toRspDTO(savedFeedback);
    }
}
