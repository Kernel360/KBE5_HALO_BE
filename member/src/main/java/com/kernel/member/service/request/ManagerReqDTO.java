package com.kernel.member.service.request;

import com.kernel.global.domain.entity.File;
import com.kernel.global.domain.entity.User;
import com.kernel.member.domain.entity.Manager;

import com.kernel.sharedDomain.domain.entity.ServiceCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "매니저 요청 DTO")
public class ManagerReqDTO {

    @Schema(description = "특기", example = "HAIR_CUTTING", required = true)
    @NotNull(message = "특기는 필수 입력입니다.")
    private ServiceCategory specialty;

    @Schema(description = "한 줄 소개", example = "전문 헤어 디자이너입니다.", required = true, maxLength = 50)
    @NotBlank(message = "한 줄 소개는 필수 입력입니다.")
    @Size(max = 50, message = "한 줄 소개는 최대 50자까지 입력 가능합니다.")
    private String bio;

    @Schema(description = "서류 파일 ID", example = "123", required = true)
    @NotNull(message = "서류 파일 ID는 필수 입력입니다.")
    private Long fileId;

    @Schema(description = "프로필 사진 ID", example = "456", required = true)
    @NotNull(message = "프로필 사진 ID는 필수 입력입니다.")
    private Long profileImageFileId;

    // ManagerReqDTO -> Manager
    public static Manager toEntity(ManagerReqDTO reqDTO, User user, File file, File profileImageFile) {
        return Manager.builder()
                .user(user)
                .specialty(reqDTO.getSpecialty())
                .bio(reqDTO.getBio())
                .fileId(file)
                .profileImageFileId(profileImageFile)
                .build();
    }
}
