package com.kernel.global.domain.entity;

import com.kernel.global.common.enums.PostStatus;

import java.time.LocalDateTime;

public class File {

    private Long fileId;

    // 파일의 경로를 JSON 배열 형태로 저장
    private String filePathsJson;

    // 게시물의 실제 게시 상태
    private PostStatus postStatus = PostStatus.TEMP; // 기본값은 TEMP로 설정

    // 게시 시각
    private LocalDateTime uploadedAt;

}
