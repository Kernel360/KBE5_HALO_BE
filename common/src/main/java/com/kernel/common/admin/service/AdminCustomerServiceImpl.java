package com.kernel.common.admin.service;

import com.kernel.common.admin.dto.mapper.CustomerMapper;
import com.kernel.common.admin.dto.response.AdminCustomerResDto;
import com.kernel.common.admin.repository.AdminCustomerRepository;
import com.kernel.common.customer.entity.Customer;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminCustomerServiceImpl implements AdminCustomerService {

    private final AdminCustomerRepository repository;

    public List<AdminCustomerResDto> getAllCustomers(String keyword) {
        List<Customer> customer;

        if (keyword != null && !keyword.isEmpty()) {
            customer = repository.findByPhone(keyword);
        } else {
            customer = repository.findAll();
        }

        return customer.stream()
                .map(CustomerMapper::from)
                .collect(Collectors.toList());
    }

    public AdminCustomerResDto getCustomerDetail(Long customerId) {
        Customer customer = repository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 수요자입니다."));
        return CustomerMapper.from(customer);
    }
}
