package com.kernel.inquiry.service.dto.request;

import com.kernel.inquiry.domain.entity.Inquiry;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class InquiryUpdateReqDTO {

    // 문의
    @NotNull(message = "수정할 문의사항을 선택해주세요.")
    private Long inquiryId;

    // 제목
    @Size(max = 50, message = "제목은 최대 50자까지 입력할 수 있습니다.")
    private String title;

    // 내용
    @Size(max = 5000, message = "내용은 최대 5000자까지 입력할 수 있습니다.")
    private String content;

    // 첨부파일
    private Long fileId;

    // 카테고리
    private Enum<?> category;

    // reqDTO to Entity Mapping
    public Inquiry toEntity() {
        return Inquiry.builder()
                .inquiryId(inquiryId)
                .title(title)
                .content(content)
                .fileId(fileId)
                .category(category)
                .build();
    }
}
