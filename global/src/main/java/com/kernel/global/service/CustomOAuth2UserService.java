package com.kernel.global.service;

import com.kernel.global.service.dto.response.OAuthLoginResult;

public interface CustomOAuth2UserService {
    OAuthLoginResult login(String code, String role);
}

