package com.kernel.common.customer.repository;

import com.kernel.common.customer.entity.InquiryCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InquiryCategoryRepository extends JpaRepository<InquiryCategory, Long> {

    // 활성화되어 있는 카테고리 전체 조회
    List<InquiryCategory> findAllByIsActiveTrue();
}
