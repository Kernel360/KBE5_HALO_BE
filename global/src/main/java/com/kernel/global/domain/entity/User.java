package com.kernel.global.domain.entity;

import com.kernel.global.common.enums.UserRole;
import com.kernel.global.common.enums.UserStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "user")
@Getter
@SuperBuilder
public class User extends BaseEntity {

    // 사용자 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
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
    private UserStatus status;
}
