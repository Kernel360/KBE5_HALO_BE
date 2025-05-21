package com.kernel360.Halo.domain.customer.mapper;

import com.kernel360.Halo.domain.customer.entity.Inquiry;
import com.kernel360.Halo.web.customer.dto.request.InquiryRequestDTO;
import com.kernel360.Halo.web.customer.dto.response.InquiryResponseDTO;
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
                .id(inquiry.getId())
                .authorId(inquiry.getAuthorId())
                .title(inquiry.getTitle())
                .content(inquiry.getContent())
                .createdAt(inquiry.getCreatedAt())
                .deleted(inquiry.isDeleted())
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
