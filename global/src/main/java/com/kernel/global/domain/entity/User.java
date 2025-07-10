package com.kernel.global.domain.entity;

import com.kernel.global.common.enums.UserRole;
import com.kernel.global.common.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "user")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User extends BaseEntity {

    // 사용자 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    // 사용자 연락처
    @Column(nullable = false, unique = true)
    private String phone;

    // 사용자 이름
    @Column(nullable = false)
    private String userName;

    // 사용자 이메일
    @Column
    private String email;

    // 사용자 비밀번호
    @Column(nullable = false)
    private String password;

    // 사용자 권한
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    // 사용자 상태값
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private UserStatus status = UserStatus.ACTIVE;

    // 사용자 email 수정
    public void updateEmail(String email) {
        if(!email.isBlank() && !this.email.equals(email)) {
            this.email = email;
        }
    }

    // 관리자 계정 업데이트
    public void updateAdmin(String phone, String userName, String email) {
        // 전화번호는 고유키이므로 서비스 단에서 중복 검사 필수
        if(!phone.isBlank() && !this.phone.equals(phone)) {
            this.phone = phone;
        }

        if(!userName.isBlank() && !this.userName.equals(userName)) {
            this.userName = userName;
        }
        if(!email.isBlank() && !this.email.equals(email)) {
            this.email = email;
        }
    }

    // 회원 탈퇴
    public void delete(){
        this.status = UserStatus.DELETED;
    }

    // 비밀번호 변경
    public void resetPassword(String password) {
        this.password = password;
    }

}
