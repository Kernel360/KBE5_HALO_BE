package com.kernel.admin.service.board;

import com.kernel.admin.domain.enums.BoardType;
import com.kernel.admin.service.dto.request.AdminBoardsReqDTO;
import com.kernel.admin.service.dto.responsse.AdminBoardsResDTO;

import java.util.List;

public interface AdminBoardsService {

    AdminBoardsResDTO createBoard(AdminBoardsReqDTO dto, Long adminId);

    List<AdminBoardsResDTO> getBoardList(BoardType boardType);

}