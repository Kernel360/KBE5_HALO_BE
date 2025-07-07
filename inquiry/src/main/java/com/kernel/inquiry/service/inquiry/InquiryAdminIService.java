package com.kernel.inquiry.service.inquiry;

import com.kernel.global.service.dto.response.EnumValueDTO;
import com.kernel.inquiry.service.dto.request.InquiryAdminSearchReqDTO;
import com.kernel.inquiry.service.dto.response.InquiryAdminDetailRspDTO;
import com.kernel.inquiry.service.dto.response.InquiryAdminSummaryRspDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface InquiryAdminIService {

    // 문의사항 검색
    Page<InquiryAdminSummaryRspDTO> searchInquiries(InquiryAdminSearchReqDTO searchReqDTO, Pageable pageable);

    // 문의사항 상세 조회
    InquiryAdminDetailRspDTO getInquiryDetails(Long inquiryId);

    // 문의사항 카테고리 조회
    Map<String, List<EnumValueDTO>> getAllInquiryCategoriesForAdmin();
}
