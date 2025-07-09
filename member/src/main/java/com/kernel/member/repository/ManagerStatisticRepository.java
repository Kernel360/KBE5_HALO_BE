package com.kernel.member.repository;

import com.kernel.member.domain.entity.ManagerStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ManagerStatisticRepository extends JpaRepository<ManagerStatistic, Long> {

    @Modifying
    @Query("UPDATE ManagerStatistic ms SET ms.reservationCount = ms.reservationCount + :count WHERE ms.userId = :userId")
    void updateReservationCount(Long userId, Integer count);
}
