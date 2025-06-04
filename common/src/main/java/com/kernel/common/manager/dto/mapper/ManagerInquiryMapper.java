package com.kernel.common.manager.dto.mapper;

import com.kernel.common.manager.dto.request.ManagerInquiryCreateReqDTO;
import com.kernel.common.manager.dto.response.ManagerInquiryRspDTO;
import com.kernel.common.manager.dto.response.ManagerInquirySummaryRspDTO;
import com.kernel.common.manager.entity.ManagerInquiry;
import com.kernel.common.manager.entity.ManagerReply;
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
        ManagerReply reply = managerInquiry.getManagerReply();
        return ManagerInquiryRspDTO.builder()
            .inquiryId(managerInquiry.getInquiryId())   // 게시글ID
            .authorId(managerInquiry.getAuthorId())     // 작성자ID(= 매니저ID)
            .title(managerInquiry.getTitle())           // 제목
            .content(managerInquiry.getContent())       // 내용
            .fileId(managerInquiry.getFileId())         // 파일첨부
            .createdAt(managerInquiry.getCreatedAt())   // 생성일시
            // 답변이 있을 경우에만 값 설정
            .answerId(reply != null ? reply.getAnswerId() : null)              // 답변ID
            .replyContent(reply != null ? reply.getContent() : null)           // 답변 내용
            .replyFileId(reply != null ? reply.getFileId() : null)             // 답변첨부파일ID
            .replyCreatedAt(reply != null ? reply.getCreatedAt() : null)       // 답변일시
            .build();
    }

    // Entity -> SummaryResponseDTO
    public ManagerInquirySummaryRspDTO toSummaryResponseDTO(ManagerInquiry managerInquiry) {
        return ManagerInquirySummaryRspDTO.builder()
            .inquiryId(managerInquiry.getInquiryId())
            .title(managerInquiry.getTitle())
            .createdAt(managerInquiry.getCreatedAt())
            .isReplied(managerInquiry.getManagerReply() != null)
            .build();
    }
}
