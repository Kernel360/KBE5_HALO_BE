package com.kernel.admin.domain.entity;

import com.kernel.admin.service.dto.request.AdminBannerReqDTO;
import com.kernel.global.domain.entity.BaseEntity;
import com.kernel.global.domain.entity.File;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Table(name = "banner")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
public class Banner extends BaseEntity {

    // 배너 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bannerId;

    @Column
    private String title;

    @Column
    private String path;

    @Column
    private LocalDate startAt;

    @Column
    private LocalDate endAt;

    @OneToOne
    @JoinColumn(nullable = false)
    private File file;

    @Column(nullable = false)
    @Builder.Default
    private Long views = 0L;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isDeleted = false;

    public void incrementViews() {
        this.views++;
    }


    public void update(AdminBannerReqDTO reqDTO, File file) {
        if (reqDTO.getTitle() != null) {
            this.title = reqDTO.getTitle();
        }
        if (reqDTO.getPath() != null) {
            this.path = reqDTO.getPath();
        }
        if (reqDTO.getStartAt() != null) {
            this.startAt = reqDTO.getStartAt();
        }
        if (reqDTO.getEndAt() != null) {
            this.endAt = reqDTO.getEndAt();
        }
        if (reqDTO.getFileId() != null) {
            this.file = file;
        }
        this.isDeleted = false; // 배너 수정 시 삭제 상태를 false로 초기화
    }

    public void delete(){
        this.isDeleted = true; // 배너 삭제 시 삭제 상태를 true로 설정
    }

}
