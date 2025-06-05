package com.kernel.common.manager.service;

import com.kernel.common.manager.dto.request.ManagerReviewReqDTO;
import com.kernel.common.manager.dto.request.ManagerReviewSearchCondDTO;
import com.kernel.common.manager.dto.response.ManagerReviewRspDTO;
import com.kernel.common.manager.dto.response.ManagerReviewSummaryRspDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ManagerReviewService {

    /**
     * 매니저 리뷰 목록 조회 (검색 조건 및 페이징 처리)
     * @param managerId 매니저ID
     * @param searchCondDTO 검색조건DTO
     * @param pageable 페이징
     * @return 조건에 맞는 리뷰 정보를 담은 Page 객체
     */
    Page<ManagerReviewSummaryRspDTO> searchManagerReviewsWithPaging(Long managerId, ManagerReviewSearchCondDTO searchCondDTO, Pageable pageable);

    /**
     * 매니저 리뷰 등록
     * @param managerReviewReqDTO 매니저 리뷰 등록 요청 DTO
     * @return 작성된 리뷰 정보를 담은 응답
     */
    ManagerReviewRspDTO createManagerReview(Long authorId, Long reservationId, ManagerReviewReqDTO managerReviewReqDTO);
}
