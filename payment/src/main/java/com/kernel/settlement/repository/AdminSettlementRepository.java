package com.kernel.settlement.repository;

import com.kernel.settlement.domain.Settlement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminSettlementRepository extends JpaRepository<Settlement, Long>, CustomAdminSettleRepository {


}
