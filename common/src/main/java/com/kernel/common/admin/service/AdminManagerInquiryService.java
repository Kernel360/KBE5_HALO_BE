package com.kernel.common.admin.service;

import com.kernel.common.admin.dto.request.AdminInquiryReplyReqDTO;
import com.kernel.common.admin.dto.request.AdminInquirySearchReqDTO;
import com.kernel.common.admin.dto.response.AdminInquiryDetailRspDTO;
import com.kernel.common.admin.dto.response.AdminInquirySummaryManagerRspDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminManagerInquiryService {

    // Manager 문의사항 서비스
    Page<AdminInquirySummaryManagerRspDTO> getManagerInquiryPage(AdminInquirySearchReqDTO query, Pageable pageable);
    AdminInquiryDetailRspDTO getManagerInquiryDetail(Long inquiryId);
    void DeleteManagerInquiry(Long inquiryId);
    void CreateReplyManagerInquiry(AdminInquiryReplyReqDTO reply, Long authorId);
    void UpdateReplyManagerInquiry(AdminInquiryReplyReqDTO reply, Long authorId, Long replyId);

}
