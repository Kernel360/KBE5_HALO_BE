package com.kernel.member.repository;

import com.kernel.member.domain.entity.ManagerStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ManagerStatisticRepository extends JpaRepository<ManagerStatistic, Long> {
    @Modifying(clearAutomatically = true) // 영속성 컨텍스트를 자동으로 초기화하여 변경 사항을 반영
    @Query("UPDATE ManagerStatistic ms SET ms.reviewCount = ms.reviewCount + 1 WHERE ms.user.id = :userId")
    void updateReviewCount(Long userId);
}
