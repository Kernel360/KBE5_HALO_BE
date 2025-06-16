package com.kernel.common.reservation.dto.response;

import com.kernel.common.reservation.enums.ReservationStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ManagerReservationRspDTO {

    /* 예약 정보 **************************************/
    // 예약ID
    private String reservationId;

    // 청소 요청 날짜
    private String requestDate;

    // 예약시간
    private String startTime;

    // 소요시간
    private Integer turnaround;

    // 서비스명
    private String serviceName;

    // 상태
    private ReservationStatus status;

    // 상태명
    private String statusName;


    /* 고객 정보 **************************************/
    // 고객명
    private String customerName;

    // 고객 연락처
    private String customerPhone;

    // 고객 주소(도로명 주소 + 상세 주소)
    private String customerAddress;


    /* 서비스 상세 ************************************/
    // 추가 서비스
    private String extraServiceName;
    // 고객 요청사항
    private String memo;


    /* 체크인/ 체크아웃 ********************************/
    // 체크ID
    private Long checkId;

    // 체크인 일시
    private LocalDateTime inTime;

    // 체크인 첨부파일
    private Long inFileId;

    // 체크아웃 일시
    private LocalDateTime outTime;

    // 체크아웃 첨부파일
    private Long outFileId;


    /* 수요자 리뷰 ************************************/
    // 수요자 리뷰ID
    private Long customerReviewId;

    // 수요자 리뷰 평점
    private Integer customerRating;

    // 수요자 리뷰 내용
    private String customerContent;

    // 수요자 리뷰 작성일시
    private LocalDateTime customerCreateAt;


    /* 매니저 리뷰 ************************************/
    // 매니저 리뷰ID
    private Long managerReviewId;

    // 매니저 리뷰 평점
    private Integer managerRating;

    // 매니저 리뷰 내용
    private String managerContent;

    // 매니저 리뷰 작성일시
    private LocalDateTime managerCreateAt;
}
