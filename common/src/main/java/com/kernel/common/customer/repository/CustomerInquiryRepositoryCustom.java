package com.kernel.common.customer.repository;

import com.kernel.common.customer.entity.CustomerInquiry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CustomerInquiryRepositoryCustom {

    // 수요자 문의사항 조회 및 검색
    Page<CustomerInquiry> searchByAuthorIdAndKeyword(Long customerId, String keyword, Pageable pageable);

    // 수요자 문의사항 상세 조회
    Optional<CustomerInquiry> getCustomerInquiryDetails(Long customerId, Long inquiryId);
}
