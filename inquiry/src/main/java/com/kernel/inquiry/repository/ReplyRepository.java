package com.kernel.inquiry.repository;

import com.kernel.inquiry.domain.entity.Reply;
import com.kernel.inquiry.domain.info.ReplyInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    Optional<ReplyInfo> findInfoByInquiryId_InquiryId(Long inquiryId);
}
