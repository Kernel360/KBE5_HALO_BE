package com.kernel.evaluation.repository.feedback;


import com.kernel.evaluation.common.enums.FeedbackType;
import com.kernel.evaluation.domain.entity.QFeedback;
import com.kernel.evaluation.domain.info.CustomerFeedbackInfo;
import com.kernel.global.common.enums.UserStatus;
import com.kernel.global.domain.entity.QUser;
import com.kernel.reservation.domain.entity.QReservation;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class CustomCustomerFeedbackRepositoryImpl implements CustomCustomerFeedbackRepository {

    private final JPAQueryFactory queryFactory;
    private final QReservation reservation = QReservation.reservation;
    private final QFeedback feedback = QFeedback.feedback;
    private final QUser user = QUser.user;


    /**
     * 수요자 피드백 조회 및 검색
     * @param userId 수요자ID
     * @param type 피드백 타입
     * @param pageable 페이징 정보
     * @return 검색된 피드백 목록
     */
    @Override
    public Page<CustomerFeedbackInfo> searchFeedbackByFeedbackType(Long userId, FeedbackType type, Pageable pageable) {

        // 피드백 타입 조건 (nullable 대응)
        BooleanBuilder feedbackTypeCondition = new BooleanBuilder();

        if (type != null) {
            feedbackTypeCondition.and(feedback.type.eq(type));
        }

        // 1. 수요자 피드백 조회 및 info 변환
        List<CustomerFeedbackInfo> feedbacks = queryFactory
                .select(Projections.fields(CustomerFeedbackInfo.class,
                        feedback.feedbackId,            // 피드백ID
                        feedback.type,                  // 피드백 타입
                        feedback.manager.userId,        // 매니저ID
                        feedback.manager.userName       // 매니저 이름
                ))
                .from(feedback)
                .leftJoin(feedback.manager, user)
                .where(
                        feedback.customer.userId.eq(userId),            // 수요자ID
                        feedback.deleted.isFalse(),                     // 삭제 여부
                        feedback.manager.status.eq(UserStatus.ACTIVE),  // 매니저 상태
                        feedbackTypeCondition                           // 피드백 타입
                )
                .orderBy(feedback.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        if (feedbacks.isEmpty()) {
            return Page.empty(pageable);
        }

        // 전체 개수 조회
        Long total = queryFactory
                .select(feedback.count())
                .from(feedback)
                .where(
                        feedback.customer.userId.eq(userId),
                        feedback.deleted.isFalse(),
                        feedbackTypeCondition
                )
                .fetchOne();

        return new PageImpl<>(feedbacks, pageable, total != null ? total : 0L);
    }


}
