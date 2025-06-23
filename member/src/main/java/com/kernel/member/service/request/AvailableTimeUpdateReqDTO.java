package com.kernel.member.service.request;

import com.kernel.member.common.enums.DayOfWeek;
import com.kernel.member.domain.entity.AvailableTime;
import jakarta.validation.constraints.AssertTrue;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@Builder
public class AvailableTimeUpdateReqDTO {

    // 가능 요일
    private DayOfWeek dayOfWeek;

    // 가능 시간
    private LocalTime time;

    // 요일이 있을 때 시간은 필수
    @AssertTrue(message = "요일이 선택된 경우, 업무 시간은 필수입니다.")
    private boolean isTimeRequired() {
        return dayOfWeek != null ? time != null : true;
    }

    // AvailableTimeUpdateReqDTO -> AvailableTime로 매핑
    public static AvailableTime toEntity(AvailableTimeUpdateReqDTO availableTimeUpdateReqDTO) {
        if (availableTimeUpdateReqDTO == null) {
            return null;
        }
        return AvailableTime.builder()
                .dayOfWeek(availableTimeUpdateReqDTO.getDayOfWeek())
                .time(availableTimeUpdateReqDTO.getTime())
                .build();
    }
}
