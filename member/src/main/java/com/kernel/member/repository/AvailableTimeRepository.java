package com.kernel.member.repository;

import com.kernel.member.domain.entity.AvailableTime;
import com.kernel.member.domain.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvailableTimeRepository extends JpaRepository<AvailableTime, Long> {
    List<AvailableTime> findByManager(Manager manager);
}
