package com.kernel.member.service.request;

import com.kernel.global.common.enums.UserStatus;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@ToString
@Schema(description = "관리자 매니저 조회 요청 DTO")
public class AdminManagerSearchReqDTO {

    @Schema(description = "매니저 이름")
    private String userName;

    @Schema(description = "매니저 연락처")
    private String phone;

    @Schema(description = "매니저 이메일")
    private String email;

    @Schema(description = "매니저 상태")
    private UserStatus status;

    @Schema(description = "제외시킬 매니저 상태 목록")
    private List<UserStatus> excludeStatus;

    @Schema(description = "최소 평점", example = "0")
    @Min(value = 0, message = "최소 평점은 0 이상이어야 합니다.")
    @Max(value = 5, message = "최대 평점은 5 이하이어야 합니다.")
    private BigDecimal minRating;

    @Schema(description = "최대 평점", example = "5")
    @Min(value = 0, message = "최소 평점은 0 이상이어야 합니다.")
    @Max(value = 5, message = "최대 평점은 5 이하이어야 합니다.")
    private BigDecimal maxRating;

    public AdminManagerSearchReqDTO() {
        if (minRating != null && maxRating != null && minRating.compareTo(maxRating) > 0) {
            throw new IllegalArgumentException("최대 평점은 최소 평점보다 크거나 같아야 합니다.");
        }
    }
}