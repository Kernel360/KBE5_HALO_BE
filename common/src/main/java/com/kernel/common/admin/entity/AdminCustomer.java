package com.kernel.common.admin.entity;

import com.kernel.common.global.enums.Gender;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "customer")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminCustomer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;
    private String email;
    private String password;
    private String userName;
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    private AccountStatus accountStatus;

    private String phone;
    private String zipcode;
    private String roadAddress;
    private String detailAddress;
    private Integer point;
}