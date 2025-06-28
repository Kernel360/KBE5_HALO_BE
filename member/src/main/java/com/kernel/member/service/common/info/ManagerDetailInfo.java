package com.kernel.member.service.common.info;

import com.kernel.global.domain.entity.File;
import com.kernel.member.common.enums.ContractStatus;
import com.kernel.member.domain.entity.Manager;
import com.kernel.reservation.domain.entity.ServiceCategory;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ManagerDetailInfo {

    // 특기
    private ServiceCategory specialty;

    // 한 줄 소개
    private String bio;

    // 프로필 URL
    private String profileImageFilePath;

    // 서류 파일 ID
    private String filePaths;

    // 계약상태
    private ContractStatus contractStatus;

    // 계약 일시
    private LocalDateTime contractDate;

    // Manager -> ManagerDetailInfo
    public static ManagerDetailInfo fromEntity(Manager manager) {
        return ManagerDetailInfo.builder()
                .specialty(manager.getSpecialty())
                .bio(manager.getBio())
                .profileImageFilePath(manager.getProfileImageFileId() != null ? manager.getProfileImageFileId().getFilePathsJson() : null)
                .filePaths(manager.getFileId() != null ? manager.getFileId().getFilePathsJson() : null)
                .contractStatus(manager.getContractStatus())
                .contractDate(manager.getContractDate())
                .build();
    }
}
