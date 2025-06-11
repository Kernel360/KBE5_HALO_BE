package com.kernel.common.admin.service;

import com.kernel.common.admin.dto.mapper.AdminSearchMapper;
import com.kernel.common.admin.dto.request.AdminSearchReqDTO;
import com.kernel.common.admin.dto.response.AdminSearchRspDTO;
import com.kernel.common.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminAccountServiceImpl implements AdminAccountService {

    private final AdminRepository adminRepository;
    private final AdminSearchMapper adminSearchMapper;

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

        Page<AdminSearchRspDTO> adminPage = adminRepository.searchByConditionsWithPaging(request, pageable)
                .map(adminSearchMapper::toAdminSearchRspDTO);

        return adminPage;
    }
}
