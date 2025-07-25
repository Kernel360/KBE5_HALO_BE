package com.kernel.reservation.domain.entity;

import com.kernel.global.domain.entity.BaseEntity;
import com.kernel.sharedDomain.domain.entity.Reservation;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;

@Entity
@Table(name = "service_check_log")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
public class ServiceCheckLog extends BaseEntity {

    // 예약 Id
    @Id
    private Long reservationId;

    // 예약 ID
    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

    // 체크인 일시
    @Column(nullable = false)
    private Timestamp inTime;

    // 체크인 파일 ID
    //@Column(nullable = false)
    @Column(nullable = true) // 이슈 테스트 - 체크인 파일은 필수로 하지 않음
    private Long inFileId;

    // 체크아웃 시간
    @Column(nullable = true)
    private Timestamp outTime;

    // 체크아웃 파일 ID
    @Column(nullable = true)
    private Long outFileId;

    @PreRemove
    private void preventRemove() {
        throw new UnsupportedOperationException("예약 관련 정보는 삭제할 수 없습니다.");
    }

    // 체크인
    public void checkIn(Long inFileId) {
        this.inTime = new Timestamp(System.currentTimeMillis());
        this.inFileId = inFileId;
    }

    // 체크아웃
    public void checkOut(Long outFileId) {
        this.outTime = new Timestamp(System.currentTimeMillis());
        this.outFileId = outFileId;
    }
}
