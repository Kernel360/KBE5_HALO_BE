package com.kernel.common.customer.entity;

import com.kernel.common.customer.dto.request.CustomerInfoUpdateReqDTO;
import com.kernel.common.global.entity.BaseEntity;
import com.kernel.common.global.enums.Gender;
import com.kernel.common.global.enums.UserStatus;
import com.kernel.common.global.enums.UserType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "customer")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Customer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    // 핸드폰번호(계정ID)
    @Column(nullable = false)
    private String phone;

    // 이메일
    @Email
    private String email;

    // 비밀번호
    @Column(nullable = false)
    private String password;

    // 이름
    @Column(nullable = false)
    private String userName;

    // 생년월일
    @Column(nullable = false)
    private LocalDate birthDate;

    // 성별
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    // 도로명주소
    @Column(nullable = false)
    private String roadAddress;

    // 상세주소
    @Column(nullable = false)
    private String detailAddress;

    // 위도
    @Column(precision = 10, scale = 7, nullable = false)
    private BigDecimal latitude;

    // 경도
    @Column(precision = 10, scale = 7, nullable = false)
    private BigDecimal longitude;

    // 포인트
    @Column(nullable = false)
    private int point;

    // 유저상태
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private UserStatus status = UserStatus.ACTIVE;

    // 수요자 타입 조회
    public String getUserType() {
        return  "ROLE_"+ UserType.CUSTOMER;
    }

    // 회원 탈퇴
    public void delete(){
        this.status = UserStatus.DELETED;
    }

    // 탈퇴 여부 확인
    public void validateDelete(){
        if(this.status == UserStatus.DELETED)
            throw new IllegalStateException("이미 삭제된 사용자입니다.");
    }

    // 회원 정보 수정
    public void update(CustomerInfoUpdateReqDTO updateDTO){

        if(updateDTO.getEmail() != null && !updateDTO.getEmail().equals(this.email)){
            this.email = updateDTO.getEmail();
        }
        if(updateDTO.getRoadAddress() != null && !updateDTO.getRoadAddress().equals(this.roadAddress)){
            this.roadAddress = updateDTO.getRoadAddress();
        }
        if(updateDTO.getDetailAddress() != null && !updateDTO.getDetailAddress().equals(this.detailAddress)){
            this.detailAddress = updateDTO.getDetailAddress();
        }
        if(updateDTO.getLatitude()!= null && !updateDTO.getLatitude().equals(this.latitude)){
            this.latitude = updateDTO.getLatitude();
        }
        if(updateDTO.getLongitude() != null && !updateDTO.getLongitude().equals(this.longitude)){
            this.longitude = updateDTO.getLongitude();
        }
    }

    // 비밀번호 수정
    public void resetPassword(String newPassword){
        this.password = newPassword;
    }
}