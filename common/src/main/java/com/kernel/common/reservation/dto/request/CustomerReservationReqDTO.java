package com.kernel.common.reservation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
public class CustomerReservationReqDTO {

    // 메인 서비스ID
    @NotNull(message = "서비스 유형을 선택해주세요.")
    private Long mainServiceId;

    // 추가 서비스ID
    private List<Long> additionalServiceIds;

    // 핸드폰 번호
    @NotBlank(message = "핸드폰 번호를 입력해주세요.")
    private String phone;

    // 도로명 주소
    @NotBlank(message = "도로명 주소를 입력해주세요.")
    private String roadAddress;

    // 상세 주소
    @NotBlank(message = "상세주소를 입력해주세요.")
    private String detailAddress;

    // 위도
    @NotNull(message = "주소를 입력해주세요.")
    private BigDecimal latitude;

    // 경도
    @NotNull(message = "주소를 입력해주세요.")
    private BigDecimal longitude;

    // 청소 요청 날짜
    @NotNull(message = "서비스 요청 날짜를 입력해주세요.")
    private LocalDate requestDate;

    // 청소 시작 희망 시간
    @NotNull(message = "서비스 희망 시간을 입력해주세요.")
    private LocalTime startTime;

    // 소요 시간
    @NotNull(message = "서비스를 선택해주세요.")
    private Integer turnaround;

    // 예약 금액
    @NotNull(message = "서비스를 선택해주세요.")
    private Integer price;

    // 고객 요청사항 메모
    private String memo;
}
