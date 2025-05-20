package com.kernel360.Halo.domain.customer.service;

import com.kernel360.Halo.web.customer.dto.request.InquiryRequestDTO;
import com.kernel360.Halo.web.customer.dto.response.InquiryResponseDTO;

import java.util.List;

public interface InquiryService {


    List<InquiryResponseDTO> getInquiries(String keyword);

    InquiryResponseDTO createInquiry(InquiryRequestDTO inquiryRequestDTO);

    InquiryResponseDTO updateInquiry(InquiryRequestDTO inquiryRequestDTO, Long inquiryId);

    void deleteInquiry(Long inquiryId);
}
