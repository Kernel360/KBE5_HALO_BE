package com.kernel.common.manager.repository;

import com.kernel.common.customer.entity.QCustomer;
import com.kernel.common.global.enums.AuthorType;
import com.kernel.common.manager.dto.response.ManagerReviewSummaryRspDTO;
import com.kernel.common.reservation.entity.QReview;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomManagerReviewRepositoryImpl implements CustomManagerReviewRepository {

    private final JPQLQueryFactory jpaQueryFactory;

    @Override
    public Page<ManagerReviewSummaryRspDTO> searchManagerReviewWithPaging(
        Long managerId,
        LocalDateTime fromCreatedAt, LocalDateTime toCreatedAt, Integer ratingOption, String customerNameKeyword, String contentKeyword,
        Pageable pageable) {

        QReview managerReview = QReview.review;
        QCustomer customer = QCustomer.customer;

        // 전체 개수 조회
        long total = Optional.ofNullable(
            jpaQueryFactory
                .select(managerReview.count())
                .from(managerReview)
                .where(
                    managerIdEq(managerId),                     // 매니저 ID 일치
                    authroTypeEq(AuthorType.CUSTOMER),          // 작성자 타입 일치
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
        List<ManagerReviewSummaryRspDTO> results = jpaQueryFactory
            .select(Projections.constructor(
                ManagerReviewSummaryRspDTO.class,
                managerReview.reviewId.as("reviewId"),
                managerReview.reservation.reservationId.as("reservationId"),
                managerReview.authorId.as("authorId"),
                customer.userName.as("authorName"),
                managerReview.rating.as("rating"),
                managerReview.content.as("content"),
                managerReview.reservation.serviceCategory.serviceId.as("serviceId"),
                managerReview.reservation.serviceCategory.serviceName.as("serviceName"),
                managerReview.createdAt.as("createdAt")
            ))
            .from(managerReview)
            .leftJoin(customer).on(customer.customerId.eq(managerReview.authorId))
            .where(
                managerIdEq(managerId),                     // 매니저 ID 일치
                authroTypeEq(AuthorType.CUSTOMER),          // 작성자 타입 일치
                createdAtGoe(fromCreatedAt),                // 작성일 ≥ 시작일
                createdAtLoe(toCreatedAt),                  // 작성일 ≤ 종료일
                ratingEq(ratingOption),                     // 평점 일치
                customerNameContains(customerNameKeyword),  // 고객명 검색어 포함
                contentContains(contentKeyword)             // 내용 검색어 포함
            )
            .offset(pageable.getOffset())               // 시작 위치
            .limit(pageable.getPageSize())              // 페이지 사이즈
            .orderBy(managerReview.reviewId.desc())     // 정렬(리뷰ID 기준 내림차순)
            .fetch();

        return new PageImpl<>(results, pageable, total);
    }

    // 매니저 ID 일치
    private BooleanExpression managerIdEq(Long managerId) {
        return QReview.review.reservation.manager.managerId.eq(managerId);
    }
    
    // 작성자 타입 일치
    private BooleanExpression authroTypeEq(AuthorType authorType) {
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
            ? QReview.review.reservation.customer.userName.containsIgnoreCase(keyword)
            : null;
    }

    // 내용 검색어 포함
    private BooleanExpression contentContains(String keyword) {
        return keyword != null && !keyword.isBlank()
            ? QReview.review.content.containsIgnoreCase(keyword)
            : null;
    }
}
