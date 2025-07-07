package com.kernel.inquiry.service.reply;

import com.kernel.inquiry.common.enums.InquiryErrorCode;
import com.kernel.inquiry.common.exception.InquiryNotFoundException;
import com.kernel.inquiry.domain.entity.Inquiry;
import com.kernel.inquiry.repository.InquiryRepository;
import com.kernel.inquiry.repository.ReplyRepository;
import com.kernel.inquiry.service.dto.request.ReplyCreateReqDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    private final InquiryRepository inquiryRepository;
    private final ReplyRepository replyRepository;

    @Transactional
    @Override
    public void createReply(ReplyCreateReqDTO request, Long authorId) {

        // 1. 문의 조회
        Inquiry inquiry = inquiryRepository.findById(request.getInquiryId())
                .orElseThrow(() -> new InquiryNotFoundException(InquiryErrorCode.INQUIRY_NOT_FOUND));

        // 2. 답변 생성
        replyRepository.save(request.toEntity(request, inquiry, authorId));

        // 3. 문의 사항 답변 체크
        inquiry.replied();

    }
}
