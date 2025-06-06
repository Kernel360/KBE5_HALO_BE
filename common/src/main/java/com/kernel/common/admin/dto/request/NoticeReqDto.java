package com.kernel.common.admin.dto.request;

import com.kernel.common.global.enums.NoticeType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class NoticeReqDto {

    private NoticeType noticeType;

    @NotBlank(message = "제목은 공백일 수 없습니다.")
    @Size(max = 30, message = "제목은 30자 이내여야 합니다.")
    private String title;

    @NotBlank(message = "내용은 공백일 수 없습니다.")
    @Size(max = 500, message = "내용은 500자 이내여야 합니다.")
    private String content;

}
