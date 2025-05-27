package com.kernel.customer.repository;

import com.kernel.customer.entity.Inquiry;

import java.util.List;

public interface InquiryRepositoryCustom {
    List<Inquiry> findByIdCustom(Long keyword);
}
