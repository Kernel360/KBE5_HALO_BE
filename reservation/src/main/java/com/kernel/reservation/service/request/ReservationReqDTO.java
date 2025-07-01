package com.kernel.reservation.service.request;

import com.kernel.global.domain.entity.User;
import com.kernel.reservation.domain.entity.ReservationLocation;
import com.kernel.reservation.domain.entity.ReservationSchedule;
import com.kernel.sharedDomain.domain.entity.Reservation;
import com.kernel.sharedDomain.domain.entity.ServiceCategory;
import jakarta.validation.constraints.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
public class ReservationReqDTO {

    // 메인 서비스 ID
    @NotNull(message = "서비스 유형을 선택해주세요.")
    private Long mainServiceId;

    // 추가 서비스 ID
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

    // 예약 금액
    @NotNull(message = "서비스를 선택해주세요.")
    private Integer price;

    // 고객 요청사항 메모
    private String memo;

    // ReservationReqDTO -> Reservation
    public Reservation toReservation(User user, ServiceCategory serviceCategory) {
        return Reservation.builder()
                .user(user)
                .price(this.price)
                .memo(this.memo)
                .phone(this.phone)
                .serviceCategory(serviceCategory)
                .build();
    }

    // ReservationReqDTO -> ReservationSchedule
    public ReservationSchedule toSchedule(Reservation reservation) {
        return ReservationSchedule.builder()
                .reservation(reservation)
                .requestDate(this.requestDate)
                .startTime(this.startTime)
                .turnaround(this.turnaround)
                .build();
    }

    // ReservationReqDTO -> ReservationLocation
    public ReservationLocation toLocation(Reservation reservation) {
        return ReservationLocation.builder()
                .reservation(reservation)
                .latitude(this.latitude)
                .longitude(this.longitude)
                .roadAddress(this.roadAddress)
                .detailAddress(this.detailAddress)
                .build();
    }

}
