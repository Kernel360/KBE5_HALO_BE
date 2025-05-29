package com.kernel.common.manager.repository;

import com.kernel.common.enums.ReplyStatus;
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
}
