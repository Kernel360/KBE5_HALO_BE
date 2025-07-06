package com.kernel.inquiry.service.inquiry;

import com.kernel.global.domain.entity.User;
import com.kernel.global.repository.UserRepository;
import com.kernel.inquiry.common.enums.InquiryErrorCode;
import com.kernel.inquiry.common.exception.InquiryNotFoundException;
import com.kernel.inquiry.domain.entity.Inquiry;
import com.kernel.inquiry.domain.entity.Reply;
import com.kernel.inquiry.repository.InquiryAdminRepository;
import com.kernel.inquiry.repository.ReplyRepository;
import com.kernel.inquiry.service.dto.request.InquiryAdminSearchReqDTO;
import com.kernel.inquiry.service.dto.response.InquiryAdminDetailRspDTO;
import com.kernel.inquiry.service.dto.response.InquirySummaryRspDTO;
import com.kernel.inquiry.service.info.InquirySummaryInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class InquiryAdminIServiceImpl implements InquiryAdminIService {

    private final InquiryAdminRepository adminInquiryRepository;
    private final ReplyRepository replyRepository;
    private final UserRepository userRepository;

    /**
     * 문의사항 검색
     *
     * @param searchReqDTO  검색 조건을 포함하는 요청 DTO
     * @param pageable 페이징 정보
     * @return Page<InquirySummaryRspDTO>
     */
    @Transactional(readOnly = true)
    @Override
    public Page<InquirySummaryRspDTO> searchInquiries(InquiryAdminSearchReqDTO searchReqDTO, Pageable pageable) {

        Page<InquirySummaryInfo> inquirySummaryInfo = adminInquiryRepository.searchInquiriesWithPagination(searchReqDTO, pageable);

        return InquirySummaryRspDTO.fromInfo(inquirySummaryInfo);
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

        // 1. 문의사항 조회
        Inquiry foundInquiry = adminInquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new InquiryNotFoundException(InquiryErrorCode.INQUIRY_NOT_FOUND));

        // 2. 답변 조회
        Reply foundReply = replyRepository.findByInquiryId(foundInquiry).orElse(null);

        // 3. 작성자 조회
        User author = userRepository.findById(foundInquiry.getAuthorId()).orElse(null);

        return InquiryAdminDetailRspDTO.fromEntity(foundInquiry, foundReply, author);
    }

}
