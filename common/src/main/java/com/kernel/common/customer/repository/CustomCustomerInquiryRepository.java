package com.kernel.common.customer.repository;

import com.kernel.common.admin.dto.request.AdminInquirySearchReqDTO;
import com.kernel.common.customer.dto.response.CustomerInquiryRspDTO;
import com.kernel.common.customer.entity.CustomerInquiry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;

public interface CustomCustomerInquiryRepository {

    // 수요자 문의사항 조회 및 검색
    Page<CustomerInquiryRspDTO> searchByAuthorIdAndKeyword(Long customerId, LocalDateTime startDate, Pageable pageable);

    // 수요자 문의사항 검색(관리자용)
    Page<CustomerInquiry> searchCustomerInquiryByKeyword(AdminInquirySearchReqDTO query, Pageable pageable);

    // 수요자 문의사항 상세 조회
    Optional<CustomerInquiry> getCustomerInquiryDetails(Long customerId, Long inquiryId);
}
