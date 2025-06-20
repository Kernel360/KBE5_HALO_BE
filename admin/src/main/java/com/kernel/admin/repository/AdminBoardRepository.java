package com.kernel.admin.repository;

import com.kernel.admin.domain.entity.Board;
import com.kernel.admin.domain.enumerate.BoardType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminBoardRepository extends JpaRepository<Board, Long> {

    List<Board> findByBoardType(BoardType type);
}