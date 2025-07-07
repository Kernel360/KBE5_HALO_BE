package com.kernel.inquiry.service.dto.response;

import com.kernel.inquiry.common.utils.InquiryCategoryUtils;
import com.kernel.inquiry.service.info.InquiryAdminDetailInfo;
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
    private Long authorId;

    @Schema(description = "작성자 이름", example = "홍길동", required = true)
    private String authorName;

    @Schema(description = "작성자 타입", example = "CUSTOMER", required = true)
    private String authorType;

    @Schema(description = "작성자 핸드폰번호", example = "010-1111-1111", required = true)
    private String phone;

    @Schema(description = "작성자 이메일", example = "user01@gmail.com", required = true)
    private String email;

    @Schema(description = "첨부파일 ID", example = "123", required = false)
    private Long fileId;

    @Schema(description = "문의사항 작성 일시", example = "2023-01-01T12:00:00", required = true)
    private LocalDateTime createdAt;

    @Schema(description = "답변DTO", required = false)
    private ReplyAdminDetailRspDTO replyDetail;


    public static InquiryAdminDetailRspDTO fromEntity(InquiryAdminDetailInfo detailInfo) {
        return InquiryAdminDetailRspDTO.builder()
                .inquiryId(detailInfo.getInquiryId())
                .categoryName(InquiryCategoryUtils.getCategoryLabel(detailInfo.getCategoryName(), detailInfo.getAuthorType()))
                .title(detailInfo.getTitle())
                .content(detailInfo.getContent())
                .authorId(detailInfo.getAuthorId())
                .authorName(detailInfo.getAuthorName())
                .phone(detailInfo.getPhone())
                .email(detailInfo.getEmail())
                .authorType(detailInfo.getAuthorType().getLabel())
                .fileId(detailInfo.getFileId())
                .createdAt(detailInfo.getCreatedAt())
                .replyDetail(
                        detailInfo.getReplyDetailInfo() != null ?
                        ReplyAdminDetailRspDTO.fromInfo(detailInfo.getReplyDetailInfo()) : null
                )
                .build();
    }
}