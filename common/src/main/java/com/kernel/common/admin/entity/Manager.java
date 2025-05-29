package com.kernel.common.admin.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.kernel.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

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

    @Column(unique = true, length = 50)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(length = 100)
    private String userName;

    private LocalDateTime birthDate;

    @Column(unique = true, nullable = false, length = 20)
    private String phone;

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
    @Column(columnDefinition = "Status DEFAULT 'INACTIVE'")
    private Status status;

    @Column(columnDefinition = "Boolean DEFAULT false")
    private boolean isDeleted;

    public void updateStatus(Status status) {
        this.status = status;
    }

    public void delete() {
        this.isDeleted = true;
    }
}
