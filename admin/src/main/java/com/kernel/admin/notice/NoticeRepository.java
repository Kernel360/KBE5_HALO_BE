package com.kernel.admin.notice;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoticeRepository extends  JpaRepository<Notice, Long> {
    List<Notice> findByNoticeType(NoticeType noticeType);
}
