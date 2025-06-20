package com.kernel.inquiry.service.inquiry;

import com.kernel.inquiry.service.dto.request.InquirySearchReqDTO;
import com.kernel.inquiry.service.dto.response.InquiryDetailRspDTO;
import com.kernel.inquiry.service.dto.response.InquirySummaryRspDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InquiryQueryService {

    // 문의사항 검색
    Page<InquirySummaryRspDTO> searchInquiries(InquirySearchReqDTO request, Long authorId, Pageable pageable);

    // 문의사항 상세 조회
    InquiryDetailRspDTO getInquiryDetails(Long inquiryId);
}
