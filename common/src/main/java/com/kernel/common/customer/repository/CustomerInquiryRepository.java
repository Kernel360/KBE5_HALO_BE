package com.kernel.common.customer.repository;

import com.kernel.common.customer.entity.CustomerInquiry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerInquiryRepository extends JpaRepository<CustomerInquiry, Long>, CustomerInquiryRepositoryCustom {
}
