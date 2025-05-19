package com.kernel360.Halo.domain.customer.service;

import com.kernel360.Halo.web.customer.dto.response.InquiryResponseDTO;

import java.util.List;

public interface InquiryService {


    List<InquiryResponseDTO> getInquires(String keyword);
}
