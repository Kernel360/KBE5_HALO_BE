package com.kernel.admin.service.dto.response;



import com.kernel.admin.domain.entity.Banner;
import com.kernel.admin.domain.enums.BannerStatus;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class AdminBannerRspDTO {

    // 배너 ID
    private Long bannerId;

    // 배너 제목
    private String title;

    // 배너 경로
    private String path;

    // 배너 게시 시작 날짜
    private LocalDate startAt;

    // 배너 게시 종료 날짜
    private LocalDate endAt;

    // 배너 조회수
    private Long views;

    // 배너 첨부파일 ID
    private Long fileId;

    // 생성 정보
    private LocalDateTime createdAt;
    private Long createdBy;

    // 배너 게시 상태
    private BannerStatus bannerStatus;

    // entity -> dto Page 변환 메서드
    public static Page<AdminBannerRspDTO> fromEntityPage(Page<Banner> banner) {
        return banner.map(b -> AdminBannerRspDTO.builder()
                .bannerId(b.getBannerId())
                .title(b.getTitle())
                .path(b.getPath())
                .startAt(b.getStartAt())
                .endAt(b.getEndAt())
                .views(b.getViews())
                .fileId(b.getFile().getFileId())
                .createdAt(b.getCreatedAt())
                .createdBy(b.getCreatedBy())
                .build());
    }

    // entity -> dto 변환 메서드
    public static AdminBannerRspDTO fromEntity(Banner banner) {
        return AdminBannerRspDTO.builder()
                .bannerId(banner.getBannerId())
                .title(banner.getTitle())
                .path(banner.getPath())
                .startAt(banner.getStartAt())
                .endAt(banner.getEndAt())
                .views(banner.getViews())
                .fileId(banner.getFile().getFileId())
                .createdAt(banner.getCreatedAt())
                .createdBy(banner.getCreatedBy())
                .build();
    }
}
