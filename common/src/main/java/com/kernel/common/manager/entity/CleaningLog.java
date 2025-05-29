package com.kernel.common.manager.entity;

import com.kernel.common.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name="cleaning_log")
@Getter
@SuperBuilder
@NoArgsConstructor
public class CleaningLog extends BaseEntity {

    // 체크ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long checkId;

    // 예약ID
    // TODO: 예약 테이블과 추후 연결 필요
    @Column(nullable = false)
    private Long reservationId;

    // 체크인 일시
    private LocalDateTime inTime;

    // 체크인 첨부파일
    private Long inFileId;

    // 체크아웃 일시
    private LocalDateTime outTime;

    // 체크아웃 첨부파일
    private Long outFileId;

    @PrePersist
    public void prePersist() {
        // 체크인할때 Entity가 생성됨
        if (this.inTime == null) {
            this.inTime = LocalDateTime.now();
        }
    }

    public void checkOut(Long outFileId) {
        this.outTime = LocalDateTime.now(); // 현재 시간으로 설정
        this.outFileId = outFileId;
    }
}
