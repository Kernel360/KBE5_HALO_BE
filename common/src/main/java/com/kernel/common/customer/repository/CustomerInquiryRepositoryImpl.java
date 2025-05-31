package com.kernel.common.customer.repository;

import com.kernel.common.customer.entity.CustomerInquiry;
import  com.kernel.common.customer.entity.QCustomerInquiry;
import com.kernel.common.customer.entity.QCustomerReply;
import com.kernel.common.customer.entity.QInquiryCategory;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CustomerInquiryRepositoryImpl implements CustomerInquiryRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    private final QCustomerInquiry inquiry = QCustomerInquiry.customerInquiry;
    private final QInquiryCategory category = QInquiryCategory.inquiryCategory;
    private final QCustomerReply reply = QCustomerReply.customerReply;

    /* 수요자 게시글 조회 및 검색
    @Param : 수요자ID
    @Param : 검색키워드(제목)
    @Param : 페이징
    */
    @Override
    public Page<CustomerInquiry> searchByAuthorIdAndKeyword(Long customerId, String keyword, Pageable pageable) {

        // 공통 조건 추출
        BooleanExpression byAuthorIdAndKeywordAndNotDeleted = authorIdAndKeywordAndNotDeleted(customerId, keyword);

        // 게시글 검색
        List<CustomerInquiry> content = queryFactory
                .selectFrom(inquiry)
                .where(byAuthorIdAndKeywordAndNotDeleted)
                .orderBy(inquiry.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 전체 개수 조회
        long total = Optional.ofNullable(
                queryFactory
                .select(inquiry.count())
                .from(inquiry)
                .where(byAuthorIdAndKeywordAndNotDeleted)
                .fetchOne()
        ).orElse(0L);



        return new PageImpl<>(content, pageable, total);
    }

    /* 수요자 게시글 상세 조회
    * @Param : 수요자ID
    * @Param : 게시글ID
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

    // authorId, keyword 조건 체크
    private BooleanExpression authorIdAndKeywordAndNotDeleted(Long customerId, String keyword) {

        // 문의사항 작성자ID
        BooleanExpression condition = inquiry.authorId.eq(customerId);

        // 검색 keyword
        if(keyword != null && !keyword.isBlank()){
            condition = condition.and(inquiry.title.containsIgnoreCase(keyword));
        }

        // 삭제되지 않은 게시글
        condition = condition.and(inquiry.isDeleted.eq(false));

        return condition;
    }

}
