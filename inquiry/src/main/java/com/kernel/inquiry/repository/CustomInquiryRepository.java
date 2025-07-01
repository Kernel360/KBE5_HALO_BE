package com.kernel.inquiry.repository;

import com.kernel.inquiry.domain.entity.Inquiry;
import com.kernel.inquiry.domain.info.InquirySummaryInfo;
import com.kernel.inquiry.service.dto.request.InquirySearchReqDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomInquiryRepository {

    Page<InquirySummaryInfo> searchInquiriesWithPagination(InquirySearchReqDTO request, Long authorId,  Boolean isAdmin, Pageable pageable);
}
