package com.kernel.common.customer.service;


import com.kernel.common.customer.dto.mapper.CustomerInquiryMapper;
import com.kernel.common.customer.dto.request.CustomerInquiryCreateReqDTO;
import com.kernel.common.customer.dto.request.CustomerInquiryUpdateReqDTO;
import com.kernel.common.customer.dto.response.CustomerInquiryDetailRspDTO;
import com.kernel.common.customer.dto.response.CustomerInquiryRspDTO;
import com.kernel.common.customer.entity.CustomerInquiry;
import com.kernel.common.customer.entity.InquiryCategory;
import com.kernel.common.customer.repository.CustomerInquiryRepository;
import com.kernel.common.customer.repository.InquiryCategoryRepository;
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


    private final CustomerInquiryRepository customerInquiryRepository;
    private final InquiryCategoryRepository inquiryCategoryRepository;
    private final CustomerInquiryMapper customerInquiryMapper;

    /**
     * 수요자 문의사항 목록 조회
     * @param customerId 수요자 ID
     * @param keyword 검색 키워드
     * @param pageable 페이징 정보
     * @return 검색된 문의사항 목록 (페이징 포함)
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CustomerInquiryRspDTO> searchCustomerInquiries(
            Long customerId,
            String keyword,
            Pageable pageable
    ) {
        Page<CustomerInquiry> inquiryPage = customerInquiryRepository.searchByAuthorIdAndKeyword(customerId, keyword, pageable);

        List<CustomerInquiryRspDTO> dtoList = inquiryPage.getContent().stream()
                .map(customerInquiryMapper::toRspDTO)
                .toList();

        return new PageImpl<>(dtoList, pageable, inquiryPage.getTotalElements());
    }

    /**
     * 수요자 문의사항 상세 조회
     * @param customerId 수요자 ID
     * @param inquiryId 문의사항 ID
     * @return 상세 조회된 문의사항 정보
     */
    @Override
    @Transactional(readOnly = true)
    public CustomerInquiryDetailRspDTO getCustomerInquiryDetails(Long customerId, Long inquiryId) {
        CustomerInquiry inquiryRsp = customerInquiryRepository.getCustomerInquiryDetails(customerId, inquiryId)
                .orElseThrow(() -> new NoSuchElementException("문의사항이 존재하지 않습니다."));

        inquiryRsp.validateDelete();

        return customerInquiryMapper.toDetailRspDTO(inquiryRsp);
    }

    /**
     * 수요자 문의사항 등록
     * @param customerId 수요자 ID
     * @param inquiryRequestDTO 문의사항 등록 요청 데이터
     * @return 등록된 문의사항 정보
     */
    @Override
    @Transactional
    public CustomerInquiryDetailRspDTO createCustomerInquiry(Long customerId, CustomerInquiryCreateReqDTO inquiryRequestDTO) {
        InquiryCategory findCategory = findCategory(inquiryRequestDTO.getCategoryId());
        CustomerInquiry makeEntity = customerInquiryMapper.toEntity(customerId, inquiryRequestDTO, findCategory);
        CustomerInquiry saveEntity = customerInquiryRepository.save(makeEntity);

        return getCustomerInquiryDetails(saveEntity.getAuthorId(), saveEntity.getInquiryId());
    }

    /**
     * 수요자 문의사항 수정
     * @param customerId 수요자 ID
     * @param inquiryRequestDTO 문의사항 수정 요청 데이터
     * @return 수정된 문의사항 정보
     */
    @Override
    @Transactional
    public CustomerInquiryDetailRspDTO updateCustomerInquiry(Long customerId, CustomerInquiryUpdateReqDTO inquiryRequestDTO) {
        CustomerInquiry findInquiry = findCustomerInquiry(inquiryRequestDTO.getInquiryId(), customerId);
        findInquiry.validateDelete();
        findInquiry.validateReply();
        InquiryCategory findCategory = findCategory(inquiryRequestDTO.getCategoryId());

        findInquiry.update(
                inquiryRequestDTO.getTitle(),
                inquiryRequestDTO.getContent(),
                findCategory
        );

        return customerInquiryMapper.toDetailRspDTO(findInquiry);
    }

    /**
     * 수요자 문의사항 삭제
     * @param customerId 수요자 ID
     * @param inquiryId 문의사항 ID
     */
    @Override
    @Transactional
    public void deleteCustomerInquiry(Long customerId, Long inquiryId) {
        CustomerInquiry findInquiry = findCustomerInquiry(inquiryId, customerId);
        findInquiry.validateDelete();
        findInquiry.validateReply();
        findInquiry.delete();
    }

    /**
     * 카테고리 조회 (등록/수정 시 사용)
     * @param categoryId 카테고리 ID
     * @return 조회된 카테고리
     */
    private InquiryCategory findCategory(Long categoryId) {
        return inquiryCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 카테고리 입니다."));
    }

    /**
     * 문의사항 조회 (수정/삭제 시 사용)
     * @param inquiryId 문의사항 ID
     * @param customerId 수요자 ID
     * @return 조회된 문의사항
     */
    private CustomerInquiry findCustomerInquiry(Long inquiryId, Long customerId) {
        return customerInquiryRepository.findByInquiryIdAndAuthorId(inquiryId, customerId)
                .orElseThrow(() -> new NoSuchElementException("문의사항이 존재하지 않거나 권한이 없습니다."));
    }


}
