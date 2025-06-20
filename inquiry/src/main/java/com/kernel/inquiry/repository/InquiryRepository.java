package com.kernel.inquiry.repository;

import com.kernel.inquiry.domain.entity.Inquiry;
import com.kernel.inquiry.domain.info.InquiryDetailInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InquiryRepository extends JpaRepository<Inquiry, Long>, CustomInquiryRepository {
    Optional<InquiryDetailInfo> findInfoByInquiryId(Long inquiryId);
}
