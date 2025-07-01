package com.kernel.member.service.request;

import com.kernel.global.domain.entity.User;
import com.kernel.member.domain.entity.Customer;
import com.kernel.member.domain.entity.UserInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "고객 요청 DTO")
public class CustomerReqDTO {

    @Schema(description = "포인트", example = "0", required = true)
    @Min(value = 0, message = "포인트는 0 이상이어야 합니다.")
    @Builder.Default
    private Integer point = 0;

    // CustomerReqDTO -> Customer
    public Customer toEntity(User user, UserInfo info) {
        return Customer.builder()
                .user(user)
                .point(point)
                .build();
    }
}