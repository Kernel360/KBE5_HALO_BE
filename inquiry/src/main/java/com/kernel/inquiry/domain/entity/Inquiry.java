package com.kernel.inquiry.domain.entity;

import com.kernel.inquiry.domain.enumerate.CustomerInquiryCategory;
import com.kernel.inquiry.domain.enumerate.ManagerInquiryCategory;

public class Inquiry {

    private Long inquiryId;

    // 카테고리 조회용
    private CustomerInquiryCategory customerInquiryCategory;

    // 카테고리 조회용
    private ManagerInquiryCategory managerInquiryCategory;

    // 작성자 ID
    private Long authorId;

    // 제목
    private String title;

    // 내용
    private String content;

    // 첨부파일 ID
    private Long fileId;

    // 삭제 여부
    private Boolean isDeleted = Boolean.FALSE;

}
