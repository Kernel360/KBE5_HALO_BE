package com.kernel.common.customer.service;


import com.kernel.common.customer.dto.request.CustomerInquiryCreateReqDTO;
import com.kernel.common.customer.dto.request.CustomerInquiryUpdateReqDTO;
import com.kernel.common.customer.dto.response.CustomerInquiryDetailRspDTO;
import com.kernel.common.customer.dto.response.CustomerInquiryRspDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerInquiryService {

    // 수요자 문의사항 검색 및 조회
    Page<CustomerInquiryRspDTO> searchCustomerInquiries(Long customerId, String keyword, Pageable pageable);

    // 수요자 문의사항 상세 조회
    CustomerInquiryDetailRspDTO getCustomerInquiryDetails(Long customerId, Long inquiryId);

    // 수요자 문의사항 등록
    CustomerInquiryDetailRspDTO createCustomerInquiry(Long customerId, CustomerInquiryCreateReqDTO inquiryRequestDTO);

    // 수요자 문의사항 수정
    CustomerInquiryDetailRspDTO updateCustomerInquiry(Long customerId, CustomerInquiryUpdateReqDTO inquiryRequestDTO);

    // 수요자 문의사항 삭제
    void deleteCustomerInquiry(Long customerId, Long inquiryId);
}
