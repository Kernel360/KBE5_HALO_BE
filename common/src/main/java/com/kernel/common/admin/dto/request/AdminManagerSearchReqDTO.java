package com.kernel.common.admin.dto.request;

import com.kernel.common.global.enums.UserStatus;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class AdminManagerSearchReqDTO {

    // 매니저 이름
    private String userName;

    // 매니저 연락처
    private String phone;

    // 매니저 이메일
    private String email;

    // 매니저 상태
    private String status;

    // 매니저 평점 범위
    @Min(value = 0, message = "최소 평점은 0 이상이어야 합니다.")
    @Max(value = 5, message = "최소 평점은 5 이하이어야 합니다.")
    private BigDecimal minRating;

    @Min(value = 0, message = "최대 평점은 0 이상이어야 합니다.")
    @Max(value = 5, message = "최대 평점은 5 이하이어야 합니다.")
    private BigDecimal maxRating;

    // 생성자에서 유효성 검사
    public AdminManagerSearchReqDTO() {
        if (minRating != null && maxRating != null && minRating.compareTo(maxRating) > 0) {
            throw new IllegalArgumentException("최대 평점은 최소 평점보다 크거나 같아야 합니다.");
        }
    }

}
