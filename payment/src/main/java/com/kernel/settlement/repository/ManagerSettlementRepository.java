package com.kernel.settlement.repository;

import com.kernel.settlement.domain.Settlement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface ManagerSettlementRepository extends JpaRepository<Settlement, Long>, CustomManagerSettleRepository {

    // 저번주 정산 금액
    @Query("SELECT COALESCE(SUM(s.totalAmount), 0) " +
            "FROM Settlement s " +
            "WHERE s.manager.userId = :userId " +
            "AND s.settledAt BETWEEN :start AND :end " +
            "AND s.status = 'SETTLED'"
    )
    Long getSettledAmount(@Param("userId")Long userId,
                                  @Param("start") LocalDateTime start,
                                  @Param("end")LocalDateTime end);
}
