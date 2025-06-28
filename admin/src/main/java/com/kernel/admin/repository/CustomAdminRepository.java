package com.kernel.admin.repository;

import com.kernel.global.domain.info.AdminUserSearchInfo;
import com.kernel.global.service.dto.condition.AdminUserSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomAdminRepository {
    Page<AdminUserSearchInfo> searchByConditionsWithPaging(AdminUserSearchCondition request, Pageable pageable);
}
