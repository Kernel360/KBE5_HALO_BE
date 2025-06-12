package com.kernel.common.matching.repository;

import com.kernel.common.global.entity.QFeedback;
import com.kernel.common.global.enums.DayOfWeek;
import com.kernel.common.global.enums.FeedbackType;
import com.kernel.common.global.enums.UserStatus;
import com.kernel.common.manager.entity.Manager;
import com.kernel.common.manager.entity.QAvailableTime;
import com.kernel.common.manager.entity.QManager;
import com.kernel.common.matching.dto.ManagerMatchingRspDTO;
import com.kernel.common.reservation.dto.response.ReservationRspDTO;
import com.kernel.common.reservation.entity.QReservation;
import com.kernel.common.reservation.enums.ReservationStatus;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Repository
@RequiredArgsConstructor
public class CustomMatchingRepositoryImpl implements CustomMatchingRepository {

    private final JPAQueryFactory queryFactory;
    private final QManager manager = QManager.manager;
    private final QReservation reservation = QReservation.reservation;
    private final QAvailableTime availableTime = QAvailableTime.availableTime;
    private final QFeedback feedback = QFeedback.feedback;

    /**
     * 매니저 매칭 리스트 조회
     * @param reservationReqDTO 예약 요청 DTO
     * @return 매니저 매칭 리스트
     */
    public List<Manager> getMatchedManagers(ReservationRspDTO reservationReqDTO) {
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
                .select(availableTime.manager.managerId)
                .from(availableTime)
                .join(availableTime.manager, manager)
                .where(
                        manager.status.eq(UserStatus.ACTIVE),
                        manager.isMatching.isFalse(),
                        availableTime.dayOfWeek.eq(requestDay),
                        availableTime.time.in(requiredTimes)
                )
                .groupBy(availableTime.manager.managerId)
                .having(availableTime.manager.managerId.count().goe((long) turnaround))
                .fetch();

        // 4. 최종 매칭 매니저 조회
        List<Manager> matchedManagers = queryFactory
                .selectFrom(manager)
                .where(
                        manager.status.eq(UserStatus.ACTIVE),
/* 반경 5KM
                        Expressions.numberTemplate(Double.class,
                                "6371 * acos(cos(radians({0})) * cos(radians({1}.latitude)) * cos(radians({1}.longitude) - radians({2})) + sin(radians({0})) * sin(radians({1}.latitude)))",
                                reservationReqDTO.getLatitude(), manager, reservationReqDTO.getLongitude()
                        ).loe(5),
*/
                        manager.managerId.in(availableManagerIds),

                        JPAExpressions
                                .selectOne()
                                .from(reservation)
                                .where(
                                        reservation.manager.managerId.eq(manager.managerId),
                                        reservation.requestDate.eq(reservationReqDTO.getRequestDate()),
                                        reservation.status.ne(ReservationStatus.CANCELED),
                                        reservation.startTime.between(
                                                startTime,
                                                startTime.plusHours(turnaround - 1)
                                        )
                                ).notExists()
                )
                .limit(3)
                .fetch();

        return matchedManagers;
    }

    /**
     * 매칭된 매니저 정보 조회
     * @param customerId 수요자 ID
     * @param managerIds 매칭된 매니저 ID
     * @return 매니저 매칭 리스트
     */
    @Override
    public List<ManagerMatchingRspDTO> getMatchingManagersInfo(Long customerId, List<Long> managerIds) {
        // 최근 예약 일자
        List<Tuple> recentReservationDate = queryFactory
                .select(
                        reservation.manager.managerId,
                        reservation.requestDate
                )
                .from(reservation)
                .where(
                        reservation.customer.customerId.eq(customerId),
                        reservation.manager.managerId.in(managerIds),
                        reservation.status.eq(ReservationStatus.COMPLETED)
                )
                .fetch();

        // 좋아요/아쉬워요 여부
        List<Tuple> feedbackTypes = queryFactory
                .select(
                        feedback.manager.managerId,
                        feedback.type
                )
                .from(feedback)
                .where(
                        feedback.manager.managerId.in(managerIds),
                        feedback.customer.customerId.eq(customerId),
                        feedback.deleted.isFalse()
                )
                .fetch();

        // 1. recentReservationDate 튜플 → Map<Long, LocalDate>
        Map<Long, LocalDate> recentDateMap = recentReservationDate.stream()
                .collect(Collectors.toMap(
                        tuple -> tuple.get(reservation.manager.managerId),
                        tuple -> tuple.get(reservation.requestDate)
                ));

        // 2. feedbackTypes 튜플 → Map<Long, FeedbackType>
        Map<Long, FeedbackType> feedbackMap = feedbackTypes.stream()
                .collect(Collectors.toMap(
                        tuple -> tuple.get(feedback.manager.managerId),
                        tuple -> tuple.get(feedback.type)
                ));

        // 3. Fetch Manager entities by managerIds
        List<Tuple> matchedManagers = queryFactory
            .select(
                    manager.managerId,
                    manager.userName,
                    manager.averageRating,
                    manager.reviewCount,
                    manager.profileImageId,
                    manager.bio
            )
            .from(manager)
            .where(manager.managerId.in(managerIds))
            .fetch();

        // 4. Merge data and construct DTOs
        return matchedManagers.stream()
            .map(tuple -> {
                Long managerId = tuple.get(manager.managerId);
                return ManagerMatchingRspDTO.builder()
                    .managerId(managerId)
                    .managerName(tuple.get(manager.userName))
                    .averageRating(tuple.get(manager.averageRating))
                    .reviewCount(tuple.get(manager.reviewCount))
                    .profileImageId(tuple.get(manager.profileImageId))
                    .bio(tuple.get(manager.bio))
                    .recentReservationDate(recentDateMap.get(managerId))
                    .feedbackType(feedbackMap.get(managerId))
                    .build();
            })
            .toList();
    }


}
