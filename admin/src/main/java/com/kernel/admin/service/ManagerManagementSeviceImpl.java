package com.kernel.admin.service;

import com.kernel.admin.dto.mapper.AdminManagerStatusMapper;
import com.kernel.admin.dto.request.AdminManagerStatusRequestDTO;
import com.kernel.admin.dto.response.AdminManagerStatusResponseDTO;
import com.kernel.manager.dto.mapper.ManagerMapper;
import com.kernel.manager.dto.response.ManagerListResponseDTO;
import com.kernel.manager.dto.response.ManagerResponseDTO;
import com.kernel.manager.entity.Manager;
import com.kernel.manager.entity.Status;
import com.kernel.manager.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManagerManagementSeviceImpl implements ManagerManagementService {

    private final ManagerRepository managerRepository;
    private final ManagerMapper managerMapper;
    private final AdminManagerStatusMapper adminManagerStatusMapper;

    @Transactional(readOnly = true)
    @Override
    public List<ManagerListResponseDTO> getManagers(String keyword) {
        // TODO: 관리자 목록 조회 로직 구현
        List<Manager> managers;

        if (keyword == null || keyword.isEmpty()) managers = managerRepository.findAll();
        else managers = managerRepository.findByUserNameContaining(keyword); // 검색 쿼리는 이름 검색으로 임시 적용, 쿼리를 어떻게 작성할지는 논의 필요

        return managerMapper.toResponseDTOList(managers);
    }

    @Transactional(readOnly = true)
    @Override
    public ManagerResponseDTO getManager(Long managerId) {
        // TODO: 관리자 상세 조회 로직 구현
        return managerMapper.toResponseDTO(managerRepository.findById(managerId)
            .orElseThrow(() -> new NoSuchElementException("매니저를 찾을 수 없습니다.")));
    }

    @Transactional(readOnly = true)
    public List<ManagerListResponseDTO> getApplyManagers(String keyword) {
        // TODO: 매니저 신청 목록 조회 로직 구현
        return managerMapper.toResponseDTOList(managerRepository.findByStatus(Status.PENDING));
    }

    @Transactional
    @Override
    public AdminManagerStatusResponseDTO approveManager(Long managerId, AdminManagerStatusRequestDTO adminManagerStatusRequestDTO) {
        // TODO: 관리자 승인 로직 구현
        Manager manager = managerRepository.findById(managerId)
                .orElseThrow(() -> new NoSuchElementException("매니저를 찾을 수 없습니다."));

        manager.updateStatus(Status.ACTIVE);

        return adminManagerStatusMapper.toResponseDTO(manager);
    }

    @Transactional
    @Override
    public AdminManagerStatusResponseDTO rejectManager(Long managerId, AdminManagerStatusRequestDTO adminManagerStatusRequestDTO) {
        // TODO: 관리자 거절 로직 구현
        Manager manager = managerRepository.findById(managerId)
                .orElseThrow(() -> new NoSuchElementException("매니저를 찾을 수 없습니다."));
        manager.updateStatus(Status.REJECTED);

        return adminManagerStatusMapper.toResponseDTO(manager);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ManagerListResponseDTO> getReportedManagers(String keyword) {
        // TODO: 신고된 관리자 목록 조회 로직 구현
        List<Manager> managers;

        if (keyword == null || keyword.isEmpty()) managers = managerRepository.findAll();
        else managers = managerRepository.findByUserNameContaining(keyword);

        return managerMapper.toResponseDTOList(managers);
    }

    @Transactional
    @Override
    public ManagerResponseDTO setSuspendedManager(Long managerId) {
        // TODO: 블랙리스트 관리자 설정 로직 구현
        Manager manager = managerRepository.findById(managerId)
                .orElseThrow(() -> new NoSuchElementException("매니저를 찾을 수 없습니다."));

        manager.updateStatus(Status.SUSPENDED);

        return managerMapper.toResponseDTO(manager);
    }
}
