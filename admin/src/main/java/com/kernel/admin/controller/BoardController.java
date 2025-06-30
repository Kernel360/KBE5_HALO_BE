package com.kernel.admin.controller;

import com.kernel.admin.service.BoardService;
import com.kernel.admin.service.dto.request.BoardCreateReqDto;
import com.kernel.admin.service.dto.request.BoardSearchCondDTO;
import com.kernel.admin.service.dto.response.BoardDetailRspDTO;
import com.kernel.admin.service.dto.response.BoardSummaryRspDTO;
import com.kernel.global.service.dto.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    /**
     * 공지/이벤트 생성
     * @param requestDto 관리자 회원가입 요청 DTO
     * @return 생성된 공지/이벤트 정보
     */
    @PostMapping("")
    public ResponseEntity<ApiResponse<BoardDetailRspDTO>> createNotice(
            @RequestBody @Valid BoardCreateReqDto requestDto
    ) {
        BoardDetailRspDTO requestBoard = boardService.createBoard(requestDto);

        return ResponseEntity.ok(new ApiResponse<>(true, "게시판 등록 완료", requestBoard));
    }

    /**
     * 공지/이벤트 조회 및 검색
     * @param searchCondDTO 검색조건 DTO
     * @return 조회된 공지/이벤트 정보
     */
    @GetMapping("")
    public ResponseEntity<ApiResponse<Page<BoardSummaryRspDTO>>> searchBoardList(
            @ModelAttribute BoardSearchCondDTO searchCondDTO,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        Page<BoardSummaryRspDTO> dtoPage = boardService.searchBoardList(searchCondDTO, pageable);

        return ResponseEntity.ok(new ApiResponse<>(true, "게시판 목록 조회 완료", dtoPage));
    }

    /**
     * 공지/이벤트 상세 조회
     * @param boardId  조회할 보드ID
     * @return 조회된 공지/이벤트 상세 정보
     */
    @GetMapping("/{board-id}")
    public ResponseEntity<ApiResponse<BoardSummaryRspDTO>> getBoardDetail(
            @PathVariable("board-id") Long boardId
    ) {
        BoardSummaryRspDTO detailDto = boardService.getBoardDetail(boardId);

        return ResponseEntity.ok(new ApiResponse<>(true, "게시판 상세 조회", detailDto));
    }

    /**
     * 공지/이벤트 수정
     * @param boardId 수정할 보드ID
     * @return 수정된 공지/이벤트 상세 정보
     */
    @PatchMapping("/{board-id}")
    public ResponseEntity<ApiResponse<BoardDetailRspDTO>> updateBoard(
            @PathVariable("board-id") Long boardId,
            @RequestBody BoardCreateReqDto requestDto
    ) {
        BoardDetailRspDTO updateBoard = boardService.updateBoard(boardId, requestDto);

        return ResponseEntity.ok(new ApiResponse<>(true, "게시글 수정 완료", updateBoard));
    }

    /**
     * 공지/이벤트 삭제
     * @param boardId 삭제할 보드ID
     */
    @DeleteMapping("/{board-id}")
    public ResponseEntity<ApiResponse<Void>> deleteBoard(
            @PathVariable("board-id") Long boardId
    ) {
        boardService.deleteBoard(boardId);

        return ResponseEntity.ok(new ApiResponse<>(true, "게시글 삭제 완료", null));
    }
}