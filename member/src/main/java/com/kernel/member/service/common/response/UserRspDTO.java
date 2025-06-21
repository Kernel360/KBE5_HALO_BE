package com.kernel.member.service.common.response;

import com.kernel.global.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRspDTO {

    // 핸드폰 번호
    private String phone;

    // 이메일
    private String email;

    // 이름
    private String userName;

    // User -> UserRspDTO
    public static UserRspDTO fromEntity(User user) {
        return UserRspDTO.builder()
                .phone(user.getPhone())
                .email(user.getEmail())
                .userName(user.getUserName())
                .build();
    }

}
