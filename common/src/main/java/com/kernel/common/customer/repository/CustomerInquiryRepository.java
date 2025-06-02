package com.kernel.common.customer.repository;

import com.kernel.common.customer.entity.CustomerInquiry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerInquiryRepository extends JpaRepository<CustomerInquiry, Long>, CustomerInquiryRepositoryCustom {

    // 문의사항 조회(where inquiry and authorId)
    Optional<CustomerInquiry> findByInquiryIdAndAuthorId(Long inquiryId, Long customerId);

}
