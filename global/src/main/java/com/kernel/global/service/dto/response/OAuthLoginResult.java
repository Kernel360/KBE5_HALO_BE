package com.kernel.global.service.dto.response;

import jakarta.servlet.http.Cookie;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OAuthLoginResult {

    private final OAuthLoginRspDTO response;
    private final String accessToken;
    private final Cookie refreshToken;

    public static OAuthLoginResult ofResult(OAuthLoginRspDTO response, String accessToken, Cookie refreshToken) {
        return OAuthLoginResult.builder()
                .response(response)
                .accessToken(accessToken != null ? accessToken : "")
                .refreshToken(refreshToken != null ? refreshToken : null)
                .build();
    }
}
