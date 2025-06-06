package com.kernel.common.global.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FileDeleteRspDTO {

    // 게시물 id
    private Long postId;

    // 남은 파일 정보의 json 형식의 리스트
    private String remainingFiles;
}
