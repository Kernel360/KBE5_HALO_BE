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

    // 행위 구분 (update, delete)
    @NotBlank
    private String action;

    // 매니저 게시글ID
    @NotNull
    private Long inquiryId;

    // 작성자ID(= 매니저ID)
    @NotNull
    private Long authorId;

    // 제목
    @NotBlank(message = "제목은 필수입니다.")
    @Size(max = 50, message = "제목은 최대 50자까지 입력할 수 있습니다")
    private String title;

    // 내용
    @NotBlank(message = "내용은 필수입니다.")
    @Size(max = 5000, message = "내용은 최대 5000자까지 입력할 수 있습니다")
    private String content;

    // 첨부파일
    // TODO: 첨부파일 추후 작업 예정
//    private Long fileId;
}