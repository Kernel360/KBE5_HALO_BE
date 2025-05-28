package com.kernel.common.manager.service;

import com.kernel.common.manager.dto.reponse.ManagerInquiryRspDTO;
import com.kernel.common.manager.dto.reponse.ManagerInquirySummaryRspDTO;
import com.kernel.common.manager.dto.request.ManagerInquiryCreateReqDTO;
import com.kernel.common.manager.dto.request.ManagerInquiryUpdateReqDTO;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ManagerInquiryService {

    /**
     * 매니저 상담 게시글 목록 조회 (검색 조건 및 페이징 처리)
     * @param authorId 작성자ID(=매니저ID)
     * @param fromCreatedAt 작성일시 시작일
     * @param toCreatedAt 작성일시 종료일
     * @param replyStatus 답변 상태
     * @param titleKeyword 제목 키워드
     * @param contentKeyword 내용 키워드
     * @param pageable 페이징
     * @return 조회된 게시글 목록을 담은 응답
     */
    public Page<ManagerInquirySummaryRspDTO> searchManagerinquiriesWithPaging(
        Long authorId,
        LocalDateTime fromCreatedAt,
        LocalDateTime toCreatedAt,
        String replyStatus,
        String titleKeyword,
        String contentKeyword,
        Pageable pageable
    );

    /**
     * 매니저 상담 게시글 상세조회
     * @param authorId 작성자ID(=매니저ID)
     * @param inquiryId 게시글ID
     * @return 게시글 상세 정보를 담은 응답
     */
    public ManagerInquiryRspDTO getManagerInquiry(Long authorId, Long inquiryId);

    /**
     * 매니저 상담 게시글 등록
     * @param authorId 작성자ID(=매니저ID)
     * @param requestDTO 게시글 등록 요청 데이터
     * @return 작성된 게시글 정보를 담은 응답
     */
    public ManagerInquirySummaryRspDTO createManagerInquiry(Long authorId, ManagerInquiryCreateReqDTO requestDTO);

    /**
     * 매니저 상담 게시글 수정
     * @param authorId 작성자ID(=매니저ID)
     * @param requestDTO 게시글 수정 요청 데이터
     * @return 수정된 게시글 정보를 담은 응답
     */
    public ManagerInquirySummaryRspDTO updateManagerInquiry(Long authorId, ManagerInquiryUpdateReqDTO requestDTO);

    /**
     * 매니저 상담 게시글 삭제
     * @param authorId 작성자ID(=매니저ID)
     * @param inquiryId 게시글ID
     */
    public void deleteManagerInquiry(Long authorId, Long inquiryId);
}
