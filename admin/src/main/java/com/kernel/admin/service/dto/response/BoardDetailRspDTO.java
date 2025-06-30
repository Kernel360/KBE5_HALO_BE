package com.kernel.admin.service.dto.response;

import com.kernel.admin.common.enums.BoardType;
import com.kernel.admin.domain.entity.Board;
import com.kernel.admin.service.info.BoardInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class BoardDetailRspDTO {

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

    // Board -> BoardDetailRspDTO
    public static BoardDetailRspDTO fromEntity(Board board) {
        return BoardDetailRspDTO.builder()
                .boardId(board.getBoardId())
                .boardType(board.getBoardType())
                .title(board.getTitle())
                .content(board.getContent())
                .fileId(board.getFile().getFileId())
                .filePathsJson(board.getFile().getFilePathsJson())
                .deleted(board.getIs_Deleted())
                .views(board.getViews())
                .build();
    }

    // BoardInfo -> BoardDetailRspDTO
    public static BoardDetailRspDTO fromInfo(BoardInfo info) {
        return BoardDetailRspDTO.builder()
                .boardId(info.getBoardId())
                .boardType(info.getBoardType())
                .title(info.getTitle())
                .content(info.getContent())
                .fileId(info.getFileId())
                .filePathsJson(info.getFilePathsJson())
                .deleted(info.getDeleted())
                .views(info.getViews())
                .build();
    }
}
