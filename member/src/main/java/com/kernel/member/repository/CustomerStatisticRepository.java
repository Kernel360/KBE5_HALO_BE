package com.kernel.member.repository;

import com.kernel.member.domain.entity.CustomerStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CustomerStatisticRepository extends JpaRepository<CustomerStatistic, Long> {
    @Modifying(clearAutomatically = true) // 영속성 컨텍스트를 자동으로 초기화하여 변경 사항을 반영
    @Query("UPDATE CustomerStatistic cs SET cs.reviewCount = cs.reviewCount + 1 WHERE cs.user.id = :userId")
    void updateReviewCount(Long userId);
}
