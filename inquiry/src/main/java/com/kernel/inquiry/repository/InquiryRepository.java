package com.kernel.inquiry.repository;

import com.kernel.inquiry.domain.entity.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InquiryRepository extends JpaRepository<Inquiry, Long>, CustomInquiryRepository {

    // 문의사항 조회
    Optional<Inquiry> findByIdAndAuthorId(Long inquiryId, Long userId);
}
