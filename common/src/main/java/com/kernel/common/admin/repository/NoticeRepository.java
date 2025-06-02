package com.kernel.common.admin.repository;

import com.kernel.common.admin.entity.AdminCustomer;
import com.kernel.common.admin.entity.Notice;
import com.kernel.common.admin.entity.NoticeType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    List<Notice> findByNoticeType(NoticeType type);
}
