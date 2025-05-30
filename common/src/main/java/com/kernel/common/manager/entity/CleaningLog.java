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
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    // 체크인 일시 (INSERT 시 자동 생성)
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime inTime;

    // 체크인 첨부파일
    private Long inFileId;

    // 체크아웃 일시 (UPDATE 시 자동 갱신)
    @UpdateTimestamp
    private LocalDateTime outTime;

    // 체크아웃 첨부파일
    private Long outFileId;

    /**
     * 체크아웃 처리
     *  - outTime은 @UpdateTimestamp로 자동 갱신됨
     * @param outFileId
     */
    public void checkOut(Long outFileId) {
        this.outFileId = outFileId;
    }
}
