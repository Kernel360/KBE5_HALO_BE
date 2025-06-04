package com.kernel.common.global.entity;

import com.kernel.common.global.enums.FileStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "uploaded_files")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class UploadedFiles extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 함께 게시될 post의 ID
    // 게시물과의 연관 관계를 나타내며, null인 경우 게시물이 아직 작성되지 않았거나
    // 게시물과 연관되지 않은 파일임을 의미
    @Column(unique = true, nullable = true)
    private Long postId;

    // 파일의 경로를 JSON 배열 형태로 저장
    @Column(columnDefinition = "json", nullable = false)
    private String filePathsJson;

    // 게시물의 실제 게시 상태
    @Column(nullable = false)
    @Builder.Default
    private FileStatus fileStatus = FileStatus.TEMP; // 기본값은 TEMP로 설정

    // 게시물 삭제 여부
    @Column(nullable = false)
    @Builder.Default
    private Boolean isDeleted = false;

    /**
     * 게시물 업로드 전 첨부파일이 추가 혹은 삭제 되는 경우 update
     * 파일 경로를 JSON 배열 형태로 저장
     */
    public void updateFiles(String filePathsJson) {
        this.filePathsJson = filePathsJson;
    }

    /**
     * 파일 업로드 시 postId가 null인 경우 임시 저장 상태로 간주
     * 이 경우 fileStatus는 TEMP로 설정
     * 게시물에 첨부되면 postId가 설정되고 fileStatus는 REGISTERED로 변경됨
     */
    public void attachToPost(Long postId) {
        this.postId = postId;
        this.fileStatus = FileStatus.REGISTERED; // 게시물에 첨부되면 상태를 REGISTERED로 변경
    }

    /**
     * 게시물 삭제 상태 관리
     * 게시물 삭제 시 isDeleted를 true로 설정
     * 게시물 복원 시 false로 설정
     * fileStatus는 게시물의 실제 게시 상태를 나타내며, 삭제 상태와는 별개로 관리
     */
    public void delete() {
        this.isDeleted = true;
    }

    public void restore() {
        this.isDeleted = false;
    }

}
