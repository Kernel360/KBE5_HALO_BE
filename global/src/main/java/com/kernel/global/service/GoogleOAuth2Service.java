package com.kernel.global.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class GoogleOAuth2Service {

    private final WebClient webClient = WebClient.create("https://oauth2.googleapis.com");

    /**
     * Google OAuth2 토큰 revoke
     * Google 로그인 / 회원가입 실패시 google 엑세스 토큰 무효화
     * @param accessToken 사용자 accessToken
     */
    public void revokeToken(String accessToken) {
        try {
            webClient.post()
                    .uri(uriBuilder -> uriBuilder.path("/revoke")
                            .queryParam("token", accessToken)
                            .build())
                    .retrieve()
                    .bodyToMono(Void.class)
                    .onErrorResume(e -> {
                        log.error("[Google Revoke] 실패: {}", e.getMessage());
                        return Mono.empty();
                    })
                    .block();

            log.info("[Google Revoke] 완료: token={}", accessToken);
        } catch (Exception e) {
            log.error("[Google Revoke] 요청 실패", e);
        }
    }

    /**
     * Google OAuth2 계정 연결 해제 (unlink)
     * Google 계정에서 서비스 자체를 연결 해제
     * @param accessToken 사용자 accessToken
     */
    public void unlinkAccount(String accessToken) {
        try {
            webClient.post()
                    .uri(uriBuilder -> uriBuilder.path("/revoke")
                            .queryParam("token", accessToken)
                            .build())
                    .retrieve()
                    .bodyToMono(Void.class)
                    .onErrorResume(e -> {
                        log.error("[Google Unlink] 실패: {}", e.getMessage());
                        return Mono.empty();
                    })
                    .block();

            log.info("[Google Unlink] 완료: token={}", accessToken);
        } catch (Exception e) {
            log.error("[Google Unlink] 요청 실패", e);
        }
    }
}
