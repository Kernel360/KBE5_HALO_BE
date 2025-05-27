package com.kernel.customer.repository;

import com.kernel.customer.entity.Inquiry;
import com.kernel.customer.entity.QInquiry;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class InquiryRepositoryImpl implements InquiryRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Inquiry> findByIdCustom(Long keyword) {
        QInquiry inquiry = QInquiry.inquiry;

        return jpaQueryFactory
                .selectFrom(inquiry)
                .where(
                        keyword != null ? inquiry.authorId.eq(keyword) : null
                )
                .fetch();
    }
}
