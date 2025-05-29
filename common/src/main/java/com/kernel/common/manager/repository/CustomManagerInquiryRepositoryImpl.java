package com.kernel.common.manager.repository;

import com.kernel.common.enums.ReplyStatus;
import com.kernel.common.manager.entity.QManagerInquiry;
import com.kernel.common.manager.entity.QManagerReply;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
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
public class CustomManagerInquiryRepositoryImpl implements CustomManagerInquiryRepository {

    private final JPQLQueryFactory jpaQueryFactory;

    @Override
    public Page<Tuple> searchManagerinquiriesWithPaging(
        Long authorId,
        LocalDateTime fromCreatedAt, LocalDateTime toCreatedAt, ReplyStatus replyStatus, String titleKeyword, String contentKeyword,
        Pageable pageable) {

        QManagerInquiry managerInquiry = QManagerInquiry.managerInquiry;
        QManagerReply managerReply = QManagerReply.managerReply;
        BooleanBuilder builder = new BooleanBuilder();

        // 작성자 ID(= 매니저 ID)로 작성된 게시글만
        builder.and(managerInquiry.authorId.eq(authorId));

        // 삭제되지 않은 게시글만
        builder.and(managerInquiry.isDeleted.isFalse());

        // 작성일시 시작일
        if (fromCreatedAt != null) {
            builder.and(managerInquiry.createdAt.goe(fromCreatedAt));
        }

        // 작성일시 종료일
        if (toCreatedAt != null) {
            builder.and(managerInquiry.createdAt.loe(toCreatedAt));
        }

        // 답변상태
        if (replyStatus != null) {
            switch (replyStatus) {
                case ANSWERED -> builder.and(managerReply.inquiryId.isNotNull());   // 답변완료
                case PENDING -> builder.and(managerReply.inquiryId.isNull());       // 대기중(=미답변)
            }
        }

        // 제목 검색
        if (titleKeyword != null && !titleKeyword.isBlank()) {
            builder.and(managerInquiry.title.containsIgnoreCase(titleKeyword));
        }

        // 내용 검색
        if (contentKeyword != null && !contentKeyword.isBlank()) {
            builder.and(managerInquiry.content.containsIgnoreCase(contentKeyword));
        }

        // 전체 개수 조회
        long total = Optional.ofNullable(
            jpaQueryFactory
                .select(managerInquiry.count())
                .from(managerInquiry)
                .where(builder)
                .fetchOne()
        ).orElse(0L);

        // 페이지 결과 조회
        // managerReply에서는 inquiryId만 조회 (= 답변 여부 체크)
        List<Tuple> results = jpaQueryFactory
            .select(managerInquiry,managerReply.inquiryId)
            .from(managerInquiry)
            .leftJoin(managerInquiry.managerReply, managerReply)
            .where(builder)
            .offset(pageable.getOffset())               // 시작 위치
            .limit(pageable.getPageSize())              // 페이지 사이즈
            .orderBy(managerInquiry.inquiryId.desc())   // 정렬(게시글ID 기준 내림차순)
            .fetch();

        return new PageImpl<>(results, pageable, total);
    }
}
