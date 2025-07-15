package com.kernel.member.common.validator;

import com.kernel.member.common.annotation.ValidUserSignup;
import com.kernel.member.service.common.request.UserSignupReqDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidUserSignupValidator implements ConstraintValidator<ValidUserSignup, UserSignupReqDTO> {
    @Override
    public boolean isValid(UserSignupReqDTO dto, ConstraintValidatorContext context) {
        // provider 값이 존재하면 소셜 로그인 사용자로 간주
        boolean isSocial = dto.getProvider() != null;
        boolean valid;

        if (isSocial) {
            // 소셜 로그인인 경우: providerId는 필수이며 password는 없어야 함
            valid = dto.getProviderId() != null && !dto.getProviderId().isBlank()
                    && (dto.getPassword() == null || dto.getPassword().isBlank());
        } else {
            // 일반 회원가입인 경우: password는 필수
            valid = dto.getPassword() != null && !dto.getPassword().isBlank();
        }

        if (!valid) {
            // 유효하지 않은 경우 기본 메시지 비활성화
            context.disableDefaultConstraintViolation();

            if (isSocial) {
                // 소셜 로그인 조건이 유효하지 않을 때 메시지 설정
                context.buildConstraintViolationWithTemplate("소셜 로그인 정보가 유효하지 않습니다.")
                        .addConstraintViolation();
            } else {
                // 일반 로그인 조건이 유효하지 않을 때 메시지 설정
                context.buildConstraintViolationWithTemplate("일반 로그인 정보가 유효하지 않습니다.")
                        .addConstraintViolation();
            }
        }

        return valid;
    }
}
