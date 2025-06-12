package com.kernel.common.manager.repository;

import com.kernel.common.admin.dto.request.AdminManagerSearchReqDTO;
import com.kernel.common.manager.entity.Manager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomManagerRepository {
    Page<Manager> searchManagers(AdminManagerSearchReqDTO request, Pageable pageable);
}
