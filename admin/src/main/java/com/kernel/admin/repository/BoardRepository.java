package com.kernel.admin.repository;

import com.kernel.admin.domain.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long>, CustomBoardRepository {

    Optional<Board> findByBoardIdAndIsDeleted(Long id, Boolean isDeleted);
}
