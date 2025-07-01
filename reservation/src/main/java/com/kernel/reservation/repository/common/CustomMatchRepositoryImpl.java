package com.kernel.reservation.repository.common;

import com.kernel.global.common.enums.UserStatus;
import com.kernel.global.domain.entity.QFile;
import com.kernel.global.domain.entity.QUser;
import com.kernel.member.common.enums.ContractStatus;
import com.kernel.member.common.enums.DayOfWeek;
import com.kernel.member.domain.entity.QAvailableTime;
import com.kernel.member.domain.entity.QManager;
import com.kernel.member.domain.entity.QManagerStatistic;
import com.kernel.member.domain.entity.QUserInfo;
import com.kernel.reservation.domain.entity.QReservationMatch;
import com.kernel.reservation.domain.entity.QReservationSchedule;
import com.kernel.reservation.service.info.MatchedManagersInfo;
import com.kernel.reservation.service.request.ReservationReqDTO;
import com.kernel.sharedDomain.common.enums.ReservationStatus;
import com.kernel.sharedDomain.domain.entity.QReservation;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class CustomMatchRepositoryImpl implements CustomMatchRepository {

    private final JPAQueryFactory queryFactory;
    private final QUser user = QUser.user;
    private final QManager manager = QManager.manager;
    private final QManagerStatistic managerStatistic = QManagerStatistic.managerStatistic;
    private final QUserInfo userInfo = QUserInfo.userInfo;
    private final QReservation reservation = QReservation.reservation;
    private final QAvailableTime availableTime = QAvailableTime.availableTime;
    private final QReservationMatch match = QReservationMatch.reservationMatch;
    private final QReservationSchedule schedule = QReservationSchedule.reservationSchedule;
    private final QFile file = QFile.file;



    /**
     * 매니저 매칭 리스트 조회
     * @param reservationReqDTO 예약 요청
     * @return 매니저 매칭 리스트
     */
    public List<Long> getMatchedManagers(ReservationReqDTO reservationReqDTO) {

        // 1. 예약 요일 Enum으로 변환
        DayOfWeek requestDay = DayOfWeek.fromJavaDayOfWeek(reservationReqDTO.getRequestDate().getDayOfWeek());

        // 2. 시작 시간, 소요시간
        LocalTime startTime = reservationReqDTO.getStartTime();
        int turnaround = reservationReqDTO.getTurnaround();

        List<LocalTime> requiredTimes = IntStream.range(0, turnaround+1)
                .mapToObj(i -> startTime.plusHours(i))
                .collect(Collectors.toList());

        // 3. 연속 가능한 시간대 조건에 맞는 매니저 ID 조회
        List<Long> availableManagerIds = queryFactory
                .select(availableTime.manager.userId)
                .from(availableTime)
                .join(availableTime.manager, manager)
                .where(
                        manager.contractStatus.eq(ContractStatus.APPROVED),
                        availableTime.dayOfWeek.eq(requestDay),
                        availableTime.time.in(requiredTimes)
                )
                .groupBy(availableTime.manager.userId)
                .having(availableTime.manager.userId.count().goe((long) turnaround))
                .fetch();

        // 거리 허용 범위 (약 5km에 해당하는 위도/경도 범위)
        BigDecimal latDelta = new BigDecimal("0.045");
        BigDecimal lngDelta = new BigDecimal("0.055");

        // 기준 좌표
        BigDecimal targetLat = reservationReqDTO.getLatitude();
        BigDecimal targetLng = reservationReqDTO.getLongitude();

        // 4. 최종 매칭 매니저 조회
        List<Long> matchedManagers = queryFactory
                .select(
                        manager.userId
                )
                .from(manager)
                .leftJoin(manager.user, user)
                .leftJoin(userInfo).on(userInfo.userId.eq(manager.userId))
                .where(
                        manager.contractStatus.eq(ContractStatus.APPROVED),     // 매니저 계약 상태
                        user.status.eq(UserStatus.ACTIVE),                      // 계정 상태
                        manager.userId.in(availableManagerIds),                 // 업무 가능 매니저

                        // [1단계 필터] 대략적인 사각 범위 필터링
                        userInfo.latitude.between(targetLat.subtract(latDelta), targetLat.add(latDelta)),
                        userInfo.longitude.between(targetLng.subtract(lngDelta), targetLng.add(lngDelta)),

                        // [2단계 필터] 정확한 거리 계산 (Haversine)
                        Expressions.numberTemplate(Double.class,
                                "6371 * acos(cos(radians({0})) * cos(radians({1}.latitude)) * cos(radians({1}.longitude) - radians({2})) + sin(radians({0})) * sin(radians({1}.latitude)))",
                                targetLat, userInfo, targetLng
                        ).loe(5),

                        // 중복 예약 확인
                        JPAExpressions
                                .selectOne()
                                .from(reservation)
                                .leftJoin(schedule).on(schedule.reservation.eq(reservation))
                                .leftJoin(match).on(match.reservation.eq(reservation))
                                .where(
                                        match.manager.userId.eq(manager.userId),
                                        schedule.requestDate.eq(reservationReqDTO.getRequestDate()),
                                        reservation.status.eq(ReservationStatus.CONFIRMED),
                                        schedule.startTime.between(
                                                startTime,
                                                startTime.plusHours(turnaround - 1)
                                        )
                                ).notExists()
                )
                .limit(5)
                .fetch();

        return matchedManagers;
    }

    /**
     * 매칭된 매니저 정보 조회
     * @param managerIds 매칭된 매니저 ID
     * @return 매니저 매칭 리스트
     */
    @Override
    public List<MatchedManagersInfo> getMatchingManagersInfo(Long customerId, List<Long> managerIds) {

        // 최근 예약 일자
        List<Tuple> recentReservationDate = queryFactory
                .select(
                        match.manager.userId,
                        schedule.requestDate.max()
                )
                .from(reservation)
                .leftJoin(match).on(match.reservation.eq(reservation))
                .leftJoin(schedule).on(schedule.reservation.eq(reservation))
                .where(
                        reservation.user.userId.eq(customerId),             // 수요자ID
                        match.manager.userId.in(managerIds),                // 매니저ID
                        reservation.status.eq(ReservationStatus.COMPLETED)
                )
                .groupBy(match.manager.userId)
                .fetch();

        // 1. 매니저별 최근 예약일자 설정
        Map<Long, LocalDate> recentDateMap = recentReservationDate.stream()
                .collect(Collectors.toMap(
                        r -> r.get(match.manager.userId),
                        r -> Optional.ofNullable(r.get(schedule.requestDate)).orElse(null),
                        (v1, v2) -> v1
                ));

        // 2. 예약 가능한 매니저 정보 추출
        List<Tuple> matchedManagers = queryFactory
                .select(
                        user.userId,
                        user.userName,
                        managerStatistic.averageRating,
                        managerStatistic.reviewCount,
                        managerStatistic.reservationCount,
                        manager.profileImageFileId.filePathsJson,
                        manager.bio
                )
                .from(user)
                .leftJoin(manager).on(manager.user.eq(user))
                .leftJoin(managerStatistic).on(managerStatistic.user.eq(user))
                .leftJoin(file).on(file.fileId.eq(manager.profileImageFileId.fileId))
                .where(manager.userId.in(managerIds))
                .fetch();

        // 4. Info Mapping
        return matchedManagers.stream()
                .map(tuple -> {
                    Long managerId = tuple.get(user.userId);
                    return MatchedManagersInfo.builder()
                            .managerId(managerId)
                            .managerName(tuple.get(user.userName))
                            .reviewCount(tuple.get(managerStatistic.reviewCount))
                            .reservationCount(tuple.get(managerStatistic.reservationCount))
                            .averageRating(tuple.get(managerStatistic.averageRating))
                            .profileImageId(tuple.get(manager.profileImageFileId.filePathsJson))
                            .bio(tuple.get(manager.bio))
                            .recentReservationDate(recentDateMap.get(managerId))
                            .build();
                })
                .toList();
    }

}
