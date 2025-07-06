package com.kernel.inquiry.service.dto.response;

import com.kernel.global.domain.entity.User;
import com.kernel.inquiry.common.enums.AuthorType;
import com.kernel.inquiry.common.utils.InquiryCategoryUtils;
import com.kernel.inquiry.domain.entity.Inquiry;
import com.kernel.inquiry.domain.entity.Reply;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
@Schema(description = "관리자 문의 상세 응답 DTO")
public class InquiryAdminDetailRspDTO {

    @Schema(description = "게시글 ID", example = "1", required = true)
    private Long inquiryId;

    @Schema(description = "게시글 카테고리", example = "환불문의", required = true)
    private String categoryName;

    @Schema(description = "게시글 제목", example = "제목", required = true)
    private String title;

    @Schema(description = "게시글 내용", example = "문의 내용 예시", required = true)
    private String content;

    @Schema(description = "작성자ID", example = "1", required = true)
    private Long userId;

    @Schema(description = "작성자 이름", example = "홍길동", required = true)
    private String userName;

    @Schema(description = "작성자 타입", example = "CUSOTMER", required = true)
    private AuthorType authorType;

    @Schema(description = "문의사항 답변", required = false)
    private ReplyRspDTO reply;

    @Schema(description = "첨부파일 ID", example = "123", required = false)
    private Long fileId;

    @Schema(description = "문의사항 작성 일시", example = "2023-01-01T12:00:00", required = false)
    private LocalDateTime createdAt;


    public static InquiryAdminDetailRspDTO fromEntity(Inquiry inquiry, Reply reply, User author) {
        return InquiryAdminDetailRspDTO.builder()
                .inquiryId(inquiry.getInquiryId())
                .categoryName(InquiryCategoryUtils.getCategoryLabel(inquiry.getCategoryName(), inquiry.getAuthorType()))
                .title(inquiry.getTitle())
                .content(inquiry.getContent())
                .createdAt(inquiry.getCreatedAt())
                .userId(author.getUserId())
                .authorType(AuthorType.fromUserRole(author.getRole()))
                .reply(reply != null ? ReplyRspDTO.fromEntity(reply) : null)
                .build();
    }
}