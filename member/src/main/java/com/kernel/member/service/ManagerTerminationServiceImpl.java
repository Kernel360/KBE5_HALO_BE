package com.kernel.member.service;

import com.kernel.global.domain.entity.User;
import com.kernel.global.repository.UserRepository;
import com.kernel.member.domain.entity.Manager;
import com.kernel.member.domain.entity.ManagerTermination;
import com.kernel.member.repository.ManagerRepository;
import com.kernel.member.repository.ManagerTerminationRepository;
import com.kernel.member.service.common.info.ManagerTerminationInfo;
import com.kernel.member.service.request.ManagerTerminationReqDTO;
import com.kernel.member.service.response.ManagerTerminationRspDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ManagerTerminationServiceImpl implements ManagerTerminationService {

    private final UserRepository userRepository;
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
        User user = userRepository.findById(managerId)
                .orElseThrow(() -> new NoSuchElementException("매니저를 찾을 수 없습니다."));

        // 2. 매니저 조회
        Manager manager = managerRepository.findById(managerId)
                .orElseThrow(() -> new NoSuchElementException("매니저를 찾을 수 없습니다."));

        // 3. 사용자, 매니저 상태 변경
        manager.requestTermination();

        // 4. 매니저 계약 해지 요청 저장
        managerTerminationRepository.save(request.toEntity(manager, request));
    }

    /**
     * 매니저 계약 해지 정보 조회
     *
     * @param managerId 매니저 ID
     * @return 매니저 계약 해지 정보 DTO
     */
    @Override
    @Transactional(readOnly = true)
    public ManagerTerminationRspDTO getTerminationDetails(Long managerId) {
       // 1. 매니저 계약 해지 정보 조회
        ManagerTermination termination = managerTerminationRepository.findById(managerId)
                .orElseThrow(() -> new NoSuchElementException("매니저 계약 해지 정보를 찾을 수 없습니다."));

        // 2. 필요한 필드만 Info 클래스로 추출
        ManagerTerminationInfo terminationInfo = ManagerTerminationInfo.fromEntity(termination);

        // 3. Info 클래스를 RspDTO로 변환
        return ManagerTerminationRspDTO.fromInfo(terminationInfo);
    }
}
