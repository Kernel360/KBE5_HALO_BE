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

    /*
     * 수요자 문의사항 목록 조회
     * @Param 수요자ID
     * @Param 검색 키워드
     * @Param Pageable 페이징
     * @Return 검색 게시글, 페이징
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

    /*
     * 수요자 문의사항 상세 조회
     * @Param 수요자ID
     * @Param 게시글ID
     * @Return 조회 게시글
     */
    @Override
    @Transactional(readOnly = true)
    public CustomerInquiryDetailRspDTO getCustomerInquiryDetails(Long customerId, Long inquiryId) {

        // 문의사항 조회
        CustomerInquiry inquiryRsp = customerInquiryRepository.getCustomerInquiryDetails(customerId, inquiryId)
                .orElseThrow(() -> new NoSuchElementException("문의사항이 존재하지 않습니다."));

        // 삭제 여부 확인
        inquiryRsp.validateDelete();

        return customerInquiryMapper.toDetailRspDTO(inquiryRsp);
    }

    /*
     * 수요자 문의사항 등록
     * @Param 수요자ID
     * @Param requestDTO
     * @Return 작성 게시글
     */
    @Override
    @Transactional
    public CustomerInquiryDetailRspDTO createCustomerInquiry(Long customerId, CustomerInquiryCreateReqDTO inquiryRequestDTO) {

        // category 조회
        InquiryCategory findCategory = findCategory(inquiryRequestDTO.getCategoryId());

        //RequestDTO -> entity
        CustomerInquiry makeEntity = customerInquiryMapper.toEntity(customerId, inquiryRequestDTO, findCategory);

        // 저장
        CustomerInquiry saveEntity = customerInquiryRepository.save(makeEntity);

        return getCustomerInquiryDetails(saveEntity.getAuthorId(), saveEntity.getInquiryId());
    }

    /*
     * 수요자 문의사항 수정
     * @Param requestDTO
     * @Return 수정 게시글
     */
    @Override
    @Transactional
    public CustomerInquiryDetailRspDTO updateCustomerInquiry(Long customerId, CustomerInquiryUpdateReqDTO inquiryRequestDTO) {

        // 게시글 조회
        CustomerInquiry findInquiry = findCustomerInquiry(inquiryRequestDTO.getInquiryId(), customerId);

        // 삭제 여부 확인
        findInquiry.validateDelete();

        // 답변 여부 확인
        findInquiry.validateReply();

        // 카테고리 조회
        InquiryCategory findCategory = findCategory(inquiryRequestDTO.getCategoryId());

        // 수정
        findInquiry.update(
                inquiryRequestDTO.getTitle(),       // 제목
                inquiryRequestDTO.getContent(),     // 내용
                findCategory);                      // 카테고리

        return customerInquiryMapper.toDetailRspDTO(findInquiry);
    }

    /*
     * 수요자 문의사항 삭제
     * @Param requestDTO
     */
    @Override
    @Transactional
    public void deleteCustomerInquiry(Long customerId, Long inquiryId) {

        // 조회
        CustomerInquiry findInquiry = findCustomerInquiry(inquiryId, customerId);

        // 삭제 여부 확인
        findInquiry.validateDelete();

        // 답변 여부 확인
        findInquiry.validateReply();

        // 삭제
        findInquiry.delete();
    }

    /*
     * 카테고리 조회
     * @Param 카테고리ID
     * @For 문의사항 등록, 수정
     */
    private InquiryCategory findCategory(Long categoryId) {
        return inquiryCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 카테고리 입니다."));
    }

    /*
     * 문의사항 조회
     * @Param 수요자ID
     * @Param 문의사항ID
     * @For 문의사항 수정, 삭제
     */
    private CustomerInquiry findCustomerInquiry(Long inquiryId, Long customerId) {
        return customerInquiryRepository.findByInquiryIdAndAuthorId(inquiryId, customerId)
                .orElseThrow(() -> new NoSuchElementException("문의사항이 존재하지 않거나 권한이 없습니다."));
    }

}
