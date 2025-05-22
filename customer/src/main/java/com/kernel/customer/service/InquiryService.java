package com.kernel.customer.service;

import com.kernel.customer.dto.request.InquiryRequestDTO;
import com.kernel.customer.dto.response.InquiryResponseDTO;

import java.util.List;

public interface InquiryService {


    List<InquiryResponseDTO> getInquiries(String keyword);

    InquiryResponseDTO createInquiry(InquiryRequestDTO inquiryRequestDTO);

    InquiryResponseDTO updateInquiry(InquiryRequestDTO inquiryRequestDTO, Long inquiryId);

    void deleteInquiry(Long inquiryId);
}
