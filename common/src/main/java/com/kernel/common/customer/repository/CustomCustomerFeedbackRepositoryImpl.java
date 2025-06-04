package com.kernel.common.customer.repository;

import com.kernel.common.customer.dto.response.CustomerFeedbackRspDTO;
import com.kernel.common.global.entity.QFeedback;
import com.kernel.common.global.enums.FeedbackType;
import com.kernel.common.manager.entity.QManager;
import com.kernel.common.reservation.entity.QReservation;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CustomCustomerFeedbackRepositoryImpl implements CustomCustomerFeedbackRepository {

    private final JPAQueryFactory queryFactory;
    private final QReservation reservation = QReservation.reservation;
    private final QManager manager = QManager.manager;
    private final QFeedback feedback = QFeedback.feedback;


    /**
     * 수요자 피드백 조회 및 검색
     * @param customerId 수요자ID
     * @param type 피드백 타입
     * @param pageable 페이징 정보
     * @return 검색된 피드백 목록
     */
    @Override
    public Page<CustomerFeedbackRspDTO> searchCustomerFeedbackByFeedbackType(Long customerId, FeedbackType type, Pageable pageable) {

        // 피드백 타입 조건 (nullable 대응)
        BooleanBuilder feedbackTypeCondition = new BooleanBuilder();
        if (type != null) {
            feedbackTypeCondition.and(feedback.type.eq(type));
        }

        // 수요자 피드백 조회 (예약일자 제외)
        List<Tuple> feedbacks = queryFactory
                .select(
                        feedback.feedbackId,            // 피드백ID
                        feedback.type,                  // 피드백 타입
                        manager.managerId,              // 매니저ID
                        manager.userName,               // 매니저 이름
                        manager.averageRating,          // 매니저 평균 별점
                        manager.reviewCount             // 매니저 리뷰 수
                )
                .from(feedback)
                .leftJoin(feedback.manager, manager)
                .where(
                        feedback.customer.customerId.eq(customerId),    // 수요자ID
                        feedback.deleted.isFalse(),                     // 삭제 여부
                        feedbackTypeCondition                           // 피드백 타입
                )
                .orderBy(feedback.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        if (feedbacks.isEmpty()) {
            return Page.empty(pageable);
        }

        // 매니저ID 모으기
        List<Long> managerIds = feedbacks.stream()
                .map(t -> t.get(manager.managerId))
                .distinct()
                .toList();

        // 매니저별 최근 예약 일자 조회
        Map<Long, LocalDate> latestRequestDates = queryFactory
                .select(
                        reservation.manager.managerId,
                        reservation.requestDate.max()
                )
                .from(reservation)
                .where(
                        reservation.manager.managerId.in(managerIds),
                        reservation.customer.customerId.eq(customerId)
                )
                .groupBy(reservation.manager.managerId)
                .fetch()
                .stream()
                .collect(Collectors.toMap(
                        tuple -> tuple.get(reservation.manager.managerId),
                        tuple -> tuple.get(reservation.requestDate.max())
                ));

        // tuple -> DTO 변환
        List<CustomerFeedbackRspDTO> content = feedbacks.stream()
                .map(tuple ->
                    CustomerFeedbackRspDTO.builder()
                            .feedbackId(tuple.get(feedback.feedbackId))
                            .feedbackType(tuple.get(feedback.type))
                            .managerId(tuple.get(manager.managerId))
                            .managerName(tuple.get(manager.userName))
                            .averageRating(tuple.get(manager.averageRating))
                            .reviewCount(tuple.get(manager.reviewCount))
                            .requestDate(
                                    latestRequestDates.get(tuple.get(manager.managerId))
                            )
                            .build()
                ).toList();

        // 전체 개수 조회
        Long total = queryFactory
                .select(feedback.count())
                .from(feedback)
                .where(
                        feedback.customer.customerId.eq(customerId),
                        feedback.deleted.isFalse(),
                        feedbackTypeCondition
                )
                .fetchOne();

        return new PageImpl<>(content, pageable, total != null ? total : 0L);
    }


}
