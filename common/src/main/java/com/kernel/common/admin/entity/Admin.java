package com.kernel.common.admin.entity;

import com.kernel.common.admin.dto.request.AdminUpdateReqDTO;
import com.kernel.common.global.entity.BaseEntity;
import com.kernel.common.global.enums.UserStatus;
import com.kernel.common.global.enums.UserType;

import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.persistence.*;

@Entity
@Table(name = "admin")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@SuperBuilder
// @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class) // application.yml에서 전역 설정했으므로 주석처리
public class Admin extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminId;

    @Column(unique = true, nullable = false, length = 15)
    private String phone;

    @Column(nullable = false, length = 10)
    private String userName;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private UserStatus status = UserStatus.ACTIVE;


    /**
     * 관리자 정보 수정 메서드
     * @param request 수정 요청 DTO
     * @param password 새 비밀번호 (null이면 비밀번호는 변경하지 않음)
     */
    public void update(AdminUpdateReqDTO request, String password) {
        if (request.getUserName() != null) {
            this.userName = request.getUserName();
        }
        if (request.getPhone() != null) {
            this.phone = request.getPhone();
        }
        if (request.getEmail() != null) {
            this.email = request.getEmail();
        }
        if (request.getStatus() != null) {
            this.status = UserStatus.valueOf(request.getStatus());
        }
        if (password != null && !password.isEmpty()) {
            this.password = password;
        }
    }

    /**
     * 관리자 상태를 DELETED로 변경하는 메서드
     * 이 메서드는 관리자 계정을 삭제하는 용도로 사용됩니다.
     */
    public void delete() {
        this.status = UserStatus.DELETED;
    }

    public String getUserType() {
        return "ROLE_"+ UserType.ADMIN;
    }
}
