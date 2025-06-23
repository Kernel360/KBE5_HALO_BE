package com.kernel.evaluation.repository.review;


import com.kernel.evaluation.common.enums.AuthorType;
import com.kernel.evaluation.domain.entity.QReview;
import com.kernel.evaluation.domain.info.ManagerReviewInfo;
import com.kernel.global.domain.entity.QUser;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomManagerReviewRepositoryImpl implements CustomManagerReviewRepository {

    private final JPQLQueryFactory jpaQueryFactory;

    @Override
    public Page<ManagerReviewInfo> searchManagerReviewWithPaging(
        Long managerId,
        LocalDateTime fromCreatedAt, LocalDateTime toCreatedAt, Integer ratingOption, String customerNameKeyword, String contentKeyword,
        Pageable pageable
    )
    {
        QReview review = QReview.review;
        QUser user = QUser.user;

        // 전체 개수 조회
        long total = Optional.ofNullable(
            jpaQueryFactory
                .select(review.count())
                .from(review)
                .where(
                    targetIdEq(managerId),                      // 매니저 ID 일치
                    authorTypeEq(AuthorType.CUSTOMER),          // 작성자 타입 일치
                    createdAtGoe(fromCreatedAt),                // 작성일 ≥ 시작일
                    createdAtLoe(toCreatedAt),                  // 작성일 ≤ 종료일
                    ratingEq(ratingOption),                     // 평점 일치
                    customerNameContains(customerNameKeyword),  // 고객명 검색어 포함
                    contentContains(contentKeyword)             // 내용 검색어 포함
                )
                .fetchOne()
        ).orElse(0L);

        // 페이지 결과 조회
        // managerReply에서는 inquiryId만 조회 (= 답변 여부 체크)
        List<ManagerReviewInfo> results = jpaQueryFactory
            .select(Projections.constructor(
                    ManagerReviewInfo.class,
                    review.reviewId.as("reviewId"),
                    review.reservation.reservationId.as("reservationId"),
                    review.authorId.as("authorId"),
                    user.userName.as("authorName"),
                    review.rating.as("rating"),
                    review.content.as("content"),
                    review.reservation.serviceCategory.serviceId.as("serviceId"),
                    review.reservation.serviceCategory.serviceName.as("serviceName"),
                    review.createdAt.as("createdAt")
            ))
            .from(review)
            .leftJoin(user).on(user.userId.eq(review.authorId))
            .where(
                targetIdEq(managerId),                      // 매니저 ID 일치
                authorTypeEq(AuthorType.CUSTOMER),          // 작성자 타입 일치
                createdAtGoe(fromCreatedAt),                // 작성일 ≥ 시작일
                createdAtLoe(toCreatedAt),                  // 작성일 ≤ 종료일
                ratingEq(ratingOption),                     // 평점 일치
                customerNameContains(customerNameKeyword),  // 고객명 검색어 포함
                contentContains(contentKeyword)             // 내용 검색어 포함
            )
            .offset(pageable.getOffset())               // 시작 위치
            .limit(pageable.getPageSize())              // 페이지 사이즈
            .orderBy(review.reviewId.desc())     // 정렬(리뷰ID 기준 내림차순)
            .fetch();

        return new PageImpl<>(results, pageable, total);
    }

    // 매니저 ID 일치
    private BooleanExpression targetIdEq(Long managerId) {
        return QReview.review.targetId.eq(managerId);
    }
    
    // 작성자 타입 일치
    private BooleanExpression authorTypeEq(AuthorType authorType) {
        return QReview.review.authorType.eq(authorType);
    }

    // 작성일 >= 시작일
    private BooleanExpression createdAtGoe(LocalDateTime from) {
        return from != null ? QReview.review.createdAt.goe(from) : null;
    }

    // 작성일시 <= 종료일
    private BooleanExpression createdAtLoe(LocalDateTime to) {
        return to != null ? QReview.review.createdAt.loe(to) : null;
    }

    // 평점 일치
    private BooleanExpression ratingEq(Integer ratingOption) {
        return ratingOption != null ? QReview.review.rating.eq(ratingOption) : null;
    }

    // 고객명 검색어 포함
    private BooleanExpression customerNameContains(String keyword) {
        return keyword != null && !keyword.isBlank()
            ? QReview.review.reservation.user.userName.containsIgnoreCase(keyword)
            : null;
    }

    // 내용 검색어 포함
    private BooleanExpression contentContains(String keyword) {
        return keyword != null && !keyword.isBlank()
            ? QReview.review.content.containsIgnoreCase(keyword)
            : null;
    }
}
