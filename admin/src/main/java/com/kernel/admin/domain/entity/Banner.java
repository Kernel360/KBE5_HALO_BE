package com.kernel.admin.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kernel.admin.domain.enums.BannerStatus;
import com.kernel.admin.service.dto.request.AdminBannerReqDTO;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Table(name = "banner")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@ToString
//extends BaseEntity
public class Banner {

    // Banner ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bannerId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String path;

    // 광고 배너 게시 시작일
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private LocalDate startAt;

    // 광고 배너 게시 종료일
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private LocalDate endAt;

    // 광고 배너 첨부파일 ID
    private Long fileId;

    // 광고 배너 조회수
    @Builder.Default
    @Column(nullable = false)
    private Long views = 0L;

    // 광고 배너 삭제관리
    @Builder.Default
    @Column(nullable = false)
    private Boolean isDeleted = false;

    public void update(AdminBannerReqDTO request) {
        this.startAt = request.getStartAt();
        this.endAt = request.getEndAt();
        this.fileId = request.getFileId();
    }

    public void incrementViews() {
        this.views++;
    }

    public void delete() {
        this.isDeleted = true;
    }

    // 게시 상태 계산 메서드
    public BannerStatus getBannerStatus() {
        LocalDate now = LocalDate.now();

        if (now.isBefore(this.startAt)) {
            return BannerStatus.PENDING;
        } else if (now.isAfter(this.endAt)) {
            return BannerStatus.EXPIRED;
        } else {
            return BannerStatus.ACTIVE;
        }
    }
}
