package com.kernel360.Halo.domain.customer.service;

import com.kernel360.Halo.domain.customer.entity.Inquiry;
import com.kernel360.Halo.domain.customer.mapper.InquiryMapper;
import com.kernel360.Halo.domain.customer.repository.InquiryRepository;
import com.kernel360.Halo.web.customer.dto.request.InquiryRequestDTO;
import com.kernel360.Halo.web.customer.dto.response.InquiryResponseDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class InquiryServiceImpl implements InquiryService {


    private final InquiryRepository inquiryRepository;
    private final InquiryMapper inquiryMapper;

    @Override
    public List<InquiryResponseDTO> getInquiries(String keyword) {
        List<Inquiry> inquiries;

        if("all".equalsIgnoreCase(keyword)) inquiries = inquiryRepository.findAll();
        else inquiries = inquiryRepository.findByTitleContainingIgnoreCase(keyword);

        return inquiryMapper.toResponseDTOList(inquiries);
    }

    @Transactional
    @Override
    public InquiryResponseDTO createInquiry(InquiryRequestDTO inquiryRequestDTO) {

        Inquiry inquiry = inquiryMapper.toEntity(inquiryRequestDTO);
        System.out.println("mapper : "+inquiry);
        Inquiry saved = inquiryRepository.save(inquiry);
        System.out.println("saved  : "+saved);


        return inquiryMapper.toResponseDTO(saved);

    }

    @Transactional
    @Override
    public InquiryResponseDTO updateInquiry(InquiryRequestDTO inquiryRequestDTO, Long inquiryId) {
        Inquiry inquiry = inquiryRepository.findById(inquiryId).orElseThrow(() -> new NoSuchElementException("문의글을 찾을 수 없습니다"));

        inquiry.updateContent(inquiryRequestDTO.getTitle(), inquiryRequestDTO.getContent());

        return inquiryMapper.toResponseDTO(inquiry);
    }

    @Transactional
    @Override
    public void deleteInquiry(Long inquiryId) {
        inquiryRepository.deleteById(inquiryId);
    }

}
