package com.kernel.inquiry.repository;

import com.kernel.inquiry.domain.entity.Inquiry;
import com.kernel.inquiry.domain.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    // 답변 조회
    Reply findByInquiryId(Inquiry foundInquiry);
}
