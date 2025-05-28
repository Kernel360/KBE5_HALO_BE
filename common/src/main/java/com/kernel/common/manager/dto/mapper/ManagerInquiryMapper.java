package com.kernel.common.manager.dto.mapper;

import com.kernel.common.manager.dto.reponse.ManagerInquiryRspDTO;
import com.kernel.common.manager.dto.reponse.ManagerInquirySummaryRspDTO;
import com.kernel.common.manager.dto.request.ManagerInquiryCreateReqDTO;
import com.kernel.common.manager.entity.ManagerInquiry;
import org.springframework.stereotype.Component;

@Component
public class ManagerInquiryMapper {

    // CreateRequestDTO -> Entity
    public ManagerInquiry toEntity(Long authorId, ManagerInquiryCreateReqDTO requestDTO) {
        return ManagerInquiry.builder()
            .authorId(authorId)                 // 작성자ID(= 매니저ID)
            .title(requestDTO.getTitle())       // 제목
            .content(requestDTO.getContent())   // 내용
            // TODO: 첨부파일 - 추후 작업 예정
            .build();
    }

    // Entity -> ResponseDTO
    public ManagerInquiryRspDTO toResponseDTO(ManagerInquiry managerInquiry) {
        return ManagerInquiryRspDTO.builder()
            .inquiryId(managerInquiry.getInquiryId())       // 게시글ID
            .authorId(managerInquiry.getAuthorId())         // 작성자ID(= 매니저ID)
            .title(managerInquiry.getTitle())               // 제목
            .content(managerInquiry.getContent())           // 내용
            // TODO: 첨부파일 - 추후 작업 예정
            .createdAt(managerInquiry.getCreatedAt())       // 생성일시
            .managerReply(managerInquiry.getManagerReply()) // 답변
            .build();
    }

    // Entity -> SummaryResponseDTO
    public ManagerInquirySummaryRspDTO toSummaryResponseDTO(ManagerInquiry managerInquiry) {
        return ManagerInquirySummaryRspDTO.builder()
            .inquiryId(managerInquiry.getInquiryId())
            .title(managerInquiry.getTitle())
            .content(managerInquiry.getContent())
            .createdAt(managerInquiry.getCreatedAt())
            .isReplied(managerInquiry.getManagerReply() != null)
            .build();
    }
}
