package com.kernel.common.customer.repository;

import com.kernel.common.admin.dto.request.AdminInquirySearchReqDTO;
import com.kernel.common.customer.dto.response.CustomerInquiryRspDTO;
import com.kernel.common.customer.entity.CustomerInquiry;
import com.kernel.common.customer.entity.QCustomerInquiry;
import com.kernel.common.customer.entity.QCustomerReply;
import com.kernel.common.customer.entity.QInquiryCategory;
import com.kernel.common.global.enums.ReplyStatus;
import com.querydsl.core.Tuple;
import com.kernel.common.customer.entity.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CustomCustomerInquiryRepositoryImpl implements CustomCustomerInquiryRepository {

    private final JPAQueryFactory queryFactory;
    private final QCustomerInquiry inquiry = QCustomerInquiry.customerInquiry;
    private final QInquiryCategory category = QInquiryCategory.inquiryCategory;
    private final QCustomerReply reply = QCustomerReply.customerReply;
    private final CustomerRepository customerRepository;

    /**
     * 수요자 게시글 조회 및 검색
     * @param customerId 수요자 ID
     * @param startDate 최근 검색 날짜
     * @param pageable 페이징 정보
     * @return 검색된 문의사항 목록 (페이징 적용)
     */
    @Override
    public Page<CustomerInquiryRspDTO> searchByAuthorIdAndKeyword(Long customerId, LocalDateTime startDate, Pageable pageable) {

        BooleanExpression byAuthorIdAndStartDateAndNotDeleted = authorIdAndKeywordAndNotDeleted(customerId, startDate);

        // 총 개수 조회
        long total = Optional.ofNullable(
                queryFactory
                        .select(inquiry.count())
                        .from(inquiry)
                        .where(byAuthorIdAndStartDateAndNotDeleted)
                        .fetchOne()
        ).orElse(0L);

        if (total == 0)
            return new PageImpl<>(List.of(), pageable, 0);


        // 2) Tuple 조회
        List<Tuple> tuples = queryFactory
                .select(
                        inquiry.inquiryId,
                        inquiry.title,
                        inquiry.createdAt,
                        inquiry.category.categoryId,
                        inquiry.category.categoryName,
                        reply.answerId.isNotNull()        // BooleanExpression 도 Tuple 로 뽑을 수 있습니다
                )
                .from(inquiry)
                .leftJoin(inquiry.customerReply, reply)
                .where(byAuthorIdAndStartDateAndNotDeleted)
                .orderBy(inquiry.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 3) Tuple → DTO(builder) 매핑
        List<CustomerInquiryRspDTO> content = tuples.stream()
                .map(t -> CustomerInquiryRspDTO.builder()
                        .inquiryId(t.get(inquiry.inquiryId))
                        .categoryId(t.get(inquiry.category.categoryId))
                        .categoryName(t.get(inquiry.category.categoryName))
                        .title(t.get(inquiry.title))
                        .createdAt(t.get(inquiry.createdAt))
                        .isReplied(t.get(reply.answerId.isNotNull()))
                        .build()
                )
                .collect(Collectors.toList());

        return new PageImpl<>(content, pageable, total);
    }

    /**
     * 수요자 게시글 상세 조회
     * @param customerId 수요자 ID
     * @param inquiryId 문의사항 ID
     * @return 조회된 문의사항 (Optional)
     */
    @Override
    public Optional<CustomerInquiry> getCustomerInquiryDetails(Long customerId, Long inquiryId) {
        CustomerInquiry result = queryFactory
                .selectFrom(inquiry)
                .leftJoin(inquiry.category, category).fetchJoin()
                .leftJoin(inquiry.customerReply, reply).fetchJoin()
                .where(
                        inquiry.inquiryId.eq(inquiryId),
                        inquiry.authorId.eq(customerId),
                        inquiry.isDeleted.eq(false)
                )
                .fetchOne();

        return Optional.ofNullable(result);
    }

    /**
     * 문의사항 검색 조건 (작성자 ID + 작성일자 + 삭제 여부)
     * @param customerId 수요자 ID
     * @param startDate 작성일자
     * @return BooleanExpression 조건
     */
    private BooleanExpression authorIdAndKeywordAndNotDeleted(Long customerId, LocalDateTime startDate) {
        BooleanExpression condition = inquiry.authorId.eq(customerId);

        if (startDate != null ) {
            condition = condition.and(inquiry.createdAt.goe(startDate));
        }

        condition = condition.and(inquiry.isDeleted.eq(false));

        return condition;
    }

    /**
     * 수요자 문의사항 검색 (관리자용)
     * @param query 검색 조건 DTO
     * @param pageable 페이징 정보
     * @return 검색된 문의사항 목록 (페이징 적용)
     */
    @Override
    public Page<CustomerInquiry> searchCustomerInquiryByKeyword(AdminInquirySearchReqDTO query, Pageable pageable) {

        List<CustomerInquiry> results = queryFactory
                .selectFrom(inquiry)
                .leftJoin(inquiry.category, category).fetchJoin()
                .leftJoin(inquiry.customerReply, reply).fetchJoin()
                .where(
                        titleContains(query.getTitle()),
                        contentContains(query.getContent()),
                        createdAtGoe(query.getFromCreatedAt()),
                        createdAtLoe(query.getToCreatedAt()),
                        replyStatusCond(query.replyStatusFilter(), inquiry)
                )
                .orderBy(inquiry.inquiryId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(inquiry.count())
                .from(inquiry)
                .leftJoin(inquiry.category, category)
                .leftJoin(inquiry.customerReply, reply)
                .where(
                        titleContains(query.getTitle()),
                        contentContains(query.getContent()),
                        createdAtGoe(query.getFromCreatedAt()),
                        createdAtLoe(query.getToCreatedAt()),
                        replyStatusCond(query.replyStatusFilter(), inquiry)
                )
                .fetchOne();

        return new PageImpl<>(results, pageable, total);
    }

    private BooleanExpression titleContains(String title) {
        return title != null ? inquiry.title.containsIgnoreCase(title) : null;
    }

    private BooleanExpression contentContains(String content) {
        return content != null ? inquiry.content.containsIgnoreCase(content) : null;
    }

    private BooleanExpression createdAtGoe(LocalDateTime fromCreatedAt) {
        return fromCreatedAt != null ? inquiry.createdAt.goe(fromCreatedAt) : null;
    }

    private BooleanExpression createdAtLoe(LocalDateTime toCreatedAt) {
        return toCreatedAt != null ? inquiry.createdAt.loe(toCreatedAt) : null;
    }

    private BooleanExpression replyStatusCond(ReplyStatus replyStatus, QCustomerInquiry inquiry) {
        if (replyStatus == null) {
            return null;
        }

        switch (replyStatus.name()) {
            case "ANSWERED":
                return inquiry.customerReply.answerId.isNotNull();
            case "PENDING":
                return inquiry.customerReply.answerId.isNull();
            default:
                return null; // 유효하지 않은 상태 값인 경우 null 반환
        }
    }
}
