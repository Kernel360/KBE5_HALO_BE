package com.kernel.reservation.service.request;


import java.time.LocalDate;
import java.util.List;

import com.kernel.sharedDomain.common.enums.ReservationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Schema(description = "매니저 예약 검색 조건 DTO")
public class ManagerReservationSearchCondDTO {

    @Schema(description = "예약 날짜 시작일", example = "2023-01-01", required = true)
    private LocalDate fromRequestDate;

    @Schema(description = "예약 날짜 종료일", example = "2023-01-31", required = true)
    private LocalDate toRequestDate;

    @Schema(description = "예약 상태 목록", example = "[\"CONFIRMED\", \"CANCELLED\"]", required = false)
    private List<ReservationStatus> reservationStatus;

    @Schema(description = "체크인 여부", example = "true", required = false)
    private Boolean isCheckedIn;

    @Schema(description = "체크아웃 여부", example = "false", required = false)
    private Boolean isCheckedOut;

    @Schema(description = "리뷰 작성 여부", example = "true", required = false)
    private Boolean isReviewed;

    @Schema(description = "고객명 키워드", example = "홍길동", required = false)
    private String customerNameKeyword;
}
