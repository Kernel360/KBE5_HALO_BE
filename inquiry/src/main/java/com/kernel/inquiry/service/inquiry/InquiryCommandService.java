package com.kernel.inquiry.service.inquiry;

import com.kernel.inquiry.common.enums.AuthorType;
import com.kernel.inquiry.service.dto.request.InquiryCreateReqDTO;
import com.kernel.inquiry.service.dto.request.InquiryDeleteReqDTO;
import com.kernel.inquiry.service.dto.request.InquiryUpdateReqDTO;

public interface InquiryCommandService {

    // 문의사항 생성
    void createInquiry(InquiryCreateReqDTO request, Long authorId, AuthorType authorType);

    // 문의사항 수정
    void updateInquiry(InquiryUpdateReqDTO request, Long authorId);

    // 문의사항 삭제
    void deleteInquiry(InquiryDeleteReqDTO request, Long authorId);

}
