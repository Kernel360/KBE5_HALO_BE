package com.kernel.common.customer.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerInquiryUpdateReqDTO {

    // 문의사항ID
    @NotNull
    private Long inquiryId;

    // 작성자ID
    @NotNull
    private Long authorId;

    // 카테고리ID
    @NotNull(message = "카테고리를 선택해주세요.")
    private Long categoryId;

    // 제목
    @NotBlank(message = "제목을 입력해주세요.")
    @Size(max = 50, message = "제목은 최대 50자까지 입력할 수 있습니다.")
    private String title;

    //내용
    @NotBlank(message = "내용을 입력해주세요.")
    @Size(max = 5000, message = "내용은 최대 5000자까지 입력할 수 있습니다.")
    private String content;

    //TODO 첨부파일 추가
    //private Long fileId;

}
