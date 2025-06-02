package com.kernel.common.customer.repository;

import com.kernel.common.customer.entity.Customer;
import com.kernel.common.customer.entity.CustomerInquiry;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface CustomerInquiryRepository extends JpaRepository<CustomerInquiry, Long>, CustomerInquiryRepositoryCustom {

    // 문의사항 조회(where inquiry and authorId)
    Optional<CustomerInquiry> findByInquiryIdAndAuthorId(@NotNull Long inquiryId, Long customerId);

    List<Customer> findByPhone(String phone);
}
