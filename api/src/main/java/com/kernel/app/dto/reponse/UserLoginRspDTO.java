package com.kernel.app.dto.reponse;

import com.kernel.app.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRspDTO {

    private Long id;
    private String email;
    private String username;
    private UserType userType;
}
