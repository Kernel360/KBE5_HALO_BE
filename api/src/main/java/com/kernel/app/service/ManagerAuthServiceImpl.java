package com.kernel.app.service;

import com.kernel.app.dto.mapper.ManagerAuthMapper;
import com.kernel.app.exception.custom.DuplicateUserException;
import com.kernel.app.repository.ManagerAuthRepository;
import com.kernel.common.manager.dto.request.ManagerSignupReqDTO;
import com.kernel.common.manager.dto.request.ManagerTerminationReqDTO;
import com.kernel.common.manager.dto.request.ManagerUpdateReqDTO;
import com.kernel.common.manager.dto.response.ManagerInfoRspDTO;
import com.kernel.common.manager.entity.Manager;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManagerAuthServiceImpl implements ManagerAuthService {

    private final ManagerAuthRepository authRepository;
    private final ManagerAuthMapper authMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 매니저 회원가입
     * @param signupReqDTO 회원가입 요청 DTO
     */
    @Override
    @Transactional
    public void signup(ManagerSignupReqDTO signupReqDTO) {
        try {
            // 연락처 중복 체크
            if(authRepository.existsByPhone(signupReqDTO.getPhone())) {
                throw new DuplicateUserException();
            }
            
            // 저장
            authRepository.save(authMapper.toEntity(signupReqDTO));

        } catch (DuplicateUserException e) {
            log.warn("Attempted to register duplicate manager: {}", signupReqDTO.getPhone());
            throw e; // 글로벌 핸들러에서 처리

        } catch (Exception e) {
            log.error("Unexpected error during manager registration: {}", e.getMessage(), e);
            throw new RuntimeException("회원가입 처리 중 오류가 발생했습니다.", e);

        }
    }

    /**
     * 매니저 정보 조회
     * @param managerId 매니저ID
     * @return 매니저 정보를 담은 응답 DTO
     */
    @Override
    @Transactional(readOnly = true)
    public ManagerInfoRspDTO getManager(Long managerId) {

        // Entity 조회
        Manager foundManager = authRepository.findById(managerId)
            .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 매니저입니다."));

        // Entity -> ResponseDTO 후, return
        return authMapper.toMangerInfoRspDTO(foundManager);
    }

    /**
     * 매니저 정보 수정
     * @param managerId 매니저ID
     * @param updateReqDTO 매니저 정보 수정 요청 DTO
     */
    @Override
    @Transactional
    public void updateManager(Long managerId, ManagerUpdateReqDTO updateReqDTO) {

        // Entity 조회
        Manager foundManager = authRepository.findById(managerId)
            .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 매니저입니다."));

        // 기존 비밀번호 일치 확인
        if (!bCryptPasswordEncoder.matches(updateReqDTO.getCurrentPassword(), foundManager.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }

        // 암호화된 새 비밀번호와 함께 매니저 정보 수정
        foundManager.updateManager(updateReqDTO, bCryptPasswordEncoder.encode(updateReqDTO.getNewPassword()));
    }

    /**
     * 매니저 계약 해지 요청
     * @param managerId 매니저ID
     * @param terminationReqDTO 매니저 계약 해지 요청 DTO
     */
    @Override
    @Transactional
    public void requestManagerTermination(Long managerId, ManagerTerminationReqDTO terminationReqDTO) {

        // Entity 조회
        Manager foundManager = authRepository.findById(managerId)
            .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 매니저입니다."));

        // 매니저 계약 해지 요청
        foundManager.requestTermination(terminationReqDTO);
    }
}
