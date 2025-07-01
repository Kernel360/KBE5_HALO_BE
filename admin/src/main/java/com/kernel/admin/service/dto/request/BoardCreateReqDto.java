package com.kernel.admin.service.dto.request;

import com.kernel.admin.common.enums.BoardType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BoardCreateReqDto {

    @NotNull(message = "타입을 선택해주세요.")
    private BoardType boardType;

    @NotBlank(message = "제목을 입력해주세요.")
    @Size(min = 5, max = 30, message = "제목은 5자 이상 30자 이하까지 작성 가능합니다.")
    private String title;

    @NotBlank(message = "내용은 공백일 수 없습니다.")
    @Size(min = 10, max = 5000, message = "내용은 10자 이상 5000자 이하까지 작성 가능합니다.")
    private String content;

    private Long fileId;
}
