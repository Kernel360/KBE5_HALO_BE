package com.kernel.inquiry.service.reply;

import com.kernel.inquiry.service.dto.request.ReplyCreateReqDTO;

public interface ReplyService {

    // 답변 생성
    void createReply(ReplyCreateReqDTO request, Long authorId);
}
