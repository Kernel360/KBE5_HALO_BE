package com.kernel.app.service;

import com.kernel.app.dto.mapper.AdminAuthMapper;
import com.kernel.app.exception.custom.DuplicateUserException;
import com.kernel.app.repository.AdminAuthRepository;
import com.kernel.common.admin.dto.request.AdminSignupReqDTO;
import com.kernel.common.admin.dto.request.AdminUpdateReqDTO;
import com.kernel.common.admin.entity.Admin;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminAuthServiceImpl implements AdminAuthService {

    private final AdminAuthRepository authRepository;
    private final AdminAuthMapper authMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional
    public void join(AdminSignupReqDTO joinDTO) {

        try{
            String phone = joinDTO.getPhone();

            if(authRepository.existsByPhone(phone))
                throw new DuplicateUserException();

            authRepository.save(authMapper.toEntity(joinDTO));
        }catch (DuplicateUserException e){
            log.warn("Attempted to register duplicate user: {}", joinDTO.getPhone());
            throw e; // 글로벌 핸들러에서 처리
        }catch (Exception e) {
            log.error("Unexpected error during customer registration: {}", e.getMessage(), e);
            throw new RuntimeException("회원가입 처리 중 오류가 발생했습니다.", e);
        }
    }

    @Override
    @Transactional
    public void updateAdmin(Long adminId, AdminUpdateReqDTO request) {
        try {
            Admin admin = authRepository.findById(adminId)
                    .orElseThrow(() -> new IllegalArgumentException("관리자 정보를 찾을 수 없습니다."));

            String encodedPassword = request.getPassword() != null
                    ? bCryptPasswordEncoder.encode(request.getPassword())
                    : admin.getPassword(); // 비밀번호가 null인 경우 기존 비밀번호 유지

            admin.update(request, encodedPassword);
        } catch (Exception e) {
            log.error("Error updating admin information: {}", e.getMessage(), e);
            throw new RuntimeException("관리자 정보 수정 중 오류가 발생했습니다.", e);
        }
    }

    @Override
    @Transactional
    public void deleteAdmin(Long adminId) {
        try {
            Admin admin = authRepository.findById(adminId)
                    .orElseThrow(() -> new IllegalArgumentException("관리자 정보를 찾을 수 없습니다."));

            admin.delete();
        } catch (Exception e) {
            log.error("Error deleting admin: {}", e.getMessage(), e);
            throw new RuntimeException("관리자 삭제 중 오류가 발생했습니다.", e);
        }
    }
}
