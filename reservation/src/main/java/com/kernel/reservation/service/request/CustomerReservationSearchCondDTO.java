package com.kernel.reservation.service.request;


import com.kernel.sharedDomain.common.enums.ReservationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Schema(description = "수요자 예약 검색 조건 DTO")
public class CustomerReservationSearchCondDTO {

    @Schema(description = "예약 날짜 시작일", example = "2023-01-01", required = false)
    private LocalDate fromRequestDate;

    @Schema(description = "예약 날짜 종료일", example = "2023-01-31", required = false)
    private LocalDate toRequestDate;

    @Schema(description = "예약 상태 목록", example = "[\"REQUESTED\", \"CONFIRMED\", \"COMPLETED\", \"CANCELED\"]", required = false)
    private List<ReservationStatus> reservationStatus;

    @Schema(description = "매니저명 키워드", example = "홍길동", required = false)
    private String managerNameKeyword;

}
