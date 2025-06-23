package com.kernel.member.repository;

import com.kernel.member.domain.entity.Manager;
import com.kernel.member.domain.entity.ManagerTermination;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagerTerminationRepository extends JpaRepository<ManagerTermination, Long> {
    Optional<ManagerTermination> findByManager(Manager manager);
}
