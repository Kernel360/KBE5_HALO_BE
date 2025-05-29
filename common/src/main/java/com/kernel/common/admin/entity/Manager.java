package com.kernel.common.admin.entity;

import com.kernel.common.global.entity.BaseEntity;
import com.kernel.common.global.enums.Gender;
import com.kernel.common.global.enums.UserStatus;
import com.kernel.common.global.enums.UserType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Table(name = "manager")
@Getter
@NoArgsConstructor
@SuperBuilder
@ToString
// @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class) // application.yml에서 전역 설정했으므로 주석처리
public class Manager extends BaseEntity {
    // TODO: Manager Entity로 매핑할 필드 정의

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long managerId;

    @Column(unique = true, nullable = false, length = 20)
    private String phone;

    @Column(unique = true, length = 50)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(length = 100)
    private String userName;

    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(length = 10)
    private String zipcode;

    @Column(length = 200)
    private String roadAddress;

    @Column(length = 100)
    private String detailAddress;

    @Column(columnDefinition = "Integer DEFAULT 0")
    private Integer reservationCount;

    @Column(columnDefinition = "Integer DEFAULT 0")
    private Integer reviewCount;

    @Column(length = 50)
    private String bio;

    private Long profileImageId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private UserStatus status = UserStatus.PENDING;

    @Column(columnDefinition = "Boolean DEFAULT false")
    private boolean isDeleted;

    public void updateStatus(UserStatus status) {
        this.status = status;
    }

    public void delete() {
        this.isDeleted = true;
    }

    public String getUserType() {
        return "ROLE_"+ UserType.MANAGER;
    }

}
