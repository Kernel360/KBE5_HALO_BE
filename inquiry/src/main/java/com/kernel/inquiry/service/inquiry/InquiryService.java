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
    Page<InquirySummaryRspDTO> searchInquiries(InquirySearchReqDTO request, Long authorId, Pageable pageable);

    // 문의사항 상세 조회
    InquiryDetailRspDTO getInquiryDetails(Long inquiryId);

    // 문의사항 생성
    void createInquiry(InquiryCreateReqDTO request, Long authorId, UserRole authorRole);

    // 문의사항 수정
    void updateInquiry(InquiryUpdateReqDTO request, Long authorId);

    // 문의사항 삭제
    void deleteInquiry(Long inquiryId, Long authorId);

}
