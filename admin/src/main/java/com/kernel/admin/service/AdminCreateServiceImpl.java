package com.kernel.admin.service;

import com.kernel.admin.dto.mapper.AdminCreateMapper;
import com.kernel.admin.dto.request.AdminCreateRequestDTO;
import com.kernel.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminCreateServiceImpl implements AdminCreateService{

    private final AdminRepository adminRepository;
    private final AdminCreateMapper adminCreateMapper;

    @Transactional
    @Override
    public void createAdmin(AdminCreateRequestDTO request) {
        // TODO: 관리자 계정을 생성하는 로직을 작성, request의 id가 기존에 존재하는지 확인하고, 사용자 생성
        if(adminRepository.exists(request.getId())) {
            log.error("관리자 계정을 생성할 수 없습니다.: 관리자 ID가 이미 존재합니다.");
            throw new IllegalArgumentException("관리자 ID가 이미 존재합니다."); // 추후에 사용자 ID가 이미 있다는 사용자 정의 예외 클래스를 만들어서 사용하도록 변경할 것
        } else {
            Admin admin = adminCreateMapper.toCreateAdminEntity(request);
            adminRepository.save(admin);
        }
    }

    @Transactional
    @Override
    public void resetAdminPassword(String adminId) {
        // TODO: 관리자 비밀번호를 초기화하는 로직을 작성, adminId로 관리자 계정을 조회하고, 비밀번호를 초기화
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new IllegalArgumentException("관리자 계정을 찾을 수 없습니다."));

        admin.resetPassword();
        adminRepository.save(admin);    // Admin Entity에 비밀번호 초기화 메서드가 정의되어 있어야 함
    }
}
