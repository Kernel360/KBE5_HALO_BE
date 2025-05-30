package com.kernel.common.admin.service;


import com.kernel.common.admin.dto.mapper.ManagerMapper;
import com.kernel.common.admin.dto.response.AdminManagerSummaryRspDTO;
import com.kernel.common.admin.dto.response.ManagerRspDTO;
import com.kernel.common.admin.entity.Manager;
import com.kernel.common.admin.entity.Status;
import com.kernel.common.admin.repository.ManagerRepository;
import com.kernel.common.global.enums.UserStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminManagerServiceImpl implements AdminManagerService {

    private final ManagerRepository managerRepository;
    private final ManagerMapper managerMapper;

    @Transactional(readOnly = true)
    @Override
    public List<AdminManagerSummaryRspDTO> getManagers(String keyword) {
        // TODO: 관리자 목록 조회 로직 구현
        List<Manager> managers;

        if (keyword == null || keyword.isEmpty()) managers = managerRepository.findAll();
        else managers = managerRepository.findByUserNameContaining(keyword); // 검색 쿼리는 이름 검색으로 임시 적용, 쿼리를 어떻게 작성할지는 논의 필요

        return managerMapper.toAdminResponseDTOList(managers);
    }

    @Transactional(readOnly = true)
    @Override
    public ManagerRspDTO getManager(Long managerId) {
        // TODO: 관리자 상세 조회 로직 구현
        return managerMapper.toResponseDTO(managerRepository.findById(managerId)
            .orElseThrow(() -> new NoSuchElementException("매니저를 찾을 수 없습니다.")));
    }

    @Transactional(readOnly = true)
    public List<AdminManagerSummaryRspDTO> getApplyManagers(String keyword) {
        // TODO: 매니저 신청 목록 조회 로직 구현
        return managerMapper.toAdminResponseDTOList(managerRepository.findByStatus(Status.PENDING));
    }

    @Transactional
    @Override
    public void processAppliedManager(Long managerId, String status) {
        // TODO: 관리자 승인 로직 구현
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

    @Transactional(readOnly = true)
    @Override
    public List<AdminManagerSummaryRspDTO> getReportedManagers(String keyword) {
        // TODO: 신고된 관리자 목록 조회 로직 구현
        List<Manager> managers;

        if (keyword == null || keyword.isEmpty()) managers = managerRepository.findByStatus(Status.SUSPENDED);
        else managers = managerRepository.findByUserNameContaining(keyword);

        return managerMapper.toAdminResponseDTOList(managers);
    }

    @Transactional
    @Override
    public ManagerRspDTO suspendManager(Long managerId) {
        // TODO: 블랙리스트 관리자 설정 로직 구현
        Manager manager = managerRepository.findById(managerId)
                .orElseThrow(() -> new NoSuchElementException("매니저를 찾을 수 없습니다."));

        manager.updateStatus(UserStatus.SUSPENDED);

        return managerMapper.toResponseDTO(manager);
    }
}
