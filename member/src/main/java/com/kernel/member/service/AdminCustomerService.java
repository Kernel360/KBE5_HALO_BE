package com.kernel.member.service;

import com.kernel.member.service.request.AdminCustomerSearchReqDTO;
import com.kernel.member.service.response.AdminCustomerSummaryRspDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminCustomerService {

    Page<AdminCustomerSummaryRspDTO> searchCustomers(AdminCustomerSearchReqDTO request, Pageable pageable);
    void deleteCustomer(Long customerId);
}