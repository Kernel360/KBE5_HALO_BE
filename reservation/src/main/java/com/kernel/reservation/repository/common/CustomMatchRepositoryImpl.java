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
import com.kernel.reservation.service.request.ManagerReqDTO;
import com.kernel.sharedDomain.common.enums.ReservationStatus;
import com.kernel.sharedDomain.domain.entity.QReservation;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.DatePath;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Slf4j
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
     * @param managerReqDTO 매니저 요청
     * @return 매니저 매칭 리스트
     */
    public List<Long> getMatchedManagers(ManagerReqDTO managerReqDTO) {

        // 1. 예약 요일 Enum으로 변환
        DayOfWeek requestDay = DayOfWeek.fromJavaDayOfWeek(managerReqDTO.getRequestDate().getDayOfWeek());

        // 2. 시작 시간, 소요시간
        LocalTime startTime = managerReqDTO.getStartTime();
        int turnaround = managerReqDTO.getTurnaround();

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
        BigDecimal targetLat = managerReqDTO.getLatitude();
        BigDecimal targetLng = managerReqDTO.getLongitude();

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
                                        schedule.requestDate.eq(managerReqDTO.getRequestDate()),
                                        reservation.status.eq(ReservationStatus.CONFIRMED),
                                        schedule.startTime.between(
                                                startTime,
                                                startTime.plusHours(turnaround)
                                        )
                                ).notExists()
                )
                .fetch();

        return matchedManagers;
    }

    /**
     * 매칭된 매니저 정보 조회
     * @param managerIds 매칭된 매니저 ID
     * @return 매니저 매칭 리스트
     */
    @Override
    public Page<MatchedManagersInfo> getMatchingManagersInfo(Long customerId, List<Long> managerIds, Pageable pageable) {

        DatePath<LocalDate> maxRequestDate = Expressions.datePath(LocalDate.class, "maxRequestDate");

        // 1. 최근 예약 일자 조회
        List<Tuple> recentReservationDate = queryFactory
                .select(
                        match.manager.userId,
                        schedule.requestDate.max().as(maxRequestDate)
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

        // 2. 튜플에서 alias를 통해 값 추출
        Map<Long, LocalDate> recentDateMap = recentReservationDate.stream()
                .filter(t -> t.get(match.manager.userId) != null)
                .collect(Collectors.toMap(
                        t -> t.get(match.manager.userId),
                        t -> t.get(maxRequestDate)  // 반드시 같은 alias로 get
                ));

        // 3. Total count 조회
        Long totalCountResult = queryFactory
                .select(user.count())
                .from(user)
                .leftJoin(manager).on(manager.user.eq(user))
                .where(manager.userId.in(managerIds))
                .fetchOne();
        
        long totalCount = totalCountResult != null ? totalCountResult : 0L;

        // 4. 페이징된 매니저 정보 추출
        JPAQuery<Tuple> query = queryFactory
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
                .leftJoin(manager.specialty)
                .where(manager.userId.in(managerIds));

        // 5. 정렬 적용
        applySorting(query, pageable.getSort());

        // 6. 그 다음 페이징 + fetch
        List<Tuple> matchedManagers = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 6. Info Mapping
        List<MatchedManagersInfo> content = matchedManagers.stream()
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

        return new PageImpl<>(content, pageable, totalCount);
    }

    private void applySorting(JPAQuery<?> query, Sort sort){
        for (Sort.Order order : sort) {
            String prop = order.getProperty();
            boolean isAsc = order.isAscending();

            switch(prop){
                case "averageRating" -> query.orderBy(isAsc ?
                        managerStatistic.averageRating.asc() :
                        managerStatistic.averageRating.desc());
                case "reviewCount" -> query.orderBy(isAsc ?
                        managerStatistic.reviewCount.asc() :
                        managerStatistic.reviewCount.desc());
                case "reservationCount" -> query.orderBy(isAsc ?
                        managerStatistic.reservationCount.asc() :
                        managerStatistic.reservationCount.desc());
            }
        }
    }
}
