package com.kernel.admin.repository;

import com.kernel.admin.service.dto.request.BoardSearchCondDTO;
import com.kernel.admin.service.info.BoardInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomBoardRepository {

    // 문의사항 목록 조회
    Page<BoardInfo> searchBoardList(BoardSearchCondDTO searchCondDTO, Pageable pageable);
}
