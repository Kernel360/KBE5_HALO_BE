package com.kernel.inquiry.service.inquiry;

import com.kernel.inquiry.service.dto.request.InquiryAdminSearchReqDTO;
import com.kernel.inquiry.service.dto.response.InquiryAdminDetailRspDTO;
import com.kernel.inquiry.service.dto.response.InquirySummaryRspDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InquiryAdminIService {

    // 문의사항 검색
    Page<InquirySummaryRspDTO> searchInquiries(InquiryAdminSearchReqDTO searchReqDTO, Pageable pageable);

    // 문의사항 상세 조회
    InquiryAdminDetailRspDTO getInquiryDetails(Long inquiryId);

}
