package com.kernel.common.customer.service;


import com.kernel.common.customer.dto.response.CustomerInquiryDetailRspDTO;
import com.kernel.common.customer.dto.response.CustomerInquiryRspDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerInquiryService {

    // 수요자 문의사항 검색 및 조회
    Page<CustomerInquiryRspDTO> searchCustomerInquiries(Long customerId, String keyword, Pageable pageable);

    // 수요자 문의사항 상세 조회
    CustomerInquiryDetailRspDTO getCustomerInquiryDetails(Long customerId, Long inquiryId);

/*
    InquiryResponseDTO createInquiry(InquiryRequestDTO inquiryRequestDTO);

    InquiryResponseDTO updateInquiry(InquiryRequestDTO inquiryRequestDTO, Long inquiryId);

    void deleteInquiry(Long inquiryId);*/
}
