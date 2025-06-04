package com.kernel.app.service;

import com.kernel.app.dto.mapper.CustomerAuthMapper;
import com.kernel.app.exception.custom.DuplicateUserException;
import com.kernel.app.repository.CustomerAuthRepository;
import com.kernel.common.customer.dto.request.CustomerFindAccountReqDTO;
import com.kernel.common.customer.dto.request.CustomerInfoUpdateReqDTO;
import com.kernel.common.customer.dto.request.CustomerPasswordResetReqDTO;
import com.kernel.common.customer.dto.request.CustomerSignupReqDTO;
import com.kernel.common.customer.dto.response.CustomerInfoDetailRspDTO;
import com.kernel.common.customer.entity.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerAuthServiceImpl implements CustomerAuthService {

    private final CustomerAuthRepository authRepository;
    private final CustomerAuthMapper authMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 수요자 회원가입
     * @param signupReqDTO 회원가입 요청 DTO
     */
    @Override
    @Transactional
    public void signup(CustomerSignupReqDTO signupReqDTO) {
        try{
            String phone = signupReqDTO.getPhone();

            // 핸드폰 번호 중복 검사
            if(authRepository.existsByPhone(phone))
                throw new DuplicateUserException();

            // 저장
            authRepository.save(authMapper.toEntity(signupReqDTO));

        }catch (DuplicateUserException e){
            log.warn("Attempted to register duplicate user: {}", signupReqDTO.getPhone());
            throw e; // 글로벌 핸들러에서 처리

        }catch (Exception e) {
            log.error("Unexpected error during customer registration: {}", e.getMessage(), e);
            throw new RuntimeException("회원가입 처리 중 오류가 발생했습니다.", e);
        }
    }

    /**
     * 수요자 회원정보 조회
     * @param customerId 수요자ID
     * @return 수요자 정보를 담은 응답
     */
    @Override
    @Transactional(readOnly = true)
    public CustomerInfoDetailRspDTO getCustomer(Long customerId) {

        // 조회
        Customer foundCustomer = findCustomer(customerId);

        // 탈퇴 여부 확인
        foundCustomer.validateDelete();

        return authMapper.toInfoDto(foundCustomer);
    }

    /**
     * 수요자 회원 정보 수정
     * @param customerId 수요자ID
     * @return 수요자 정보를 담은 응답
     */
    @Override
    @Transactional
    public CustomerInfoDetailRspDTO updateCustomer(Long customerId, CustomerInfoUpdateReqDTO updateReqDTO) {

        // 수요자 조회
        Customer foundCustomer = findCustomer(customerId);

        // 탈퇴 여부 확인
        foundCustomer.validateDelete();

        // 비밀번호 일치 여부 확인
        validatePassword(updateReqDTO.getPassword(), foundCustomer.getPassword());

        // 수정
        foundCustomer.update(updateReqDTO);

        // 재조회
        Customer updatedCustomer = findCustomer(customerId);

        return authMapper.toInfoDto(updatedCustomer);
    }

    /**
     * 수요자 비밀번호 변경
     * @param customerId 수요자ID
     * @param resetReqDTO 새로운 비밀번호 요청 DTO
     */
    @Override
    @Transactional
    public void resetPassword(Long customerId, CustomerPasswordResetReqDTO resetReqDTO) {

        // 수요자 조회
        Customer foundCustomer = findCustomer(customerId);

        // 탈퇴 여부 확인
        foundCustomer.validateDelete();

        // 비밀번호 일치 여부 확인
        validatePassword(resetReqDTO.getCurrentPassword(), foundCustomer.getPassword());

        // 비밀번호 암호화 후 수정
        foundCustomer.resetPassword(bCryptPasswordEncoder.encode(resetReqDTO.getNewPassword()));

    }

    /**
     * 수요자 회원 탈퇴
     * @param customerId 수요자ID
     */
    @Override
    @Transactional
    public void deleteCustomer(Long customerId, String password) {

        // 수요자 조회
        Customer foundCustomer = findCustomer(customerId);

        // 탈퇴 여부 확인
        foundCustomer.validateDelete();

        // 비밀번호 확인
        validatePassword(password, foundCustomer.getPassword());

        // 예약 확인
        //TODO 예약 내역 조회 개발 시 추가

        // 삭제
        foundCustomer.delete();
    }

    /**
     * 수요자 아이디 찾기
     * @param findAccountReqDTO 아이디 찾기 요청 DTO
     * @return 수요자 계정 존재 여부
     */
    @Override
    @Transactional(readOnly = true)
    public Boolean findCustomerId(CustomerFindAccountReqDTO findAccountReqDTO) {

        // 수요자 존재 여부 확인
        return authRepository.existsByPhoneAndUserNameAndBirthDate(
                                findAccountReqDTO.getPhone(),         // 핸드폰번호
                                findAccountReqDTO.getUserName(),      // 사용자 이름
                                findAccountReqDTO.getBirthDate()      // 생년월일
                              );
    }

    /**
     * 수요자 비밀번호 찾기
     * @param findAccountReqDTO 비밀번호 찾기 요청 DTO
     * @return 랜덤 비밀번호
     */
    @Override
    @Transactional
    public String findCustomerPassword(CustomerFindAccountReqDTO findAccountReqDTO) {

        // 수요자 조회
        Customer findCustomer = authRepository.findByPhoneAndUserNameAndBirthDate(
                                                findAccountReqDTO.getPhone(),         // 핸드폰번호
                                                findAccountReqDTO.getUserName(),      // 사용자 이름
                                                findAccountReqDTO.getBirthDate()      // 생년월일
                                ).orElseThrow(()-> new NoSuchElementException("존재하지 않는 사용자입니다."));

        // 랜덤 비밀번호 생성
        // TODO 종은님이 작성하신 랜덤비밀번호 사용할 예정
        String randomPassword = "test";

        // 비밀번호 암호화
        String afterRandomPassword = bCryptPasswordEncoder.encode(randomPassword);

        // 계정 비밀번호 재설정
        findCustomer.resetPassword(afterRandomPassword);

        return randomPassword;
    }

    /**
     * 수요자 회원 정보 조회
     * @param customerId 수요자ID
     * @return 수요자 정보를 담은 응답
     */
    private Customer findCustomer(Long customerId){
        return authRepository.findById(customerId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 사용자입니다."));
    }

    /**
     * 수요자 비밀번호 일치 여부 확인
     * @param reqPassword 조회한 수요자 비밀번호
     * @param entityPassword 요청한 수요자 비밀번호
     */
    private void validatePassword(String reqPassword, String entityPassword) {
        if(!bCryptPasswordEncoder.matches(reqPassword, entityPassword))
            throw new IllegalArgumentException("비밀번호를 확인해주세요.");
    }
}
