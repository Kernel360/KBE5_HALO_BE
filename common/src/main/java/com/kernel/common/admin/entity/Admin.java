package com.kernel.common.admin.entity;

import com.kernel.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
public class Admin extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminId;

    @Column(unique = true, nullable = false)
    private String id;

    @Column(nullable = false, length = 100)
    private String password;

    public Admin(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public void updatePassword(String password) { this.password = password; }
}
