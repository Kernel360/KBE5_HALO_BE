package com.kernel.member.service;

import com.kernel.global.common.enums.ErrorCode;
import com.kernel.global.common.enums.UserStatus;
import com.kernel.global.common.exception.AuthException;
import com.kernel.global.domain.entity.User;
import com.kernel.global.repository.UserRepository;
import com.kernel.member.repository.CustomerRepository;
import com.kernel.member.service.common.info.AdminCustomerSummaryInfo;
import com.kernel.member.service.request.AdminCustomerSearchReqDTO;
import com.kernel.member.service.response.AdminCustomerSummaryRspDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminCustomerServiceImpl implements AdminCustomerService {

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;

    /**
     * 관리자 고객 검색 기능을 구현합니다.
     *
     * @param request 검색 요청 DTO
     * @return 고객 정보 페이지
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AdminCustomerSummaryRspDTO> searchCustomers(AdminCustomerSearchReqDTO request, Pageable pageable) {
        Page<AdminCustomerSummaryInfo> searchResult = customerRepository.searchCustomers(request, pageable);

        return AdminCustomerSummaryRspDTO.toDTOPage(searchResult);
    }

    /**
     * 관리자 고객 삭제 기능을 구현합니다.
     *
     * @param customerId 삭제할 고객 ID
     */
    @Override
    @Transactional
    public void deleteCustomer(Long customerId) {
        // 1. 고객이 존재하는지 확인
        User customer = userRepository.findByUserIdAndStatus(customerId, UserStatus.ACTIVE)
                .orElseThrow(() -> new AuthException(ErrorCode.USER_NOT_FOUND));

        // 2. 고객의 상태를 비활성화로 변경
        customer.delete();
    }
}
