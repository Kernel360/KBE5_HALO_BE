package com.kernel.common.customer.dto.mapper;


import com.kernel.common.customer.dto.response.CustomerInquiryDetailRspDTO;
import com.kernel.common.customer.dto.response.CustomerInquiryReplyRspDTO;
import com.kernel.common.customer.dto.response.CustomerInquiryRspDTO;
import com.kernel.common.customer.entity.CustomerInquiry;
import com.kernel.common.customer.entity.CustomerReply;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CustomerInquiryMapper {

    // RequestDTO -> Entity
  /*  public Inquiry toEntity(InquiryRequestDTO dto){

        return Inquiry.builder()
                .authorId(dto.getAuthorId())
                .title(dto.getTitle())
                .content(dto.getContent())
                .createdAt(LocalDateTime.now())
                .build();
    }
*/
    // CustomerInquiry -> CustomerInquiryRspDTO
    public CustomerInquiryRspDTO toRspDTO(CustomerInquiry inquiry){
        return CustomerInquiryRspDTO.builder()
                .inquiryId(inquiry.getInquiryId())
                .title(inquiry.getTitle())
                .createdAt(inquiry.getCreatedAt())
                .isReplied(inquiry.getCustomerReply() != null)
                .build();
    }

    // CustomerInquiry -> CustomerInquiryDetailRspDTO
    public CustomerInquiryDetailRspDTO toDetailRspDTO(CustomerInquiry inquiry){
        return CustomerInquiryDetailRspDTO.builder()
                .inquiryId(inquiry.getInquiryId())
                .categoryId(inquiry.getCategory().getCategoryId())
                .categoryName(inquiry.getCategory().getCategoryName())
                .title(inquiry.getTitle())
                .content(inquiry.getContent())
                .createdAt(inquiry.getCreatedAt())
                .reply(toReplyDTO(inquiry.getCustomerReply()))
                .build();
    }

    // CustomerReply -> CustomerReplyRspDTO
    public CustomerInquiryReplyRspDTO toReplyDTO(CustomerReply reply){

        return Optional.ofNullable(reply)
                .map( r -> CustomerInquiryReplyRspDTO.builder()
                            .content(r.getContent())
                            .createdAt(r.getCreatedAt())
                            .build())
                .orElse(null);
    }


    // Entity -> ResponseDTOList
    public List<CustomerInquiryRspDTO> toResponseDTOList(List<CustomerInquiry> inquiries) {
        return inquiries.stream()
                .map(inquiry -> {
                    CustomerInquiryRspDTO dto = toRspDTO(inquiry);
                    return dto;
                })
                .collect(Collectors.toList());
    }

}
