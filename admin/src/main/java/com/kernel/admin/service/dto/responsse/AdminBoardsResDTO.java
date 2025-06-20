package com.kernel.admin.service.dto.responsse;

import com.kernel.admin.domain.entity.Board;
import com.kernel.admin.domain.enums.BoardType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class AdminBoardsResDTO {

    private Long boardId;
    private BoardType BoardType;
    private String title;
    private String content;
    private Long fileId;
    private Boolean deleted;
    private Long views;

    public static AdminBoardsResDTO fromEntity(Board board) {
        return AdminBoardsResDTO.builder()
                .boardId(board.getBoardId())
                .BoardType(board.getBoardType())
                .title(board.getTitle())
                .content(board.getContent())
                .fileId(board.getFile_Id())
                .deleted(board.getIs_Deleted())
                .views(board.getViews())
                .build();
    }
}
