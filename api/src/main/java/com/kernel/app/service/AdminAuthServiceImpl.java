package com.kernel.app.service;

import com.kernel.app.dto.mapper.AdminAuthMapper;
import com.kernel.app.repository.AdminAuthRepository;
import com.kernel.common.admin.dto.AdminSignupReqDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminAuthServiceImpl implements AdminAuthService {

    private final AdminAuthRepository repository;
    private final AdminAuthMapper mapper;

    @Override
    public void join(AdminSignupReqDTO joinDTO) {

        String phone = joinDTO.getPhone();

        Boolean isExists = repository.existsByPhone(phone);

        if (isExists) throw new NoSuchElementException("이미 존재하는 사용자입니다.");

        repository.save(mapper.toEntity(joinDTO));

    }
}
