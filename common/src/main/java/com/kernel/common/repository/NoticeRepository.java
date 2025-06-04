package com.kernel.common.repository;

import com.kernel.common.global.entity.Notice;
import com.kernel.common.global.enums.NoticeType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    List<Notice> findByNoticeType(NoticeType type);
}
