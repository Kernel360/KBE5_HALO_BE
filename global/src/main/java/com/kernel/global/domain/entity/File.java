package com.kernel.global.domain.entity;

import com.kernel.global.common.enums.PostStatus;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "file")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
public class File extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long fileId;

    // 파일의 경로를 JSON 배열 형태로 저장
    @Column(nullable = false, columnDefinition = "LONGTEXT")    // 파일 경로가 VARCHAR(255)보다 길 수 있으므로 LONGTEXT 사용
    private String filePathsJson;

    // 게시물의 실제 게시 상태
    @Column
    @Builder.Default
    private PostStatus postStatus = PostStatus.TEMP;

    // 게시 시각
    @Column
    @CreatedDate
    private LocalDateTime uploadedAt;

    public void updateFiles(String filePathsJson) {
        this.filePathsJson = filePathsJson;
    }

    public void updatePostStatus(PostStatus postStatus) {
        this.postStatus = postStatus;
    }
}
