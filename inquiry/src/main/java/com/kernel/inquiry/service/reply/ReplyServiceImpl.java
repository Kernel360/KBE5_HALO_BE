package com.kernel.inquiry.service.reply;

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
        Inquiry inquiry = inquiryRepository.findById(request.getInquiryId())
                .orElseThrow(() -> new IllegalArgumentException("문의사항을 찾을 수 없습니다."));

        // 답변 생성
        replyRepository.save(request.toEntity(request, inquiry, authorId));
    }
}
