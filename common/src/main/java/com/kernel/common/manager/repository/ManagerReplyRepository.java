package com.kernel.common.manager.repository;

import com.kernel.common.manager.entity.ManagerReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerReplyRepository extends JpaRepository<ManagerReply, Long> {
}
