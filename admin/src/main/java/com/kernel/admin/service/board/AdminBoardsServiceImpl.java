package com.kernel.admin.service.board;

import com.kernel.admin.domain.entity.Board;
import com.kernel.admin.repository.AdminBoardRepository;
import com.kernel.admin.service.dto.request.AdminBoardsReqDTO;
import com.kernel.admin.service.dto.responsse.AdminBoardsResDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminBoardsServiceImpl implements AdminBoardsService {

    private final AdminBoardRepository adminBoardRepository;

    public AdminBoardsServiceImpl(AdminBoardRepository adminBoardRepository) {
        this.adminBoardRepository = adminBoardRepository;
    }

    // 공지사항 / 이벤트 등록
    @Transactional
    @Override
    public AdminBoardsResDTO createBoard(AdminBoardsReqDTO dto, Long adminId) {
        Board board = Board.builder()
                .boardType(dto.getBoardType())
                .title(dto.getTitle())
                .content(dto.getContent())
                .is_Deleted(false)
                .views(0L)
                .build();
        Board saved = adminBoardRepository.save(board);
        // .fileId(null)
        // TODO: 파일 첨부 구현
        return AdminBoardsResDTO.fromEntity(saved);
    }

    // 공지사항 / 이벤트 목록 조회
    @Override
    public List<AdminBoardsResDTO> getBoardList(com.kernel.admin.domain.enumerate.BoardType boardType) {
        return adminBoardRepository.findByBoardType(boardType).stream()
                .map(AdminBoardsResDTO::fromEntity)
                .collect(Collectors.toList());
    }
}