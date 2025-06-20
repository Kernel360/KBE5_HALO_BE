package com.kernel.admin.service;

import com.kernel.admin.domain.enumerate.BoardType;
import com.kernel.admin.service.dto.request.AdminBoardsReqDTO;
import com.kernel.admin.service.dto.responsse.AdminBoardsResDTO;

import java.util.List;

public interface AdminBoardsService {

    AdminBoardsResDTO createBoard(AdminBoardsReqDTO dto, Long adminId);

    List<AdminBoardsResDTO> getBoardList(BoardType boardType);

}