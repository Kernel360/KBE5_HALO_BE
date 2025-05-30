package com.kernel.app.service;

import com.kernel.app.dto.mapper.AdminAuthMapper;
import com.kernel.app.exception.custom.DuplicateUserException;
import com.kernel.app.repository.AdminAuthRepository;
import com.kernel.common.admin.dto.AdminSignupReqDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminAuthServiceImpl implements AdminAuthService {

    private final AdminAuthRepository repository;
    private final AdminAuthMapper mapper;

    @Override
    @Transactional
    public void join(AdminSignupReqDTO joinDTO) {

        try{
            String phone = joinDTO.getPhone();

            if(repository.existsByPhone(phone))
                throw new DuplicateUserException();

            repository.save(mapper.toEntity(joinDTO));
        }catch (DuplicateUserException e){
            log.warn("Attempted to register duplicate user: {}", joinDTO.getPhone());
            throw e; // 글로벌 핸들러에서 처리
        }catch (Exception e) {
            log.error("Unexpected error during customer registration: {}", e.getMessage(), e);
            throw new RuntimeException("회원가입 처리 중 오류가 발생했습니다.", e);
        }
    }
}
