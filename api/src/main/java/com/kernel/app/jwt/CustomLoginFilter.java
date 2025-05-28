package com.kernel.app.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kernel.app.dto.CustomerUserDetails;
import com.kernel.app.entity.Refresh;
import com.kernel.app.repository.RefreshRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

@RequiredArgsConstructor
public class CustomLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RefreshRepository refreshRepository;
    private final JwtProperties jwtProperties;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String phone;
        String password;

        //content-type이 Json인지 확인
        if(request.getContentType() != null && request.getContentType().contains("application/json")){
            try{
                // JSON 요청 처리
                Map<String, String> requestMap = objectMapper.readValue(request.getInputStream(), Map.class);
                phone = requestMap.get("phone");
                password = requestMap.get("password");

                // username대신 phone으로 요청 보냈을 경우
                if(phone == null){
                    phone = requestMap.get("phone");
                }

            }catch (IOException e){
                throw new AuthenticationServiceException("Failed to parse json content", e);
            }
        }else{
            // 요청이 form_data인 경우
            phone = obtainUsername(request);
            password = obtainPassword(request);
        }

        //url에서 사용자 타입 추출
        String requestUri = request.getRequestURI();
        String userType = extractUserTypeFromUri(requestUri);

        if (userType == null) {
            throw new AuthenticationServiceException("Invalid login URL");
        }

        // username에 userType을 prefix로 추가
        String prefixedUsername = userType + ":" + phone;


        // 스프링 시큐리티에서 username과 password를 검증하기 위에서는 token에 담아야함
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(prefixedUsername, password, null);

        //token에 담은 검증을 위한 AuthenticationManager로 전달
        return authenticationManager.authenticate(authToken);
    }

    //로그인 성공시 실행하는 메소드 (JWT 발급)
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
        //유저 정보
        CustomerUserDetails customUserDetails = (CustomerUserDetails) authentication.getPrincipal();

        String phone = customUserDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        //토큰 생성
        String access = jwtTokenProvider.createToken("access", phone, role, jwtProperties.accessTokenValiditySeconds());
        String refresh = jwtTokenProvider.createToken("refresh", phone, role, jwtProperties.refreshTokenValiditySeconds());

        //Refresh 토큰 저장
        addRefreshEntity(phone, refresh, jwtProperties.refreshTokenValiditySeconds());

        //응답 설정
        response.setHeader("Authorization", "Bearer " + access);
        response.addCookie(createCookie("refresh", refresh));
        response.setStatus(HttpStatus.OK.value());
    }

    //로그인 실패시 실행하는 메소드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(401);
    }

    //쿠키생성
    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24*60*60); //생명주기 24시간
        //cookie.setSecure(true);   // true 설정 시, HTTPS에서만 전송됨(http x) 테스트시 용이하게 주석처리. 배포시 설정해야함
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }

    private void addRefreshEntity(String phone, String refresh, Long expiredMs) {

        Date expiration = new Date(System.currentTimeMillis() + expiredMs);

        refreshRepository.save(Refresh.builder()
                                .phone(phone)
                                .refreshToken(refresh)
                                .expiration(expiration.toString())
                                .build());
    }


    private String extractUserTypeFromUri(String uri) {
        // URI 예시: /api/customers/login, /api/managers/login, /api/admin/login
        String userType = null;

        if (uri.contains("/customers")) {
            userType =  "customer";
        } else if (uri.contains("/managers")) {
            userType = "manager";
        } else if (uri.contains("/admin")) {
            userType = "admin";
        }
        return userType;
    }
}
