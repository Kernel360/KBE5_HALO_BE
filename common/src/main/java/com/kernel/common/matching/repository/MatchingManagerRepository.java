package com.kernel.common.matching.repository;

import com.kernel.common.manager.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MatchingManagerRepository extends JpaRepository<Manager, Long>, CustomMatchingRepository {

    // 매니저 조회 for 수요자 예약 확정
    Optional<Manager> findByManagerId(Long selectedManagerId);

    // 매칭 매니저 리스트 조회
    List<Manager> findByManagerIdIn(List<Long> matchedManagerIds);

}
