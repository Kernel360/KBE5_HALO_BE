package com.kernel.admin.service;


import com.kernel.admin.domain.entity.Board;
import com.kernel.admin.repository.BoardRepository;
import com.kernel.admin.service.dto.request.BoardCreateReqDto;
import com.kernel.admin.service.dto.request.BoardSearchCondDTO;
import com.kernel.admin.service.dto.response.BoardDetailRspDTO;
import com.kernel.admin.service.dto.response.BoardSummaryRspDTO;
import com.kernel.admin.service.info.BoardInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    /**
     * 공지/이벤트 생성
     * @param requestDto 관리자 회원가입 요청 DTO
     * @return 생성된 공지/이벤트 정보
     */
    @Override
    @Transactional
    public BoardDetailRspDTO createBoard(BoardCreateReqDto requestDto)
    {

        Board saveBoard = boardRepository.save(
                Board.builder()
                        .boardType(requestDto.getBoardType())
                        .title(requestDto.getTitle())
                        .content(requestDto.getContent())
                        .build()
        );

        return BoardDetailRspDTO.fromEntity(saveBoard);
    }

    /**
     * 공지/이벤트 조회 및 검색
     * @param searchCondDTO 검색조건 DTO
     * @return 생성된 공지/이벤트 정보
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BoardSummaryRspDTO> searchBoardList(BoardSearchCondDTO searchCondDTO, Pageable pageable) {

        Page<BoardInfo> searchBoardList = boardRepository.searchBoardList(searchCondDTO, pageable);

        return searchBoardList.map(BoardSummaryRspDTO::fromInfo);
    }

    /**
     * 공지/이벤트 상세 조회
     * @param boardId  조회할 보드 ID
     * @return 조회된 공지/이벤트 상세 정보
     */
    @Override
    @Transactional
    public BoardSummaryRspDTO getBoardDetail(Long boardId) {

        // 1. 상세 조회
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 공지/이벤트 입니다."));

        // 2. 조회 수 증가
        board.updateViews();

        return BoardSummaryRspDTO.fromEntity(board);
    }

    /**
     * 공지/이벤트 수정
     * @param boardId 수정할 보드ID
     * @return 수정된 공지/이벤트 상세 정보
     */
    @Override
    @Transactional
    public BoardDetailRspDTO updateBoard(Long boardId, BoardCreateReqDto requestDto) {

        Board updateBoard = boardRepository.findById(boardId)
                .orElseThrow(() -> new NoSuchElementException("수정할 공지/이벤트가 존재하지 않습니다.."));

        updateBoard.updateBoard(requestDto);

        return BoardDetailRspDTO.fromEntity(updateBoard);
    }

    /**
     * 공지/이벤트 삭제
     * @param boardId 삭제할 보드ID
     */
    @Override
    @Transactional
    public void deleteBoard(Long boardId) {

        Board deleteBoard = boardRepository.findByBoardIdAndIsDeleted(boardId, false)
                .orElseThrow(() -> new NoSuchElementException("삭제할 공지/이벤트가 존재하지 않습니다.."));

        deleteBoard.delete();

    }
}
