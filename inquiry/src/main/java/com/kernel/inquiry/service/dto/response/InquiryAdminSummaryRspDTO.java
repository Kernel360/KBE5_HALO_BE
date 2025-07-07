package com.kernel.inquiry.service.dto.response;

import com.kernel.inquiry.common.utils.InquiryCategoryUtils;
import com.kernel.inquiry.service.info.InquiryAdminSummaryInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
@Schema(description = "문의 요약 응답 DTO")
public class InquiryAdminSummaryRspDTO {

    @Schema(description = "게시글 ID", example = "123", required = true)
    private Long inquiryId;

    @Schema(description = "문의 유형 이름", example = "GENERAL", required = true)
    private String categoryName;

    @Schema(description = "제목", example = "문의 제목 예시", required = true)
    private String title;

    @Schema(description = "작성일시", example = "2023-01-01T12:00:00", required = true)
    private LocalDateTime createdAt;

    @Schema(description = "작성자이름", example = "홍길동", required = true)
    private String userName;

    @Schema(description = "작성자 타입", example = "수요자", required = true)
    private String authorType;

    @Schema(description = "답변 여부", example = "true", required = true)
    private Boolean isReplied;

    public static Page<InquiryAdminSummaryRspDTO> fromInfo(Page<InquiryAdminSummaryInfo> inquirySummaryInfo) {
        return inquirySummaryInfo.map(info -> InquiryAdminSummaryRspDTO.builder()
                .inquiryId(info.getInquiryId())
                .categoryName(InquiryCategoryUtils.getCategoryLabel(info.getCategoryName(), info.getAuthorType()))
                .title(info.getTitle())
                .createdAt(info.getCreatedAt())
                .userName(info.getUserName())
                .authorType(info.getAuthorType().getLabel())
                .isReplied(info.getIsReplied())
                .build());
    }
}