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

    @Size(max = 50)
    private String email;

    @NotBlank
    @Size(max = 100)
    private String password;

    @Size(max = 100)
    private String userName;

    private LocalDateTime birthDate;

    @NotBlank
    @Size(max = 20)
    @Column(unique = true)
    private String phone;

    @Size(max = 10)
    private String zipcode;

    @Size(max = 200)
    private String roadAddress;

    @Size(max = 100)
    private String detailAddress;

    private Integer reservationCount;

    private Integer reviewCount;

    @Size(max = 50)
    private String bio;

    private Long profileImageId;

    private Status status;

    private boolean isDeleted;

    public void updateStatus(Status status) {
        this.status = status;
    }

    public void delete() {
        this.isDeleted = true;
    }
}
