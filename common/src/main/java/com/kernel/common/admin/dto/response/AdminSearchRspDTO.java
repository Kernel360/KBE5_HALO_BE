package com.kernel.common.admin.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AdminSearchRspDTO {

    // 관리자 ID
    private Long adminId;

    // 관리자 이름
    private String userName;

    // 관리자 전화번호
    private String phone;

    // 관리자 이메일
    private String email;

    // 관리자 상태
    private String status;

    // 관리자 생성일
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

}
