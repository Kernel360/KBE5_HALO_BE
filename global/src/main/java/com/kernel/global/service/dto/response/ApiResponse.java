package com.kernel.global.service.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "API 응답 DTO")
public class ApiResponse<T> {

     @Schema(description = "요청 성공 여부", example = "true")
     private boolean success;

     @Schema(description = "응답 메시지", example = "요청이 성공적으로 처리되었습니다.")
     private String message;

     @Schema(description = "응답 데이터")
     private T body;
}