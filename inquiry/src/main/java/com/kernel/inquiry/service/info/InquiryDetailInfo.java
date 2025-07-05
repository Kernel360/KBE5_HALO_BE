package com.kernel.inquiry.service.info;

import com.kernel.inquiry.common.enums.AuthorType;
import com.kernel.inquiry.common.enums.CustomerInquiryCategory;
import com.kernel.inquiry.common.enums.ManagerInquiryCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class InquiryDetailInfo {

    private Long inquiryId;
    private String categoryName;
    private String title;
    private String content;
    private Long fileId;
    private LocalDateTime createdAt;
    private AuthorType authorType;

    public String getCategoryLabel(){
        return switch (authorType){
            case CUSTOMER -> CustomerInquiryCategory.valueOf(categoryName).getLabel();
            case MANAGER -> ManagerInquiryCategory.valueOf(categoryName).getLabel();
            default -> categoryName;
        };
    }
}
