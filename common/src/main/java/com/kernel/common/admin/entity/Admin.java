package com.kernel.common.admin.entity;

import com.kernel.common.global.entity.BaseEntity;
import com.kernel.common.global.enums.UserStatus;
import com.kernel.common.global.enums.UserType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "admin")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SuperBuilder
// @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class) // application.yml에서 전역 설정했으므로 주석처리
public class Admin extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminId;

    @Column(unique = true, nullable = false)
    private String phone;

    @Column(nullable = false, length = 100)
    private String password;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private UserStatus status = UserStatus.ACTIVE;

    public void updatePassword(String password) { this.password = password; }
    public String getUserType() {
        return "ROLE_"+ UserType.ADMIN;
    }
}
