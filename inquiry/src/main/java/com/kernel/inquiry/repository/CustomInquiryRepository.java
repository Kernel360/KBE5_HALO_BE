package com.kernel.inquiry.repository;

import com.kernel.inquiry.service.info.InquirySummaryInfo;
import com.kernel.inquiry.service.dto.request.InquirySearchReqDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomInquiryRepository {

    // 문의 목록 검색
    Page<InquirySummaryInfo> searchInquiriesWithPagination(InquirySearchReqDTO searchReqDTO, Long userId, Pageable pageable);

}
