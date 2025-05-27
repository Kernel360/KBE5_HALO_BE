package com.kernel.common.admin.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.kernel.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "admin")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SuperBuilder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Admin extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminId;

    @NotBlank
    @Size(max=50)
    @Column(unique = true)
    private String id;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false)
    private String password;

    public Admin(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public void updatePassword(String password) { this.password = password; }
}
