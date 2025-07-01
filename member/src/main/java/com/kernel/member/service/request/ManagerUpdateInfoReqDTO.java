package com.kernel.member.service.request;

import com.kernel.sharedDomain.domain.entity.ServiceCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "매니저 정보 수정 요청 DTO")
public class ManagerUpdateInfoReqDTO {

    @Schema(description = "특기", example = "HAIR_CUTTING", required = true)
    private ServiceCategory specialty;

    @Schema(description = "한 줄 소개", example = "전문 헤어 디자이너입니다.", required = true, maxLength = 50)
    @Size(max = 50, message = "한 줄 소개는 최대 50자까지 입력 가능합니다.")
    private String bio;
}