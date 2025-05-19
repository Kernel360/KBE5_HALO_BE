package com.kernel360.Halo.domain.customer.service;

import com.kernel360.Halo.domain.customer.entity.Inquiry;
import com.kernel360.Halo.domain.customer.repository.InquiryRepository;
import com.kernel360.Halo.web.customer.dto.response.InquiryResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class InquiryServiceImpl implements InquiryService {


    private final InquiryRepository inquiryRepository;

    @Override
    public List<InquiryResponseDTO> getInquires(String keyword) {
        List<Inquiry> inquiries;

        if ("all".equalsIgnoreCase(keyword)) {
            inquiries = inquiryRepository.findAll();
        } else {
            inquiries = inquiryRepository.findByTitleContainingIgnoreCase(keyword);
        }

        return inquiries.stream()
                .map(inquiry -> new InquiryResponseDTO(
                        inquiry.getId(),
                        inquiry.getAuthorId(),
                        inquiry.getTitle(),
                        inquiry.getContent(),
                        inquiry.getCreatedAt()
                        // 필요에 따라 필드 추가
                ))
                .collect(Collectors.toList());
    }

}
