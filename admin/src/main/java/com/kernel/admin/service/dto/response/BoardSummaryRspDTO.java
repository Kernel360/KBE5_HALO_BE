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
public class BoardSummaryRspDTO {

    // 공지/이벤트 ID
    private Long boardId;

    // 타입
    private BoardType boardType;

    // 제목
    private String title;

    // 내용
    private String content;

    // 삭제여부
    private Boolean deleted;

    // 조회수
    private Long views;

    // Board -> BoardSummaryRspDTO
    public static BoardSummaryRspDTO fromEntity(Board board) {
        return BoardSummaryRspDTO.builder()
                .boardId(board.getBoardId())
                .boardType(board.getBoardType())
                .title(board.getTitle())
                .content(trimContent(board.getContent()))
                .deleted(board.getIsDeleted())
                .views(board.getViews())
                .build();
    }

    // BoardInfo -> BoardSummaryRspDTO
    public static BoardSummaryRspDTO fromInfo(BoardInfo info) {
        return BoardSummaryRspDTO.builder()
                .boardId(info.getBoardId())
                .boardType(info.getBoardType())
                .title(info.getTitle())
                .content(trimContent(info.getContent()))
                .deleted(info.getDeleted())
                .views(info.getViews())
                .build();
    }

    // content가 10자 이상일 때 파싱 처리
    private static String trimContent(String content) {
        if(content == null) return null;
        return content.length() > 10 ? content.substring(0, 10) + "..." : content;
    }
}
