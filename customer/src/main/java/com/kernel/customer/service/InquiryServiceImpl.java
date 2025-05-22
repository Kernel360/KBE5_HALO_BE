package com.kernel.customer.service;

import com.kernel.customer.dto.mapper.InquiryMapper;
import com.kernel.customer.dto.request.InquiryRequestDTO;
import com.kernel.customer.dto.response.InquiryResponseDTO;
import com.kernel.customer.entity.Inquiry;
import com.kernel.customer.repository.InquiryRepository;
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

        // keyword == null, 전체조회
        if(keyword == null || keyword.trim().isEmpty()) inquiries = inquiryRepository.findAll();
        else inquiries = inquiryRepository.findByTitleContainingIgnoreCase(keyword);

        return inquiryMapper.toResponseDTOList(inquiries);
    }

    @Transactional
    @Override
    public InquiryResponseDTO createInquiry(InquiryRequestDTO inquiryRequestDTO) {

        Inquiry inquiry = inquiryMapper.toEntity(inquiryRequestDTO);
        Inquiry saved = inquiryRepository.save(inquiry);


        return inquiryMapper.toResponseDTO(saved);

    }

    @Transactional
    @Override
    public InquiryResponseDTO updateInquiry(InquiryRequestDTO inquiryRequestDTO, Long inquiryId) {
        Inquiry inquiry = inquiryRepository.findById(inquiryId).orElseThrow(() -> new NoSuchElementException("문의글을 찾을 수 없습니다"));

        inquiry.update(inquiryRequestDTO.getTitle(), inquiryRequestDTO.getContent());

        return inquiryMapper.toResponseDTO(inquiry);
    }

    @Transactional
    @Override
    public void deleteInquiry(Long inquiryId) {
        inquiryRepository.deleteById(inquiryId);
    }

}
