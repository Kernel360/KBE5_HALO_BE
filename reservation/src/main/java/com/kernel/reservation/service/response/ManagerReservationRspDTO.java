package com.kernel.reservation.service.response;

import com.kernel.global.common.enums.UserRole;
import com.kernel.global.domain.entity.User;
import com.kernel.reservation.service.info.ManagerReservationDetailInfo;
import com.kernel.sharedDomain.common.enums.ReservationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Schema(description = "매니저 예약 응답 DTO")
public class ManagerReservationRspDTO {

    @Schema(description = "예약 ID", example = "123", required = true)
    private Long reservationId;

    @Schema(description = "청소 요청 날짜", example = "2023-01-01", required = true)
    private LocalDate requestDate;

    @Schema(description = "예약 시간", example = "14:00", required = true)
    private LocalTime startTime;

    @Schema(description = "소요 시간(분)", example = "120", required = true)
    private Integer turnaround;

    @Schema(description = "서비스명", example = "프리미엄 청소 서비스", required = true)
    private String serviceName;

    @Schema(description = "예약 상태", example = "CONFIRMED", required = true)
    private ReservationStatus status;

    @Schema(description = "고객 ID", example = "456", required = true)
    private Long customerId;

    @Schema(description = "추가 서비스명", example = "창문 청소", required = false)
    private List<String> extraServiceNames;

    @Schema(description = "서비스 가격", example = "50000", required = false)
    private Integer servicePrice;

    @Schema(description = "고객 요청사항", example = "창문 청소를 꼼꼼히 해주세요.", required = false)
    private String memo;

    @Schema(description = "체크 ID", example = "789", required = false)
    private Long checkId;

    @Schema(description = "체크인 일시", example = "2023-01-01T14:00:00", required = false)
    private LocalDateTime inTime;

    @Schema(description = "체크인 첨부파일 ID", example = "101", required = false)
    private Long inFileId;

    @Schema(description = "체크아웃 일시", example = "2023-01-01T16:00:00", required = false)
    private LocalDateTime outTime;

    @Schema(description = "체크아웃 첨부파일 ID", example = "102", required = false)
    private Long outFileId;

    @Schema(description = "고객 도로명 주소", example = "서울특별시 강남구 테헤란로 123", required = true)
    private String roadAddress;

    @Schema(description = "고객 상세 주소", example = "101동 202호", required = true)
    private String detailAddress;

    @Schema(description = "고객 이름", example = "홍길동", required = true)
    private String userName;

    @Schema(description = "예약 취소 일시", example = "2023-01-01T15:00:00", required = false)
    private LocalDateTime cancelDate;

    @Schema(description = "예약 취소 사유", example = "고객 요청으로 예약 취소", required = false)
    private String cancelReason;

    @Schema(description = "예약 취소자 이름" , example = "김매니저", required = false)
    private String canceledByName;

    @Schema(description = "예약 취소자 유형", example = "MANAGER", required = false)
    private UserRole canceledByRole;

    @Schema(description = "고객 리뷰 내용", example = "서비스가 매우 만족스러웠습니다.", required = false)
    private String customerReviewContent;

    @Schema(description = "고객 리뷰 평점", example = "5", minimum = "1", maximum = "5", required = false)
    private Integer customerReviewRating;

    @Schema(description = "매니저 리뷰 내용", example = "고객이 매우 친절했습니다.", required = false)
    private String managerReviewContent;

    @Schema(description = "매니저 리뷰 평점", example = "5", minimum = "1", maximum = "5", required = false)
    private Integer managerReviewRating;

    @Schema(description = "ManagerReservationDetailInfo에서 필요한 필드만 포함하여 DTO로 변환")
    public static ManagerReservationRspDTO fromInfo(ManagerReservationDetailInfo info, User canceledBy,
                                                   List<String> extraServiceNames, Integer servicePrice) {
        return ManagerReservationRspDTO.builder()
                .reservationId(info.getReservationId())
                .requestDate(info.getRequestDate())
                .startTime(info.getStartTime())
                .turnaround(info.getTurnaround())
                .serviceName(info.getServiceName())
                .status(info.getStatus())
                .customerId(info.getCustomerId())
                .roadAddress(info.getRoadAddress())
                .detailAddress(info.getDetailAddress())
                .userName(info.getUserName())
                .extraServiceNames(extraServiceNames != null ? extraServiceNames : null)
                .servicePrice(servicePrice)
                .memo(info.getMemo() != null ? info.getMemo() : null)
                .checkId(info.getReservationCheckId() != null ? info.getReservationCheckId() : null)
                .inTime(info.getInTime() != null ? info.getInTime().toLocalDateTime() : null)
                .inFileId(info.getInFileId() != null ? info.getInFileId() : null)
                .outTime(info.getOutTime() != null ? info.getOutTime().toLocalDateTime() : null)
                .outFileId(info.getOutFileId() != null ? info.getOutFileId() : null)
                .cancelDate(info.getCancelDate() != null ? info.getCancelDate() : null)
                .cancelReason(info.getCancelReason() != null ? info.getCancelReason() : null)
                .canceledByName(canceledBy != null ? canceledBy.getUserName() : null)
                .canceledByRole(canceledBy != null ? canceledBy.getRole() : null)
                .customerReviewContent(info.getCustomerReviewContent() != null ? info.getCustomerReviewContent() : null)
                .customerReviewRating(info.getCustomerReviewRating() != null ? info.getCustomerReviewRating() : null)
                .managerReviewContent(info.getManagerReviewContent() != null ? info.getManagerReviewContent() : null)
                .managerReviewRating(info.getManagerReviewRating() != null ? info.getManagerReviewRating() : null)
                .build();
    }
}
