package com.kernel.evaluation.repository.review;

import com.kernel.evaluation.domain.info.ManagerReviewInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface CustomManagerReviewRepository {

    Page<ManagerReviewInfo> searchManagerReviewWithPaging (
        Long managerId,
        LocalDateTime fromCreatedAt,
        LocalDateTime toCreatedAt,
        Integer ratingOption,
        String customerNameKeyword,
        String contentKeyword,
        Pageable pageable
    );
}
