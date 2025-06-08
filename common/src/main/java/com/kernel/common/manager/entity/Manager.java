package com.kernel.common.manager.entity;

import com.kernel.common.global.entity.BaseEntity;
import com.kernel.common.global.enums.Gender;
import com.kernel.common.global.enums.UserStatus;
import com.kernel.common.global.enums.UserType;
import com.kernel.common.manager.dto.request.ManagerTerminationReqDTO;
import com.kernel.common.manager.dto.request.ManagerUpdateReqDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "manager")
@Getter
@SuperBuilder
@NoArgsConstructor
public class Manager extends BaseEntity {

    // 매니저ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long managerId;

    // 연락처(=계정ID)
    @Column(length = 20, nullable = false, unique = true)
    private String phone;

    // 이메일
    @Column(length = 50, nullable = false)
    private String email;

    // 비밀번호
    @Column(length = 100, nullable = false)
    private String password;

    // 이름
    @Column(length = 100, nullable = false)
    private String userName;

    // 생년월일
    @Column(nullable = false)
    private LocalDate birthDate;

    // 성별
    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private Gender gender;

    // 위도
    @Column(precision = 10, scale = 7, nullable = false)
    private BigDecimal latitude;

    // 경도
    @Column(precision = 10, scale = 7, nullable = false)
    private BigDecimal longitude;

    // 도로명 주소
    @Column(length = 200, nullable = false)
    private String roadAddress;

    // 상세 주소
    @Column(length = 100, nullable = false)
    private String detailAddress;

    // 한줄소개
    @Column(length = 50, nullable = false)
    private String bio;

    // 프로필사진ID
    // TODO: 첨부파일 완료되면 nullable
//    @Column(nullable = false)
    @Column
    private Long profileImageId;

    // 첨부파일ID
    // TODO: 첨부파일 완료되면 nullable
//    @Column(nullable = false)
    @Column
    private Long fileId;

    // 계정 상태
    // 매니저는 회원가입 시 바로 활성화되지 않고, 지원서 제출 상태이므로 초기값은 Status.PENDING(승인대기)
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private UserStatus status = UserStatus.PENDING;

    // 매니저 업무 가능 시간
    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<AvailableTime> availableTimes = new ArrayList<>();

    // 삭제 여부
    @Column(nullable = false)
    @Builder.Default
    private Boolean isDeleted = false;

    // 계약일시
    @Column
    private LocalDateTime contractAt;

    // 계약해지사유
    @Column(length = 50)
    private String terminationReason;

    // 계약해지일시
    @Column
    private LocalDateTime terminatedAt;

    // TODO: 컬럼으로 관리할 것인지, 저장하여 관리할 것인지 결정 필요
    // 예약건수
    // 예약이 완료되면 UPDATE
    @Column
    @Builder.Default
    private Integer reservationCount = 0;

    // TODO: 컬럼으로 관리할 것인지, 저장하여 관리할 것인지 결정 필요
    // 리뷰건수
    // 리뷰가 등록되면 계산하여 UPDATE
    @Column
    @Builder.Default
    private Integer reviewCount = 0;

    // TODO: 컬럼으로 관리할 것인지, 저장하여 관리할 것인지 결정 필요
    // 리뷰평균평점
    // 리뷰가 등록되면 계산하여 UPDATE
    @Column(precision = 2, scale = 1)
    @Builder.Default
    private BigDecimal averageRating = BigDecimal.ZERO;


    @PrePersist
    public void prePersist() {
        // 계정 상태(대기중)
        if (status == null) status = UserStatus.PENDING;
        
        // 삭제 여부
        if (isDeleted == null) isDeleted = false;
        
        // 예약 건수
        if (reservationCount == null) reservationCount = 0;
        
        // 리뷰 건수
        if (reviewCount == null) reviewCount = 0;
        
        // 리뷰평균평점
        if (averageRating == null) averageRating = BigDecimal.ZERO;
    }

    // 매니저 업무 가능 시간 추가
    public void addAvailableTime(AvailableTime time) {
        availableTimes.add(time);
    }

    // 매니저 정보 수정
    public void updateManager(ManagerUpdateReqDTO updateReqDTO, String encodedPassword ) {
        this.email = updateReqDTO.getEmail();                   // 이메일
        this.password = encodedPassword;                        // 비밀번호
        this.latitude = updateReqDTO.getLatitude();             // 위도
        this.longitude = updateReqDTO.getLongitude();           // 경도
        this.roadAddress = updateReqDTO.getRoadAddress();       // 도로명주소
        this.detailAddress = updateReqDTO.getDetailAddress();   // 상세주소
        this.bio = updateReqDTO.getBio();                       // 한줄소개
        this.profileImageId = updateReqDTO.getProfileImageId(); // 프로필이미지ID
        this.fileId = updateReqDTO.getFileId();                 // 첨부파일ID

        // 업무 가능 시간 수정
        if (updateReqDTO.getAvailableTimes() != null) {
            this.availableTimes.clear(); // 기존 전체 삭제
            updateReqDTO.getAvailableTimes().forEach(timeDTO -> this.addAvailableTime(AvailableTime.builder()
                .manager(this)
                .dayOfWeek(timeDTO.getDayOfWeek())  // 가능 요일
                .time(timeDTO.getTime())            // 가능 시간
                .build()));
        }
    }
    
    // 매니저 계정 상태 변경
    public void updateStatus(UserStatus status) {
        this.status = status;
    }

    // 매니저 계약 해지 요청
    public void requestTermination(ManagerTerminationReqDTO terminationReqDTO) {
        this.status = UserStatus.TERMINATION_PENDING;                      // 계약해지대기
        this.terminationReason = terminationReqDTO.getTerminationReason(); // 계약해지사유
    }

    // 매니저 계정 삭제
    public void delete() {
        this.isDeleted = true;
        this.status = UserStatus.DELETED; // TODO: 종은님 확인 필요, 삭제 시 status 추가
    }


    // 매니저의 권한 타입 반환(ROLE_MANAGER)
    public String getUserType() {
        return "ROLE_"+ UserType.MANAGER;
    }
}
