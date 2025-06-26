package com.kernel.admin.service;

import com.kernel.admin.service.dto.request.AdminSearchReqDTO;
import com.kernel.admin.service.dto.response.AdminSearchRspDTO;
import com.kernel.global.domain.entity.User;
import com.kernel.global.domain.info.AdminUserSearchInfo;
import com.kernel.global.repository.UserRepository;
import com.kernel.global.service.dto.condition.AdminUserSearchCondition;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminAccountServiceImpl implements AdminAccountService {

    private final UserRepository userRepository;

    /**
     * 관리자 목록을 검색하고 페이징 처리된 결과를 반환합니다.
     *
     * @param request  검색 조건을 포함하는 요청 DTO
     * @param pageable 페이징 정보
     * @return 검색된 관리자 목록과 페이징 정보가 포함된 Page 객체
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AdminSearchRspDTO> searchAdminList(AdminSearchReqDTO request, Pageable pageable) {
        AdminUserSearchCondition adminUserSearchCondition = request.toCondition();

        Page<AdminUserSearchInfo> adminPage = userRepository.searchByConditionsWithPaging(adminUserSearchCondition, pageable);


        return AdminSearchRspDTO.fromInfo(adminPage);
    }
}
