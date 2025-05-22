package com.kernel.customer.dto.mapper;


import com.kernel.customer.dto.request.InquiryRequestDTO;
import com.kernel.customer.dto.response.InquiryResponseDTO;
import com.kernel.customer.entity.Inquiry;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class InquiryMapper {

    // RequestDTO -> Entity
    public Inquiry toEntity(InquiryRequestDTO dto){

        return Inquiry.builder()
                .authorId(dto.getAuthorId())
                .title(dto.getTitle())
                .content(dto.getContent())
                .createdAt(LocalDateTime.now())
                .build();
    }

    // Entity -> ResponseDTO
    public InquiryResponseDTO toResponseDTO(Inquiry inquiry){
        return InquiryResponseDTO.builder()
                .id(inquiry.getInquiryId())
                .authorId(inquiry.getAuthorId())
                .title(inquiry.getTitle())
                .content(inquiry.getContent())
                .createdAt(inquiry.getCreatedAt())
                .build();
    }

    // Entity -> ResponseDTOList
    public List<InquiryResponseDTO> toResponseDTOList(List<Inquiry> inquiries) {
        return inquiries.stream()
                .map(inquiry -> {
                    InquiryResponseDTO dto = toResponseDTO(inquiry);
                    return dto;
                })
                .collect(Collectors.toList());
    }

}
