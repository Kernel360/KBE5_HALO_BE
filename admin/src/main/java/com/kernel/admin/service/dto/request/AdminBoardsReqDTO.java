package com.kernel.admin.service.dto.request;

import com.kernel.admin.domain.entity.Board;
import com.kernel.admin.domain.enumerate.BoardType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class AdminBoardsReqDTO {

    private BoardType boardType;
    private String title;
    private String content;

    public Board toEntity() {
        return Board.builder()
                .boardType(this.boardType)
                .title(this.title)
                .content(this.content)
                .is_Deleted(false)
                .views(0L)
                .build();

    }
}
