package com.kernel360.Halo.domain.customer.repository;

import com.kernel360.Halo.domain.customer.entity.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
    List<Inquiry> findByTitleContainingIgnoreCase(String keyword);
}
