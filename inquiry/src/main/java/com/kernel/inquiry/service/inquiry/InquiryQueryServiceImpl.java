package com.kernel.inquiry.service.inquiry;

import com.kernel.inquiry.domain.info.InquiryDetailInfo;
import com.kernel.inquiry.domain.info.InquirySummaryInfo;
import com.kernel.inquiry.domain.info.ReplyInfo;
import com.kernel.inquiry.repository.InquiryRepository;
import com.kernel.inquiry.repository.ReplyRepository;
import com.kernel.inquiry.service.dto.request.InquirySearchReqDTO;
import com.kernel.inquiry.service.dto.response.InquiryDetailRspDTO;
import com.kernel.inquiry.service.dto.response.InquirySummaryRspDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class InquiryQueryServiceImpl implements InquiryQueryService {

    private final InquiryRepository inquiryRepository;
    private final ReplyRepository replyRepository;

    @Transactional(readOnly = true)
    @Override
    public Page<InquirySummaryRspDTO> searchInquiries(InquirySearchReqDTO request, Long authorId,Pageable pageable) {
        Page<InquirySummaryInfo> inquirySummaryInfo = inquiryRepository.searchInquiriesWithPagination(request, authorId, pageable);

        return InquirySummaryRspDTO.fromInfo(inquirySummaryInfo);
    }

    @Transactional(readOnly = true)
    @Override
    public InquiryDetailRspDTO getInquiryDetails(Long inquiryId) {
        InquiryDetailInfo inquiryDetailInfo = inquiryRepository.findInfoByInquiryId(inquiryId)
                .orElseThrow(() -> new NoSuchElementException("문의사항을 찾을 수 없습니다."));

        ReplyInfo replyInfo = replyRepository.findInfoByInquiryId_InquiryId(inquiryId)
                .orElseThrow(() -> new NoSuchElementException("문의사항을 찾을 수 없습니다."));

        return InquiryDetailRspDTO.fromInfo(inquiryDetailInfo, replyInfo);
    }
}
