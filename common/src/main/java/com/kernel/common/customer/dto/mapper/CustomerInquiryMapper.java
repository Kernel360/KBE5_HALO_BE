package com.kernel.common.customer.dto.mapper;


import com.kernel.common.customer.dto.request.CustomerInquiryCreateReqDTO;
import com.kernel.common.customer.dto.response.CustomerInquiryDetailRspDTO;
import com.kernel.common.customer.dto.response.CustomerInquiryReplyRspDTO;
import com.kernel.common.customer.dto.response.CustomerInquiryRspDTO;
import com.kernel.common.customer.entity.CustomerInquiry;
import com.kernel.common.customer.entity.CustomerReply;
import com.kernel.common.customer.entity.InquiryCategory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomerInquiryMapper {

    // RequestDTO -> Entity
    public CustomerInquiry toEntity(Long customerId, CustomerInquiryCreateReqDTO dto, InquiryCategory category){
        return CustomerInquiry.builder()
                .category(category)
                .authorId(customerId)
                .title(dto.getTitle())
                .content(dto.getContent())
                //TODO 첨부파일
                //.fileId(dto.getFileId)
                .build();
    }

    // CustomerInquiry -> CustomerInquiryRspDTO (문의사항 목록)
    public CustomerInquiryRspDTO toRspDTO(CustomerInquiry inquiry){
        return CustomerInquiryRspDTO.builder()
                .inquiryId(inquiry.getInquiryId())
                .title(inquiry.getTitle())
                .createdAt(inquiry.getCreatedAt())
                .isReplied(inquiry.getCustomerReply() != null)
                .build();
    }

    // CustomerInquiry -> CustomerInquiryDetailRspDTO (문의사항 상세)
    public CustomerInquiryDetailRspDTO toDetailRspDTO(CustomerInquiry inquiry){
        return CustomerInquiryDetailRspDTO.builder()
                .inquiryId(inquiry.getInquiryId())
                .authorId(inquiry.getAuthorId())
                .categoryId(inquiry.getCategory().getCategoryId())
                .categoryName(inquiry.getCategory().getCategoryName())
                .title(inquiry.getTitle())
                .content(inquiry.getContent())
                .createdAt(inquiry.getCreatedAt())
                .reply(toReplyDTO(inquiry.getCustomerReply()))
                .build();
    }

    // CustomerReply -> CustomerReplyRspDTO (문의사항 답변)
    public CustomerInquiryReplyRspDTO toReplyDTO(CustomerReply reply){

        return Optional.ofNullable(reply)
                .map( r -> CustomerInquiryReplyRspDTO.builder()
                            .content(r.getContent())
                            .createdAt(r.getCreatedAt())
                            .build())
                .orElse(null);
    }
}
