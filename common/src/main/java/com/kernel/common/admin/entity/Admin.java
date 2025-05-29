package com.kernel.common.admin.entity;
import com.kernel.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import static com.kernel.common.admin.util.RandomPasswordUtil.generatePassword;

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
    private String id;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, columnDefinition = "Boolean DEFAULT false")
    private Boolean isDeleted;

    public String resetPassword() {
        this.password = generatePassword(8);

        return this.password;
    }
}
