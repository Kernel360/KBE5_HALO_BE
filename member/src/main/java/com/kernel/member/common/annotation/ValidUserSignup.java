package com.kernel.member.common.annotation;

import com.kernel.member.common.validator.ValidUserSignupValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidUserSignupValidator.class)
public @interface ValidUserSignup {
    // 회원가입시 password, providerId 필수 여부 검증 어노테이션
    String message() default "가입 정보가 유효하지 않습니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
