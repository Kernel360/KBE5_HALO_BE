package com.kernel.member.repository;

import com.kernel.member.service.common.info.AdminCustomerSummaryInfo;
import com.kernel.member.service.request.AdminCustomerSearchReqDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomCustomerRepository {
    Page<AdminCustomerSummaryInfo> searchCustomers(AdminCustomerSearchReqDTO request, Pageable pageable);
}
