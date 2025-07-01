package com.kernel.member.service;


import com.kernel.global.common.enums.ErrorCode;
import com.kernel.global.common.enums.UserRole;
import com.kernel.global.common.enums.UserStatus;
import com.kernel.global.common.exception.AuthException;
import com.kernel.global.domain.entity.User;
import com.kernel.member.domain.entity.Customer;
import com.kernel.member.domain.entity.CustomerStatistic;
import com.kernel.member.domain.entity.UserInfo;
import com.kernel.member.repository.CustomerRepository;
import com.kernel.member.repository.CustomerStatisticRepository;
import com.kernel.member.service.common.UserInfoService;
import com.kernel.member.service.common.UserService;
import com.kernel.member.service.common.info.UserDetailInfo;
import com.kernel.member.service.common.info.UserAccountInfo;
import com.kernel.member.service.request.CustomerSignupReqDTO;
import com.kernel.member.service.request.CustomerUpdateReqDTO;
import com.kernel.member.service.response.CustomerDetailRspDTO;
import com.kernel.member.service.common.info.CustomerDetailInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final UserService userService;
    private final UserInfoService userInfoService;
    private final CustomerRepository customerRepository;
    private final CustomerStatisticRepository statisticRepository;

    /**
     * 수요자 회원가입
     * @param signupReqDTO 회원가입 요청 DTO
     */
    @Override
    @Transactional
    public void signup(CustomerSignupReqDTO signupReqDTO) {

        // 1. phone 중복 검사
        userService.validateDuplicatePhone(signupReqDTO.getUserSignupReqDTO().getPhone());

        // 2. user 저장
        User savedUser = userService.createUser(signupReqDTO.getUserSignupReqDTO(), UserRole.CUSTOMER);

        // 3. userInfo 저장
        UserInfo savedUserInfo = userInfoService.createUserInfo(signupReqDTO.getUserInfoSignupReqDTO(), savedUser);

        // 4. Customer 저장
        customerRepository.save(Customer.builder()
                                .user(savedUser).build()
        );

        // 5. 수요자 통계 저장
        statisticRepository.save(CustomerStatistic.builder()
                                  .user(savedUser)
                                  .build());

    }

    /**
     * 수요자 회원정보 조회
     * @param userId 수요자ID
     * @return 수요자 정보를 담은 응답
     */
    @Override
    @Transactional(readOnly = true)
    public CustomerDetailRspDTO getCustomer(Long userId) {

        // 1. User 조회
        User foundUser = userService.getByUserIdAndStatus(userId, UserStatus.ACTIVE);

        // 2. Customer 조회
        Customer foundCustomer = customerRepository.findById(foundUser.getUserId())
                .orElseThrow(()-> new AuthException(ErrorCode.USER_NOT_FOUND));

        // 3. UserInfo 조회
        UserInfo foundUserInfo = userInfoService.getUserById(foundUser.getUserId());

        // Entity -> rspDTO 변환 후 return
        return  CustomerDetailRspDTO.fromInfos(
                    UserAccountInfo.fromEntity(foundUser),
                    UserDetailInfo.fromEntity(foundUserInfo),
                    CustomerDetailInfo.fromEntity(foundCustomer)
                );
    }

    /**
     * 수요자 회원 정보 수정
     * @param userId 수요자ID
     * @param updateReqDTO 회원정보수정요청 DTO
     * @return 수요자 정보를 담은 응답
     */
    @Override
    @Transactional
    public CustomerDetailRspDTO updateCustomer(Long userId, CustomerUpdateReqDTO updateReqDTO) {

        // User 조회
        User foundUser = userService.getByUserIdAndStatus(userId, UserStatus.ACTIVE);

        // Customer 조회
        Customer foundCustomer = customerRepository.findById(foundUser.getUserId())
                .orElseThrow(()-> new AuthException(ErrorCode.USER_NOT_FOUND));

        // UserInfo 조회
        UserInfo foundUserInfo = userInfoService.getUserById(foundUser.getUserId());

        // User 수정
        foundUser.updateEmail(updateReqDTO.getUserUpdateReqDTO().getEmail());

        // UserInfo 수정
        foundUserInfo.updateAddress(
                updateReqDTO.getUserInfoUpdateReqDTO().getRoadAddress(),
                updateReqDTO.getUserInfoUpdateReqDTO().getDetailAddress(),
                updateReqDTO.getUserInfoUpdateReqDTO().getLatitude(),
                updateReqDTO.getUserInfoUpdateReqDTO().getLongitude()
        );

        // DTO 변환 후 return
        return CustomerDetailRspDTO.fromInfos(
                UserAccountInfo.fromEntity(foundUser),
                UserDetailInfo.fromEntity(foundUserInfo),
                CustomerDetailInfo.fromEntity(foundCustomer)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Integer getCustomerPoints(Long userId) {

        // 1. User 조회
        User foundUser = userService.getByUserIdAndStatus(userId, UserStatus.ACTIVE);

        // 2. Customer 조회
        Customer foundCustomer = customerRepository.findById(foundUser.getUserId())
                .orElseThrow(()-> new AuthException(ErrorCode.USER_NOT_FOUND));

        return foundCustomer.getPoint();
    }

    @Override
    @Transactional
    public void payByPoint(Long userId, Integer amount) {
        // 1. User 조회
        User foundUser = userService.getByUserIdAndStatus(userId, UserStatus.ACTIVE);

        // 2. Customer 조회
        Customer foundCustomer = customerRepository.findById(foundUser.getUserId())
                .orElseThrow(()-> new AuthException(ErrorCode.USER_NOT_FOUND));

        foundCustomer.updatePoint(foundCustomer.getPoint() - amount);
    }

    @Override
    @Transactional
    public void chargePoint(Long userId, Integer amount) {
        // 1. User 조회
        User foundUser = userService.getByUserIdAndStatus(userId, UserStatus.ACTIVE);

        // 2. Customer 조회
        Customer foundCustomer = customerRepository.findById(foundUser.getUserId())
                .orElseThrow(()-> new AuthException(ErrorCode.USER_NOT_FOUND));

        if(foundCustomer.getPoint() + amount > 10000000)
            throw new IllegalArgumentException("최대 보유 가능 포인트는 1,000,000P 입니다.");

        foundCustomer.updatePoint(foundCustomer.getPoint() + amount);
    }
}
