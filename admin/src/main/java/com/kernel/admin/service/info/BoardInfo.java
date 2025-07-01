package com.kernel.admin.service.info;

import com.kernel.admin.common.enums.BoardType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BoardInfo {

    // 공지/이벤트 ID
    private Long boardId;

    // 타입
    private BoardType boardType;

    // 제목
    private String title;

    // 내용
    private String content;

    // 파일 ID
    private Long fileId;

    // 파일 url
    private String filePathsJson;

    // 삭제여부
    private Boolean deleted;

    // 조회수
    private Long views;
}
