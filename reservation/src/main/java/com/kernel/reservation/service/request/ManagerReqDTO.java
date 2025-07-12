package com.kernel.reservation.service.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ManagerReqDTO {

    // 도로명 주소
    @NotBlank(message = "도로명 주소를 입력해주세요.")
    private String roadAddress;

    // 상세 주소
    @NotBlank(message = "상세주소를 입력해주세요.")
    private String detailAddress;

    // 위도
    @DecimalMin(value = "-90.0", message = "주소를 다시 선택해주세요.")
    @DecimalMax(value = "90.0", message = "주소를 다시 선택해주세요.")
    @Digits(integer = 2, fraction = 7, message = "주소를 다시 선택해주세요.")
    @NotNull(message = "주소를 입력해주세요.")
    private BigDecimal latitude;

    // 경도
    @DecimalMin(value = "-180.0", inclusive = true, message = "주소를 다시 선택해주세요.")
    @DecimalMax(value = "180.0", inclusive = true, message = "주소를 다시 선택해주세요.")
    @Digits(integer = 3, fraction = 7, message = "주소를 다시 선택해주세요.")
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

    // ReservationReqDTO -> ManagerReqDTO
    public static ManagerReqDTO toManagerReqDTO(ReservationReqDTO reservationReqDTO) {
        return ManagerReqDTO.builder()
                .roadAddress(reservationReqDTO.getRoadAddress())
                .detailAddress(reservationReqDTO.getDetailAddress())
                .latitude(reservationReqDTO.getLatitude())
                .longitude(reservationReqDTO.getLongitude())
                .requestDate(reservationReqDTO.getRequestDate())
                .startTime(reservationReqDTO.getStartTime())
                .turnaround(reservationReqDTO.getTurnaround())
                .build();
    }

}
