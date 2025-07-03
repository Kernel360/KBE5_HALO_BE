package com.kernel.member.service.common.info;

import com.kernel.global.common.enums.UserStatus;
import com.kernel.member.common.enums.ContractStatus;
import com.kernel.member.common.enums.Gender;
import com.kernel.member.service.response.AvailableTimeRspDTO;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AdminManagerDetailInfo {

    private Long userId;
    private String userName;
    private String phone;
    private String email;
    private UserStatus status;
    private LocalDate birthDate;
    private Gender gender;
    private String roadAddress;
    private String detailAddress;
    private String bio;
    private Long profileImageFileId;
    private Long fileId; // 첨부파일 ID
    private ContractStatus contractStatus;
    private LocalDateTime contractDate;
    private BigDecimal averageRating;
    private Integer reservationCount;
    private Integer reviewCount;
    private LocalDateTime terminatedAt;
    private String reason;
    private LocalDateTime requestAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<AvailableTimeRspDTO> availableTimes;





}