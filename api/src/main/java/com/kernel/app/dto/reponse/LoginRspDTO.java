package com.kernel.app.dto.reponse;

import lombok.*;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRspDTO {

    private String accessToken;
    private String refreshToken;
    private UserLoginRspDTO user;
}
