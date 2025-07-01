package com.kernel.member.service.common.info;

import com.kernel.global.common.enums.UserStatus;
import com.kernel.member.common.enums.ContractStatus;
import com.kernel.member.common.enums.Gender;
import com.kernel.member.service.response.AvailableTimeRspDTO;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AdminManagerDetailInfo {

    private Long managerId;
    private String userName;
    private String phone;
    private String email;
    private UserStatus status;
    private String birthDate;
    private Gender gender;
    private String roadAddress;
    private String detailAddress;
    private String bio;
    private Long profileImageId;
    private Long fileId; // 첨부파일 ID
    private ContractStatus contractStatus;
    private LocalDateTime contractDate;
    private Double rating;
    private Integer reservationCount;
    private Integer reviewCount;
    private LocalDateTime terminatedAt;
    private String reason;
    private LocalDateTime requestAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<AvailableTimeRspDTO> availableTimes;





}