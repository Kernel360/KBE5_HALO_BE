package com.kernel.common.admin.service;

import com.kernel.common.admin.dto.response.AdminCustomerResDto;

import java.util.List;

public interface AdminCustomerService {

    List<AdminCustomerResDto> getAllCustomers(String keyword);

    AdminCustomerResDto getCustomerDetail(Long customerId);
}