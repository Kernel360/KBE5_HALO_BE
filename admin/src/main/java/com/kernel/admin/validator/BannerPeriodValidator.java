package com.kernel.admin.validator;

import com.kernel.admin.service.dto.request.AdminBannerReqDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class BannerPeriodValidator implements ConstraintValidator<ValidBannerPeriod, AdminBannerReqDTO> {

    @Override
    public boolean isValid(AdminBannerReqDTO dto, ConstraintValidatorContext context) {
        if (dto.getStartAt() == null || dto.getEndAt() == null) {
            return true; // @NotNull 어노테이션이 이미 적용되어 있으므로, 여기서는 null 체크만 수행합니다.
        }

        return dto.getEndAt().isAfter(dto.getStartAt());
    }
}
