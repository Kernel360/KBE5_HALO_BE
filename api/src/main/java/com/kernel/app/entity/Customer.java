package com.kernel.app.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.kernel.app.dto.UserInfo;
import com.kernel.app.enums.Gender;
import com.kernel.app.enums.UserStatus;
import com.kernel.app.enums.UserType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Table(name = "customer")
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Customer extends BaseEntity implements UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    @NotBlank
    private String phone;

    @Email
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String userName;

    @NotNull
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private Gender gender;


    @NotBlank
    private String zipcode;

    @NotBlank
    private String roadAddress;

    @NotBlank
    private String detailAddress;

    @Column(nullable = false)
    @Builder.Default
    private int point = 0;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Override
    public String getUserType() {
        return  "ROLE_"+ UserType.CUSTOMER;
    }

}

