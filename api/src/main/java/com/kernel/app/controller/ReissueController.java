package com.kernel.app.controller;

import com.kernel.app.entity.Refresh;
import com.kernel.app.jwt.JwtTokenProvider;
import com.kernel.app.repository.RefreshRepository;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReissueController {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshRepository refreshRepository;

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {

        //get refresh token
        String refresh = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {

            if (cookie.getName().equals("refresh")) {

                refresh = cookie.getValue();
            }
        }

        if (refresh == null) {

            //response status code
            return new ResponseEntity<>("refresh token null", HttpStatus.BAD_REQUEST);
        }

        //expired check
        try {
            jwtTokenProvider.isExpired(refresh);
        } catch (ExpiredJwtException e) {

            //response status code
            return new ResponseEntity<>("refresh token expired", HttpStatus.BAD_REQUEST);
        }

        // 토큰이 refresh인지 확인 (발급시 페이로드에 명시)
        String category = jwtTokenProvider.getCategory(refresh);

        if (!category.equals("refresh")) {

            //response status code
            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
        }

        String username = jwtTokenProvider.getUsername(refresh);
        String role = jwtTokenProvider.getRole(refresh);

        //make new JWT
        String newAccess = jwtTokenProvider.createToken("access", username, role, 600000L);
        String newRefresh = jwtTokenProvider.createToken("refresh", username, role, 86400000L);

        //Refresh 토큰 저장 DB에 기존의 Refresh 토큰 삭제 후 새 Refresh 토큰 저장
        refreshRepository.deleteByRefreshToken(refresh);
        addRefreshEntity(username, newRefresh, 86400000L);

        //response
        response.setHeader("Authorization", "Bearer " + newAccess);
        response.addCookie(createCookie("refresh", newRefresh));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24*60*60);
        //cookie.setSecure(true);   // true 설정 시, HTTPS에서만 전송됨(http x) 테스트시 용이하게 주석처리. 배포시 설정해야함
        cookie.setPath("/");  // 쿠키의 적용 경로 설정. /로 설정하면 모든 경로에서 쿠키가 유효. 운영에선 보통 /로 지정해서 어디서든 쿠키 접근 가능하도록 하는 것이 일반적
        cookie.setHttpOnly(true);

        return cookie;
    }

    private void addRefreshEntity(String username, String refresh, Long expiredMs) {

        Date expiration = new Date(System.currentTimeMillis() + expiredMs);

        refreshRepository.save(Refresh.builder()
                         .email(username)
                         .refreshToken(refresh)
                         .expiration(expiration.toString())
                         .build());
    }
}
