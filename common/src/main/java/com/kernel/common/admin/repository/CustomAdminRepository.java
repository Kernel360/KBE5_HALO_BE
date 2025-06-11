package com.kernel.common.admin.repository;

import com.kernel.common.admin.dto.request.AdminSearchReqDTO;
import com.kernel.common.admin.entity.Admin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomAdminRepository {
    Page<Admin> searchByConditionsWithPaging(AdminSearchReqDTO request, Pageable pageable);
}
