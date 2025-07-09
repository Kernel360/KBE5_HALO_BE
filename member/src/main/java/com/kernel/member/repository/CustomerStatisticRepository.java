package com.kernel.member.repository;

import com.kernel.member.domain.entity.CustomerStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CustomerStatisticRepository extends JpaRepository<CustomerStatistic, Long> {

    @Modifying
    @Query("UPDATE CustomerStatistic cs SET cs.reservationCount = cs.reservationCount + :count WHERE cs.userId = :userId")
    void updateReservationCount(Long userId, Integer count);
}
