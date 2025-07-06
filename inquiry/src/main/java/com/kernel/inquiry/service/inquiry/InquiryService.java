package com.kernel.inquiry.service.inquiry;

import com.kernel.global.common.enums.UserRole;
import com.kernel.inquiry.service.dto.request.InquiryCreateReqDTO;
import com.kernel.inquiry.service.dto.request.InquirySearchReqDTO;
import com.kernel.inquiry.service.dto.request.InquiryUpdateReqDTO;
import com.kernel.inquiry.service.dto.response.InquiryDetailRspDTO;
import com.kernel.inquiry.service.dto.response.InquirySummaryRspDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InquiryService {

    // 문의사항 검색
    Page<InquirySummaryRspDTO> searchInquiries(InquirySearchReqDTO searchReqDTO, Long userId, Pageable pageable);

    // 문의사항 상세 조회
    InquiryDetailRspDTO getInquiryDetails(Long inquiryId, Long userId);

    // 문의사항 생성
    Long createInquiry(InquiryCreateReqDTO createReqDTO, Long userId, UserRole userRole);

    // 문의사항 수정
    void updateInquiry(Long inquiryId, InquiryUpdateReqDTO updateReqDTO, Long userId, UserRole userRole);

    // 문의사항 삭제
    void deleteInquiry(Long inquiryId, Long userId);

}
