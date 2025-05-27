package com.kernel.common.customer.dto.mapper;


import com.kernel.common.customer.dto.response.InquiryRspDTO;
import com.kernel.common.customer.entity.Inquiry;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class InquiryMapper {

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
    // Entity -> ResponseDTO
    public InquiryRspDTO toResponseDTO(Inquiry inquiry){
        return InquiryRspDTO.builder()
                .id(inquiry.getInquiryId())
                .authorId(inquiry.getAuthorId())
                .title(inquiry.getTitle())
                .content(inquiry.getContent())
                .build();
    }

    // Entity -> ResponseDTOList
    public List<InquiryRspDTO> toResponseDTOList(List<Inquiry> inquiries) {
        return inquiries.stream()
                .map(inquiry -> {
                    InquiryRspDTO dto = toResponseDTO(inquiry);
                    return dto;
                })
                .collect(Collectors.toList());
    }

}
