package com.kernel.common.manager.repository;

import com.kernel.common.manager.dto.response.ManagerReviewSummaryRspDTO;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomManagerReviewRepository {

    Page<ManagerReviewSummaryRspDTO> searchManagerReviewWithPaging (
        Long managerId,
        LocalDateTime fromCreatedAt,
        LocalDateTime toCreatedAt,
        Integer ratingOption,
        String customerNameKeyword,
        String contentKeyword,
        Pageable pageable
    );
}
