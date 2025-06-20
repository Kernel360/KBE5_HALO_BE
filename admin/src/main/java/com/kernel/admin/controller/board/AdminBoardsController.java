package com.kernel.admin.controller.board;


import com.kernel.admin.domain.entity.Board;
import com.kernel.admin.domain.enumerate.BoardType;
import com.kernel.admin.repository.AdminBoardRepository;
import com.kernel.admin.service.AdminBoardsService;
import com.kernel.admin.service.dto.request.AdminBoardsReqDTO;
import com.kernel.admin.service.dto.responsse.AdminBoardsResDTO;
import com.kernel.common.global.entity.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/boards")
@RequiredArgsConstructor
public class AdminBoardsController {

    private final AdminBoardRepository boardRepository;
    private final AdminBoardsService adminBoardsService;

    // 공지사항 / 이벤트 등록
    @PostMapping("")
    public ResponseEntity<ApiResponse<AdminBoardsResDTO>> createBoard(
            @RequestBody @Valid AdminBoardsReqDTO requestDTO
    ) {
        AdminBoardsResDTO dto = adminBoardsService.createBoard(requestDTO, 1L);
        return ResponseEntity.ok(new ApiResponse<>(true, "게시판 등록 완료", dto));
    }

    // 공지사항 / 이벤트 목록 조회
    @GetMapping("")
    public ResponseEntity<ApiResponse<List<AdminBoardsResDTO>>> getBoardList(@RequestParam("type") BoardType boardType) {
        List<AdminBoardsResDTO> dtoList = adminBoardsService.getBoardList(boardType);
        return ResponseEntity.ok(new ApiResponse<>(true, "게시판 목록 조회 완료", dtoList));
    }

    // 공지사항 / 이벤트 상세 조회
    @GetMapping("/{boardId}")
    public ResponseEntity<ApiResponse<AdminBoardsResDTO>> getBoardDetail(@PathVariable Long boardId) {
        return boardRepository.findById(boardId)
                .map(board -> ResponseEntity.ok(new ApiResponse<>(true, "게시글 상세 목록 조회 성공", AdminBoardsResDTO.fromEntity(board))))
                .orElse(ResponseEntity.ok(new ApiResponse<>(false, "게시글이 없습니다.", null)));
    }

    // 공지사항 / 이벤트 수정
    @PatchMapping("/{boardId}")
    public ResponseEntity<ApiResponse<AdminBoardsResDTO>> updateBoard(
            @PathVariable Long boardId,
            @RequestBody AdminBoardsReqDTO requestDTO
    ) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 없습니다."));

        board.update(
                requestDTO.getTitle(),
                requestDTO.getContent(),
                null,
                1L
        );
        AdminBoardsResDTO dto = AdminBoardsResDTO.fromEntity(board);
        return ResponseEntity.ok(new ApiResponse<>(true, "게시글 수정 완료", dto));
    }

    // 공지사항 / 이벤트 삭제
    @DeleteMapping("/{boardId}")
    public ResponseEntity<ApiResponse<Void>> deleteBoard(@PathVariable Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 없습니다."));
        board.Deleted(true);
        return ResponseEntity.ok(new ApiResponse<>(true, "게시글 삭제 완료", null));
    }
}
}