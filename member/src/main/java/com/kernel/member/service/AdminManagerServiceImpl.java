package com.kernel.member.service;

import com.kernel.global.common.enums.UserStatus;
import com.kernel.member.domain.entity.AvailableTime;
import com.kernel.member.domain.entity.Manager;
import com.kernel.member.domain.entity.ManagerTermination;
import com.kernel.member.repository.AvailableTimeRepository;
import com.kernel.member.repository.ManagerRepository;
import com.kernel.member.repository.ManagerTerminationRepository;
import com.kernel.member.service.common.info.AdminManagerDetailInfo;
import com.kernel.member.service.common.info.ManagerDetailInfo;
import com.kernel.member.service.common.info.ManagerSummaryInfo;
import com.kernel.member.service.request.AdminManagerSearchReqDTO;
import com.kernel.member.service.response.AdminManagerRspDTO;
import com.kernel.member.service.response.AdminManagerSummaryRspDTO;

import com.kernel.member.service.response.AvailableTimeRspDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminManagerServiceImpl implements AdminManagerService {

    private final ManagerRepository managerRepository;
    private final AvailableTimeRepository availableTimeRepository;
    private final ManagerTerminationRepository managerTerminationRepository;

    /**
     * 전체 매니저 목록 조회
     * @param request 검색어
     * @param pageable 페이징 정보
     * @return 매니저 목록 페이지
     */
    @Transactional(readOnly = true)
    public Page<AdminManagerSummaryRspDTO> getManagers(AdminManagerSearchReqDTO request, Pageable pageable) {
        Page<ManagerSummaryInfo> managers = managerRepository.searchManagers(request, pageable);

        return AdminManagerSummaryRspDTO.toDTOList(managers);
    }

    /**
     * 선택한 매니저 상세 정보 조회
     * @param managerId 매니저 ID
     * @return 매니저 상세 정보 DTO
     */
    @Override
    @Transactional(readOnly = true)
    public AdminManagerRspDTO getManager(Long managerId) {
        // 1. 매니저 상세 정보 조회
        AdminManagerDetailInfo managerDetailInfo = managerRepository.getAdminManagerDetailInfo(managerId);

        // 2. 매니저의 사용 가능한 시간 목록 조회
        Manager manager = managerRepository.findById(managerId)
                .orElseThrow(() -> new NoSuchElementException("매니저를 찾을 수 없습니다."));

        List<AvailableTimeRspDTO> availableTimes = availableTimeRepository.findAllByManager(manager)
                .stream()
                .map(AvailableTimeRspDTO::fromEntity)
                .toList();

        // 3. 매니저 상세 정보 Info와 사용 가능한 시간 목록을 결합하여 AdminManagerRspDTO 생성
        return AdminManagerRspDTO.fromInfo(managerDetailInfo, availableTimes);
    }

    /**
     * 매니저 신청 승인/반려 처리
     * @param managerId 매니저 ID
     * @param status 처리 상태 (ACTIVE, REJECTED)
     */
    @Override
    @Transactional
    public void processAppliedManager(Long managerId, String status) {
        Manager manager = managerRepository.findById(managerId)
                .orElseThrow(() -> new NoSuchElementException("매니저를 찾을 수 없습니다."));

        if (status.equalsIgnoreCase("ACTIVE")) {
            manager.approve();
        } else if (status.equalsIgnoreCase("REJECTED")) {
            manager.reject();
        } else {
            throw new IllegalArgumentException("유효하지 않은 상태입니다.");
        }
    }

    /**
     * 매니저 계약 해지 처리
     * @param managerId 매니저 ID
     */
    @Override
    @Transactional
    public void terminateManager(Long managerId) {
        Manager manager = managerRepository.findById(managerId)
                .orElseThrow(() -> new NoSuchElementException("매니저를 찾을 수 없습니다."));

        ManagerTermination termination = managerTerminationRepository.findByManager(manager)
                .orElseThrow(() -> new NoSuchElementException("해당 매니저의 해지 정보를 찾을 수 없습니다."));

        manager.terminate();
        termination.updateTerminatedAt();
    }
}
