package com.kernel.common.manager.repository;

import com.kernel.common.manager.entity.ManagerInquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerInquiryRepository extends JpaRepository<ManagerInquiry, Long>, CustomManagerInquiryRepository {
    
    // 게시글ID와 작성자ID(=매니저ID)로 조회
    ManagerInquiry findByInquiryIdAndAuthorId(Long inquiryId, Long authorId);
}
