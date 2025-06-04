package com.kernel.common.manager.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ManagerInquiryUpdateReqDTO {

    // 제목
    @NotBlank(message = "제목은 필수입니다.")
    @Size(max = 50, message = "제목은 최대 50자까지 입력할 수 있습니다")
    private String title;

    // 내용
    @NotBlank(message = "내용은 필수입니다.")
    @Size(max = 5000, message = "내용은 최대 5000자까지 입력할 수 있습니다")
    private String content;

    // 첨부파일
    private Long fileId;
}