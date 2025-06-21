package com.kernel.member.domain.entity;

import com.kernel.global.domain.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "customer")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Customer {

    @Id
    private Long userId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId  // PK이자 FK
    @JoinColumn(name = "user_id")
    private User user;

    // 포인트
    @Column(nullable = false)
    @Builder.Default
    private Integer point = 0;
}
