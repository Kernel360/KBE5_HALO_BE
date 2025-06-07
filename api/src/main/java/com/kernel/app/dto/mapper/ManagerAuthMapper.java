package com.kernel.app.dto.mapper;

import com.kernel.common.global.enums.DayOfWeek;
import com.kernel.common.global.enums.UserStatus;
import com.kernel.common.manager.dto.request.ManagerSignupReqDTO;
import com.kernel.common.manager.dto.response.AvailableTimeRspDTO;
import com.kernel.common.manager.dto.response.ManagerInfoRspDTO;
import com.kernel.common.manager.entity.AvailableTime;
import com.kernel.common.manager.entity.Manager;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ManagerAuthMapper {

    // 암호화
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // AvailableTime 엔티티 생성 메서드
    private AvailableTime toAvailableTime(Manager manager, DayOfWeek dayOfWeek, LocalTime time) {
        return AvailableTime.builder()
            .manager(manager)       // 매니저
            .dayOfWeek(dayOfWeek)   // 가능 요일
            .time(time)             // 가능 시간
            .build();
    }

    // AvailableTime → AvailableTimeRspDTO
    private AvailableTimeRspDTO toAvailableTimeRspDTO(AvailableTime time) {
        return AvailableTimeRspDTO.builder()
            .dayOfWeek(time.getDayOfWeek()) // 가능 요일
            .time(time.getTime())           // 가능 시간
            .build();
    }

    // RequestDTO -> Entity
    public Manager toEntity(ManagerSignupReqDTO requestDTO){

        // Manager 객체 생성
        Manager manager = Manager.builder()
            .phone(requestDTO.getPhone())                   // 연락처
            .email(requestDTO.getEmail())                   // 이메일
            .password(bCryptPasswordEncoder.encode(requestDTO.getPassword()))   // 비밀번호(암호화)
            .userName(requestDTO.getUserName())             // 이름
            .birthDate(requestDTO.getBirthDate())           // 생년월일
            .gender(requestDTO.getGender())                 // 성별
            .latitude(requestDTO.getLatitude())             // 위도
            .longitude(requestDTO.getLongitude())           // 경도
            .roadAddress(requestDTO.getRoadAddress())       // 도로명주소
            .detailAddress(requestDTO.getDetailAddress())   // 상세주소
            .bio(requestDTO.getBio())                       // 한줄소개
            .profileImageId(requestDTO.getProfileImageId()) // 프로필이미지ID
            .fileId(requestDTO.getFileId())                 // 첨부파일ID
            .status(UserStatus.PENDING)                     // 계정 상태(승인대기)
            .build();

        // AvailableTime 리스트 추가
        if (requestDTO.getAvailableTimes() != null) {
            requestDTO.getAvailableTimes().forEach(timeDTO -> manager.addAvailableTime(
                toAvailableTime(
                    manager,
                    timeDTO.getDayOfWeek(), // 가능 요일
                    timeDTO.getTime()       // 가능 시간
                )
            ));
        }

        return manager;
    }

    // Entity -> ResponseDTO
    public ManagerInfoRspDTO toMangerInfoRspDTO(Manager manager) {
        return  ManagerInfoRspDTO.builder()
            .managerId(manager.getManagerId())              // 매니저ID
            .phone(manager.getPhone())                      // 연락처
            .email(manager.getEmail())                      // 이메일
            .userName(manager.getUserName())                // 이름
            .birthDate(manager.getBirthDate())              // 생년월일
            .gender(manager.getGender())                    // 성별
            .genderName(manager.getGender().getLabel())     // 성별 라벨
            .latitude(manager.getLatitude())                // 위도
            .longitude(manager.getLongitude())              // 경도
            .roadAddress(manager.getRoadAddress())          // 도로명주소
            .detailAddress(manager.getDetailAddress())      // 상세주소
            .bio(manager.getBio())                          // 한줄소개
            .profileImageId(manager.getProfileImageId())    // 프로필이미지ID
            .fileId(manager.getFileId())                    // 첨부파일ID
            .status(manager.getStatus())                    // 계정 상태
            .statusName(manager.getStatus().getLabel())     // 계정 상태 라벨
            .availableTimes(                                // 매니저 가능 시간
                manager.getAvailableTimes().stream()
                    .map(this::toAvailableTimeRspDTO)
                    .toList()
            )
            .contractAt(manager.getContractAt())                // 계약일시
            .terminationReason(manager.getTerminationReason())  // 계약해지사유
            .terminatedAt(manager.getTerminatedAt())            // 계약해지일시
            .build();
    }
}
