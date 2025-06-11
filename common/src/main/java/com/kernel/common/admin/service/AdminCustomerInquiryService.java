package com.kernel.common.admin.service;

import com.kernel.common.admin.dto.request.AdminInquiryReplyReqDTO;
import com.kernel.common.admin.dto.request.AdminInquirySearchReqDTO;
import com.kernel.common.admin.dto.response.AdminInquiryDetailRspDTO;
import com.kernel.common.admin.dto.response.AdminInquirySummaryCustomerRspDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminCustomerInquiryService {

    // Customer 문의사항 서비스
    Page<AdminInquirySummaryCustomerRspDTO> getCustomerInquiryPage(AdminInquirySearchReqDTO request, Pageable pageable);
    AdminInquiryDetailRspDTO getCustomerInquiryDetail(Long inquiryId, Long authorId);
    void DeleteCustomerInquiry(Long inquiryId);
    void CreateReplyCustomerInquiry(AdminInquiryReplyReqDTO reply, Long authorId);
    void UpdateReplyCustomerInquiry(AdminInquiryReplyReqDTO reply, Long authorId, Long replyId);

}
