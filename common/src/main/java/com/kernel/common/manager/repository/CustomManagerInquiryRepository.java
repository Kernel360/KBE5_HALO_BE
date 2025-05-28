package com.kernel.common.manager.repository;

import com.querydsl.core.Tuple;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomManagerInquiryRepository {

    Page<Tuple> searchManagerinquiriesWithPaging (
        Long authorId,
        LocalDateTime fromCreatedAt,
        LocalDateTime toCreatedAt,
        String replyStatus,
        String titleKeyword,
        String contentKeyword,
        Pageable pageable
    );
}
