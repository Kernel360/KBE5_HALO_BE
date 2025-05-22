package com.kernel.customer.repository;

import com.kernel.customer.entity.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
    List<Inquiry> findByTitleContainingIgnoreCase(String keyword);
}
