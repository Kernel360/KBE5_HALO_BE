package com.kernel.member.service.request;

import com.kernel.reservation.domain.entity.ServiceCategory;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ManagerUpdateInfoReqDTO {

    // 특기
    private ServiceCategory specialty;

    // 한 줄 소개
    @Size(max = 50, message = "한 줄 소개는 최대 100자까지 입력 가능합니다.")
    private String bio;
}
