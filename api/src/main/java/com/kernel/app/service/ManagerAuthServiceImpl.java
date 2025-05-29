package com.kernel.app.service;

import com.kernel.app.dto.mapper.ManagerAuthMapper;
import com.kernel.app.repository.ManagerAuthRepository;
import com.kernel.common.manager.dto.ManagerSignupReqDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManagerAuthServiceImpl implements ManagerAuthService {

    private final ManagerAuthRepository repository;
    private final ManagerAuthMapper mapper;

    @Override
    public void join(ManagerSignupReqDTO joinDTO) {

        String phone = joinDTO.getPhone();

        Boolean isExists = repository.existsByPhone(phone);

        if (isExists) throw new NoSuchElementException("이미 존재하는 사용자입니다.");

        repository.save(mapper.toEntity(joinDTO));

    }
}
