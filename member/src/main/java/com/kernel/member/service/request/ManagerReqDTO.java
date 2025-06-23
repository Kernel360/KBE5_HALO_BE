package com.kernel.member.service.request;

import com.kernel.global.domain.entity.File;
import com.kernel.global.domain.entity.User;
import com.kernel.member.domain.entity.Manager;
import com.kernel.reservation.domain.entity.ServiceCategory;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ManagerReqDTO {

    // 특기
    @NotNull(message = "특기는 필수 입력입니다.")
    private ServiceCategory specialty;

    // 한 줄 소개
    @NotBlank(message = "한 줄 소개는 필수 입력입니다.")
    @Size(max = 50, message = "한 줄 소개는 최대 50자까지 입력 가능합니다.")
    private String bio;

    // 서류 파일 ID
    @NotNull(message = "서류 파일 ID는 필수 입력입니다.")
    private Long fileId;

    // 프로필 사진 ID
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
