package com.kernel.admin.repository;


import com.kernel.admin.common.enums.BoardType;
import com.kernel.admin.domain.entity.QBoard;
import com.kernel.admin.service.dto.request.BoardSearchCondDTO;
import com.kernel.admin.service.info.BoardInfo;
import com.kernel.global.domain.entity.QFile;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomBoardRepositoryImpl implements CustomBoardRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final QBoard board = QBoard.board;
    private final QFile file = QFile.file;

    /**
     * 공지/이벤트 조회 및 검색
     * @param searchCondDTO 검색조건 DTO
     * @return 생성된 공지/이벤트 정보
     */
    @Override
    public Page<BoardInfo> searchBoardList(BoardSearchCondDTO searchCondDTO, Pageable pageable) {

        // 전체 개수 조회
        Long total = Optional.ofNullable(
                jpaQueryFactory
                        .select(board.count())
                        .from(board)
                        .where(
                                boardTypeEq(searchCondDTO.getBoardType()),
                                titleKeyword(searchCondDTO.getTitle())
                        )
                        .fetchOne()
        ).orElse(0L);

        // 페이지 결과 조회
        List<BoardInfo> content = jpaQueryFactory
                .select(Projections.fields(BoardInfo.class,
                        board.boardId,
                        board.boardType,
                        board.title,
                        board.content,
                        board.is_Deleted.as("deleted"),
                        board.views
                ))
                .from(board)
                .leftJoin(board.file, file)
                .where(
                        boardTypeEq(searchCondDTO.getBoardType()),
                        titleKeyword(searchCondDTO.getTitle())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(board.createdAt.desc())
                .fetch();


        return new PageImpl<>(content, pageable, total);
    }

    // 보드 타입 조건
    private BooleanExpression boardTypeEq(BoardType boardType) {
        return boardType != null ? QBoard.board.boardType.eq(boardType) : null;
    }

    // 제목 키워드 조건
    private BooleanExpression titleKeyword(String keyword) {
        return keyword != null && !keyword.isBlank() ? QBoard.board.title.like("%" + keyword + "%") : null;
    }
}
