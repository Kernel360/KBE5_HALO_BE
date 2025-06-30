package com.kernel.admin.service;


import com.kernel.admin.service.dto.request.BoardCreateReqDto;
import com.kernel.admin.service.dto.request.BoardSearchCondDTO;
import com.kernel.admin.service.dto.response.BoardDetailRspDTO;
import com.kernel.admin.service.dto.response.BoardSummaryRspDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardService {

    // 보드 생성
    BoardDetailRspDTO createBoard(BoardCreateReqDto dto);

    // 보드 조회
    Page<BoardSummaryRspDTO> searchBoardList(BoardSearchCondDTO searchCondDTO, Pageable pageable);

    // 보드 상세 조회
    BoardSummaryRspDTO getBoardDetail(Long boardId);

    // 보드 수정
    BoardDetailRspDTO updateBoard(Long boardId, BoardCreateReqDto requestDto);

    // 보드 삭제
    void deleteBoard(Long boardId);
}