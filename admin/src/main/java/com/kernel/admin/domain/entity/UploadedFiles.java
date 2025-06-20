package com.kernel.admin.domain.entity;

import com.kernel.admin.domain.enumerate.PostStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "file")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class UploadedFiles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileId;

    // 파일의 경로를 JSON 배열 형태로 저장
    @Column(columnDefinition = "json", nullable = false)
    private String filePathsJson;

    // 게시물의 실제 게시 상태
    @Column(nullable = false)
    @Builder.Default
    private PostStatus postStatus = PostStatus.TEMP; // 기본값은 TEMP로 설정

    // 게시 시각
    @Column(columnDefinition = "datetime(0)", updatable = false)
    @CreationTimestamp
    private LocalDateTime uploadedAt;

    /**
     * 게시물 업로드 전 첨부파일이 추가 혹은 삭제 되는 경우 update
     * 파일 경로를 JSON 배열 형태로 저장
     */
    public void updateFiles(String filePathsJson) {this.filePathsJson = filePathsJson;}

    public void restore() {
        this.postStatus = PostStatus.REGISTERED;
    }

}
