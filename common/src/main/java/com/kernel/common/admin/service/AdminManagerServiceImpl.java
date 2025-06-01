package com.kernel.common.admin.service;

import com.kernel.common.admin.dto.mapper.AdminManagerMapper;
import com.kernel.common.admin.dto.response.AdminManagerRspDTO;
import com.kernel.common.admin.dto.response.AdminManagerSummaryRspDTO;
import com.kernel.common.global.enums.UserStatus;
import com.kernel.common.manager.entity.Manager;
import com.kernel.common.manager.repository.ManagerRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminManagerServiceImpl implements AdminManagerService {

    private final ManagerRepository managerRepository;
    private final AdminManagerMapper adminManagerMapper;

    // 전체 매니저 목록 조회
    @Transactional(readOnly = true)
    public Page<AdminManagerSummaryRspDTO> getManagers(String keyword, Pageable pageable) {

        if (keyword == null || keyword.isEmpty()) {
            return adminManagerMapper.toAdminManagerSummaryDTOList(managerRepository.findAll(pageable));
        } else {
            return adminManagerMapper.toAdminManagerSummaryDTOList(managerRepository.findByUserNameContaining(keyword, pageable));
        }
    }

    // 선택한 매니저 상세 정보 조회
    @Transactional(readOnly = true)
    @Override
    public AdminManagerRspDTO getManager(Long managerId) {
        return adminManagerMapper.toAdminManagerRspDTO(managerRepository.findById(managerId)
            .orElseThrow(() -> new NoSuchElementException("매니저를 찾을 수 없습니다.")));
    }

    // 매니저 신청 목록 조회
    @Transactional(readOnly = true)
    @Override
    public Page<AdminManagerSummaryRspDTO> getAppliedManagers(String keyword, Pageable pageable) {
        return adminManagerMapper.toAdminManagerSummaryDTOList(managerRepository.findByStatus(UserStatus.PENDING, pageable));
    }

    // 매니저 신청 승인 or 반려
    @Transactional
    @Override
    public void processAppliedManager(Long managerId, String status) {
        Manager manager = managerRepository.findById(managerId)
                .orElseThrow(() -> new NoSuchElementException("매니저를 찾을 수 없습니다."));

        if (status.equalsIgnoreCase("ACTIVE")) {
            manager.updateStatus(UserStatus.ACTIVE);
        } else if (status.equalsIgnoreCase("REJECTED")) {
            manager.updateStatus(UserStatus.REJECTED);
        } else {
            throw new IllegalArgumentException("유효하지 않은 상태입니다.");
        }
    }

    // 신고된 매니저 목록 조회
    @Transactional(readOnly = true)
    @Override
    public Page<AdminManagerSummaryRspDTO> getReportedManagers(String keyword, Pageable pageable) {
        Page<Manager> managers;

        if (keyword == null || keyword.isEmpty()) managers = managerRepository.findByStatus(UserStatus.SUSPENDED, pageable);
        else managers = managerRepository.findByUserNameContaining(keyword, pageable);

        return adminManagerMapper.toAdminManagerSummaryDTOList(managers);
    }

}
