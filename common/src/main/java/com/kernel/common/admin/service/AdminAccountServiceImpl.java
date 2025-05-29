package com.kernel.common.admin.service;
import com.kernel.common.admin.dto.mapper.AdminAccountMapper;
import com.kernel.common.admin.dto.request.AdminAccountReqDTO;
import com.kernel.common.admin.dto.response.AdminResDTO;
import com.kernel.common.admin.entity.Admin;
import com.kernel.common.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminAccountServiceImpl implements AdminAccountService {

    private final AdminRepository adminRepository;
    private final AdminAccountMapper adminAccountMapper;

    /**
     * 관리자 계정 목록을 조회하는 메서드
     * @return 관리자 계정 목록
     */
    @Transactional(readOnly = true)
    @Override
    public Page<AdminResDTO> getAdminList(Pageable pageble) {
        // TODO: 관리자 계정 목록을 조회하는 로직을 작성

        Page<Admin> admins = adminRepository.findAll(pageble);
        return adminAccountMapper.toAdminResponseDTOList(admins);
    }


    /**
     * 관리자 계정 목록을 조회하는 메서드
     * @return 관리자 계정 목록
     */
    @Transactional
    @Override
    public void createAdmin(AdminAccountReqDTO request) {
        // TODO: 관리자 계정을 생성하는 로직을 작성, request의 id가 기존에 존재하는지 확인하고, 사용자 생성
        if(adminRepository.existsById(request.getId())) {
            log.error("관리자 계정을 생성할 수 없습니다.: 관리자 ID가 이미 존재합니다.");
            throw new IllegalArgumentException("관리자 ID가 이미 존재합니다."); // 추후에 사용자 ID가 이미 있다는 사용자 정의 예외 클래스를 만들어서 사용하도록 변경할 것
        } else {
            Admin admin = adminAccountMapper.toCreateAdminEntity(request);
            adminRepository.save(admin);
        }
    }

    /**
     * 관리자 비밀번호를 초기화하는 메서드
     * @param id 관리자 ID
     * @return 새로 설정된 비밀번호
     */
    @Transactional
    @Override
    public String resetAdminPassword(String id) {
        // TODO: 관리자 비밀번호를 초기화하는 로직을 작성, id로 관리자 계정을 조회하고, 비밀번호를 초기화
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("관리자 계정을 찾을 수 없습니다."));

        String newPassword = admin.resetPassword();
        adminRepository.save(admin);    // Admin Entity에 비밀번호 초기화 메서드가 정의되어 있어야 함

        return newPassword; // 비밀번호 초기화 후 새 비밀번호를 반환
    }
}