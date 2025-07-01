package com.kernel.member.repository;

import com.kernel.member.domain.entity.CustomerStatistic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerStatisticRepository extends JpaRepository<CustomerStatistic, Long> {
}
