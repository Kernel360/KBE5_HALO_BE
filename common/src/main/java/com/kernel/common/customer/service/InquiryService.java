package com.kernel.common.customer.service;


import com.kernel.common.customer.dto.response.InquiryRspDTO;

import java.util.List;

public interface InquiryService {

    List<InquiryRspDTO> getInquiries(String keyword);

/*
    InquiryResponseDTO createInquiry(InquiryRequestDTO inquiryRequestDTO);

    InquiryResponseDTO updateInquiry(InquiryRequestDTO inquiryRequestDTO, Long inquiryId);

    void deleteInquiry(Long inquiryId);*/
}
