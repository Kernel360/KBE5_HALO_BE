package com.kernel.member.service;

import com.kernel.global.common.enums.UserStatus;
import com.kernel.global.domain.entity.User;
import com.kernel.member.domain.entity.Manager;
import com.kernel.member.repository.ManagerRepository;
import com.kernel.member.repository.ManagerTerminationRepository;
import com.kernel.member.service.common.UserService;
import com.kernel.member.service.request.ManagerTerminationReqDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ManagerTerminationServiceImpl implements ManagerTerminationService {

    private final UserService userService;
    private final ManagerRepository managerRepository;
    private final ManagerTerminationRepository managerTerminationRepository;

    /**
     * 매니저 계약 해지 요청 처리
     *
     * @param managerId 매니저 ID
     * @param request   계약 해지 요청 DTO
     */
    @Override
    @Transactional
    public void requestTermination(Long managerId, ManagerTerminationReqDTO request) {

        // 1. 사용자 조회
        User user = userService.getByUserIdAndStatus(managerId, UserStatus.ACTIVE);

        // 2. 매니저 조회
        Manager manager = managerRepository.findById(managerId)
                .orElseThrow(() -> new NoSuchElementException("매니저를 찾을 수 없습니다."));

        // 3. 사용자, 매니저 상태 변경
        manager.requestTermination();

        // 4. 매니저 계약 해지 요청 저장
        managerTerminationRepository.save(request.toEntity(manager, request));
    }
}
