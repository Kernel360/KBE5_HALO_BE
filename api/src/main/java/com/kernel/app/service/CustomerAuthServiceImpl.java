package com.kernel.app.service;

import com.kernel.app.dto.mapper.CustomerAuthMapper;
import com.kernel.app.dto.request.UserLoginReqDTO;
import com.kernel.app.repository.CustomerAuthRepository;
import com.kernel.app.dto.request.CustomerSignupReqDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerAuthServiceImpl implements CustomerAuthService {

    private final CustomerAuthRepository repository;
    private final CustomerAuthMapper mapper;

    @Override
    public void join(CustomerSignupReqDTO joinDTO) {

        String email = joinDTO.getEmail();

        Boolean isExists = repository.existsByEmail(email);

        if (isExists) throw new NoSuchElementException("이미 존재하는 id 입니다.");

        repository.save(mapper.toEntity(joinDTO));

    }

    @Override
    public void login(UserLoginReqDTO loginDTO) {

    }
}
