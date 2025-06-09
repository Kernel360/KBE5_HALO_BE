package com.kernel.common.customer.repository;

import com.kernel.common.admin.dto.request.AdminInquirySearchReqDTO;
import com.kernel.common.admin.repository.AdminRepository;
import com.kernel.common.customer.entity.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

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
     * @param keyword 검색 키워드 (제목 기준)
     * @param pageable 페이징 정보
     * @return 검색된 문의사항 목록 (페이징 적용)
     */
    @Override
    public Page<CustomerInquiry> searchByAuthorIdAndKeyword(Long customerId, String keyword, Pageable pageable) {

        BooleanExpression byAuthorIdAndKeywordAndNotDeleted = authorIdAndKeywordAndNotDeleted(customerId, keyword);

        List<CustomerInquiry> content = queryFactory
                .selectFrom(inquiry)
                .where(byAuthorIdAndKeywordAndNotDeleted)
                .orderBy(inquiry.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = Optional.ofNullable(
                queryFactory
                        .select(inquiry.count())
                        .from(inquiry)
                        .where(byAuthorIdAndKeywordAndNotDeleted)
                        .fetchOne()
        ).orElse(0L);

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
     * 문의사항 검색 조건 (작성자 ID + 키워드 + 삭제 여부)
     * @param customerId 수요자 ID
     * @param keyword 검색 키워드
     * @return BooleanExpression 조건
     */
    private BooleanExpression authorIdAndKeywordAndNotDeleted(Long customerId, String keyword) {
        BooleanExpression condition = inquiry.authorId.eq(customerId);

        if (keyword != null && !keyword.isBlank()) {
            condition = condition.and(inquiry.title.containsIgnoreCase(keyword));
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
        BooleanExpression condition = inquiry.isDeleted.eq(false);

        if (query.getAuthorName() != null && !query.getAuthorName().isBlank()) {
            Optional<Customer> customer = customerRepository.findByUserName(query.getAuthorName());
            if (customer.isEmpty()) {
                return new PageImpl<>(List.of(), pageable, 0);
            }
            condition = condition.and(inquiry.authorId.eq(customer.get().getCustomerId()));
        }

        if (query.getTitle() != null && !query.getTitle().isBlank()) {
            condition = condition.and(inquiry.title.containsIgnoreCase(query.getTitle()));
        }

        if (query.getCategory() != null) {
            condition = condition.and(inquiry.category.categoryName.eq(query.getCategory()));
        }

        List<CustomerInquiry> content = queryFactory
                .selectFrom(inquiry)
                .leftJoin(inquiry.category, category).fetchJoin()
                .where(condition)
                .orderBy(inquiry.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = Optional.ofNullable(
                queryFactory
                        .select(inquiry.count())
                        .from(inquiry)
                        .where(condition)
                        .fetchOne()
        ).orElse(0L);

        return new PageImpl<>(content, pageable, total);
    }
}
