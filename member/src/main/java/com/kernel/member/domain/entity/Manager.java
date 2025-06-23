package com.kernel.member.domain.entity;

import com.kernel.global.domain.entity.File;
import com.kernel.global.domain.entity.User;
import com.kernel.member.common.enums.ContractStatus;

import com.kernel.member.service.request.ManagerUpdateInfoReqDTO;
import com.kernel.reservation.domain.entity.ServiceCategory;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Table(name = "manager")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
public class Manager {

    @Id
    private Long userId;

    // 매니저 ID
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    // 특기
    @ManyToOne
    @JoinColumn
    private ServiceCategory specialty;

    // 한 줄 소개
    @Column(length = 50)
    private String bio;

    // 프로필 URL
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private File profileImageFileId;

    // 서류 파일 ID
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private File fileId;

    // 계약상태
    @Enumerated(EnumType.STRING)
    @Column
    private ContractStatus contractStatus;

    // 계약 일시
    @Column
    private LocalDateTime contractDate;

    // Manager 정보 수정
    public void update(ManagerUpdateInfoReqDTO request) {
        if (request.getSpecialty() != null) {
            this.specialty = request.getSpecialty();
        }
        if (request.getBio() != null) {
            this.bio = request.getBio();
        }
    }

    // 매니저 신청 승인
    public void approve() {
        this.contractStatus = ContractStatus.APPROVED;
        this.contractDate = LocalDateTime.now();
    }

    // 매니저 신청 거절
    public void reject() {
        this.contractStatus = ContractStatus.REJECTED;
    }

    // 매니저 계약 해지 요청
    public void requestTermination() {
        this.contractStatus = ContractStatus.TERMINATION_PENDING;
    }

    // 매니저 계약 해지
    public void terminate() {
        this.contractStatus = ContractStatus.TERMINATED;
    }

}
