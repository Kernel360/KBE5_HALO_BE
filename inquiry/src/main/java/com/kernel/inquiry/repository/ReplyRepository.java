package com.kernel.inquiry.repository;

import com.kernel.inquiry.domain.entity.Inquiry;
import com.kernel.inquiry.domain.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    // 답변 조회
    Optional<Reply> findByInquiryId(Inquiry foundInquiry);

}
