package com.kernel.common.admin.service;

import com.kernel.common.admin.dto.response.AdminCustomerResDto;
import com.kernel.common.admin.entity.AdminCustomer;
import com.kernel.common.admin.repository.AdminCustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminCustomerService {
    private final AdminCustomerRepository repository;

    public AdminCustomerService(AdminCustomerRepository repository) {
        this.repository = repository;
    }

    public List<AdminCustomerResDto> getAllCustomers(String keyword) {
        List<AdminCustomer> customers;

        if (keyword != null && !keyword.isEmpty()) {
            customers = repository.findByCustomerNameContaining(keyword);
        } else {
            customers = repository.findAll();
        }

        return customers.stream()
                .map(AdminCustomerResDto::from)
                .collect(Collectors.toList());
    }



    public AdminCustomerResDto getCustomerDetail(Long customerId) {
        AdminCustomer customer = repository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 수요자입니다."));
        return AdminCustomerResDto.from(customer);
    }
}
