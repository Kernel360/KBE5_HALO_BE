package com.kernel.member.repository.common;

import com.kernel.sharedDomain.domain.entity.ServiceCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerServiceCategoryRepository extends JpaRepository<ServiceCategory, Long> {
}
