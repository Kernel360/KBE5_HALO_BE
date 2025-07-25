package com.kernel.inquiry.service.inquiry;

import com.kernel.global.service.dto.response.EnumValueDTO;
import com.kernel.inquiry.repository.InquiryAdminRepository;
import com.kernel.inquiry.service.dto.request.InquiryAdminSearchReqDTO;
import com.kernel.inquiry.service.dto.response.InquiryAdminDetailRspDTO;
import com.kernel.inquiry.service.dto.response.InquiryAdminSummaryRspDTO;
import com.kernel.inquiry.service.info.InquiryAdminDetailInfo;
import com.kernel.inquiry.service.info.InquiryAdminSummaryInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class InquiryAdminIServiceImpl implements InquiryAdminIService {

    private final InquiryAdminRepository adminInquiryRepository;
    private final InquiryService inquiryService;

    /**
     * 문의사항 검색
     *
     * @param searchReqDTO  검색 조건을 포함하는 요청 DTO
     * @param pageable 페이징 정보
     * @return Page<InquirySummaryRspDTO>
     */
    @Transactional(readOnly = true)
    @Override
    public Page<InquiryAdminSummaryRspDTO> searchInquiries(InquiryAdminSearchReqDTO searchReqDTO, Pageable pageable) {

        Page<InquiryAdminSummaryInfo> inquirySummaryInfo = adminInquiryRepository.searchInquiriesWithPagination(searchReqDTO, pageable);

        return InquiryAdminSummaryRspDTO.fromInfo(inquirySummaryInfo);
    }

    /**
     * 문의사항 상세 조회
     *
     * @param inquiryId 문의사항 ID
     * @return InquiryDetailRspDTO
     */
    @Transactional(readOnly = true)
    @Override
    public InquiryAdminDetailRspDTO getInquiryDetails(Long inquiryId) {

        InquiryAdminDetailInfo foundInquiry = adminInquiryRepository.getInquiryDetails(inquiryId);

        return InquiryAdminDetailRspDTO.fromEntity(foundInquiry);
    }

    /**
     * 문의사항 카테고리 조회
     * @return 문의사항 카테고리 목록
     */
    @Override
    public Map<String, List<EnumValueDTO>> getAllInquiryCategoriesForAdmin() {

        Map<String, List<EnumValueDTO>> result = new HashMap<>();

        result.put("CUSTOMER", inquiryService.getCustomerInquiryCategory());
        result.put("MANAGER", inquiryService.getManagerInquiryCategory());

        return result;
    }


}


