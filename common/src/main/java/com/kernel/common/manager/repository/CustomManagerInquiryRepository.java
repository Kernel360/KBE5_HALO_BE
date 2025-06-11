package com.kernel.common.manager.repository;

import com.kernel.common.admin.dto.request.AdminInquirySearchReqDTO;
import com.kernel.common.global.enums.ReplyStatus;
import com.kernel.common.manager.entity.ManagerInquiry;
import com.querydsl.core.Tuple;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomManagerInquiryRepository {

    Page<Tuple> searchManagerinquiriesWithPaging (
        Long authorId,
        LocalDateTime fromCreatedAt,
        LocalDateTime toCreatedAt,
        ReplyStatus replyStatus,
        String titleKeyword,
        String contentKeyword,
        Pageable pageable
    );

    Page<ManagerInquiry> searchManagerInquiryWithReply(AdminInquirySearchReqDTO request, Pageable pageable);
}
