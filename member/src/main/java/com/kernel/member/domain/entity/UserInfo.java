package com.kernel.member.domain.entity;

import com.kernel.global.domain.entity.BaseEntity;
import com.kernel.global.domain.entity.User;
import com.kernel.member.common.enums.Gender;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "user_info")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserInfo extends BaseEntity {

    @Id
    private Long userId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId  // PK이자 FK
    @JoinColumn(name = "user_id")
    private User user;

    // 생년월일
    @Column(nullable = false)
    private LocalDate birthDate;

    // 성별
    @Column(nullable = false)
    private Gender gender;

    // 위도
    @Column(nullable = false)
    private BigDecimal latitude;

    // 경도
    @Column(nullable = false)
    private BigDecimal longitude;

    // 도로명 주소
    @Column(nullable = false)
    private String roadAddress;

    // 상세 주소
    @Column(nullable = false)
    private String detailAddress;


    // 사용자 주소 수정
    public void updateAddress(
            String roadAddress, String detailAddress,
            BigDecimal latitude, BigDecimal longitude
    ) {
        // 도로명 주소
        if (!roadAddress.isBlank() && !this.roadAddress.equals(roadAddress))
            this.roadAddress = roadAddress;

        // 상세 주소
        if(!detailAddress.isBlank() && !this.detailAddress.equals(detailAddress))
            this.detailAddress = detailAddress;

        // 위도
        if(latitude != null && !this.latitude.equals(latitude))
            this.latitude = latitude;

        // 경도
        if(longitude != null && !this.longitude.equals(longitude))
            this.longitude = longitude;

    }
}
