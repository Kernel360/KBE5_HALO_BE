package com.kernel.inquiry.repository;

import com.kernel.inquiry.service.dto.request.InquiryAdminSearchReqDTO;
import com.kernel.inquiry.service.info.InquiryAdminDetailInfo;
import com.kernel.inquiry.service.info.InquiryAdminSummaryInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomInquiryAdminRepository {

    // 관리자 문의사항 목록 조회
    Page<InquiryAdminSummaryInfo> searchInquiriesWithPagination(InquiryAdminSearchReqDTO searchReqDTO, Pageable pageable);

    // 관리자 문의사항 상세 조회
    InquiryAdminDetailInfo getInquiryDetails(Long inquiryId);
}
