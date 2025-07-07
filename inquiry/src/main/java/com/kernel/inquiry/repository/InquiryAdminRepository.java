package com.kernel.inquiry.repository;

import com.kernel.inquiry.domain.entity.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryAdminRepository extends JpaRepository<Inquiry, Long>, CustomInquiryAdminRepository {
}
