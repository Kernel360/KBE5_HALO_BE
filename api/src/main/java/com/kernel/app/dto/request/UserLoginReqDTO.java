package com.kernel.app.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserLoginReqDTO {

    @NotBlank
    private String email;
    @NotBlank
    private String password;

}
