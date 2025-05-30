package com.kernel.common.customer.service;


import com.kernel.common.customer.dto.mapper.CustomerInquiryMapper;
import com.kernel.common.customer.dto.response.CustomerInquiryDetailRspDTO;
import com.kernel.common.customer.dto.response.CustomerInquiryRspDTO;
import com.kernel.common.customer.entity.CustomerInquiry;
import com.kernel.common.customer.repository.CustomerInquiryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerInquiryServiceImpl  implements CustomerInquiryService {


    private final CustomerInquiryRepository inquiryRepository;
    private final CustomerInquiryMapper inquiryMapper;

    /*
     * 수요자 문의사항 목록 조회
     * @Param 수요자ID
     * @Param 검색 키워드
     * @Param Pageable 페이징
     * @Return 검색 키워드에 맞는 게시글 목록
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CustomerInquiryRspDTO> searchCustomerInquiries(
            Long customerId,
            String keyword,
            Pageable pageable
    ) {
        Page<CustomerInquiry> inquiryPage = inquiryRepository.searchByCustomerIdAndKeyword(customerId, keyword, pageable);

        List<CustomerInquiryRspDTO> dtoList = inquiryPage.getContent().stream()
                .map(inquiryMapper::toRspDTO)
                .toList();

        return new PageImpl<>(dtoList, pageable, inquiryPage.getTotalElements());
    }

    /*
     * 수요자 문의사항 상세 조회
     * @Param 수요자ID
     * @Return 수요자가 작성한 게시글
     */
    @Override
    @Transactional(readOnly = true)
    public CustomerInquiryDetailRspDTO getCustomerInquiryDetails(Long customerId, Long inquiryId) {

        CustomerInquiry inquiryRsp = inquiryRepository.getCustomerInquiryDetails(customerId, inquiryId)
                .orElseThrow(() -> new NoSuchElementException("게시글이 존재하지 않습니다."));

        if(inquiryRsp.getIsDeleted()) throw new NoSuchElementException("이미 삭제된 게시글 입니다.");

        return inquiryMapper.toDetailRspDTO(inquiryRsp);
    }

/*
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
*/
}
