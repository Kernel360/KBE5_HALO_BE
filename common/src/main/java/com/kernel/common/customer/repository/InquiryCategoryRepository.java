package com.kernel.common.customer.repository;

import com.kernel.common.customer.entity.InquiryCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryCategoryRepository extends JpaRepository<InquiryCategory, Long> {
}
