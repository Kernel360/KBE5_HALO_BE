package com.kernel.member.repository;

import com.kernel.member.domain.entity.AvailableTime;
import com.kernel.member.domain.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AvailableTImeRepository extends JpaRepository<AvailableTime, Long> {
    Optional<List<AvailableTime>> findByManager(Manager manager);
}
