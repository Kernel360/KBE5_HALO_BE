package com.kernel.common.manager.repository;

import com.kernel.common.global.enums.ReplyStatus;
import com.kernel.common.manager.entity.QManagerInquiry;
import com.kernel.common.manager.entity.QManagerReply;
import com.querydsl.core.Tuple;
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
public class CustomManagerInquiryRepositoryImpl implements CustomManagerInquiryRepository {

    private final JPQLQueryFactory jpaQueryFactory;

    @Override
    public Page<Tuple> searchManagerinquiriesWithPaging(
        Long authorId,
        LocalDateTime fromCreatedAt, LocalDateTime toCreatedAt, ReplyStatus replyStatus, String titleKeyword, String contentKeyword,
        Pageable pageable) {

        QManagerInquiry managerInquiry = QManagerInquiry.managerInquiry;
        QManagerReply managerReply = QManagerReply.managerReply;

        // 전체 개수 조회
        long total = Optional.ofNullable(
            jpaQueryFactory
                .select(managerInquiry.count())
                .from(managerInquiry)
                .where(
                    authorEq(authorId),             // 작성자 ID 일치
                    notDeleted(),                   // 삭제되지 않은 게시글
                    createdAtGoe(fromCreatedAt),    // 작성일 ≥ 시작일
                    createdAtLoe(toCreatedAt),      // 작성일 ≤ 종료일
                    replyStatusCond(replyStatus, managerInquiry),   // 답변 상태 필터
                    titleContains(titleKeyword),    // 제목 검색어 포함
                    contentContains(contentKeyword) // 내용 검색어 포함
                )
                .fetchOne()
        ).orElse(0L);

        // 페이지 결과 조회
        // managerReply에서는 inquiryId만 조회 (= 답변 여부 체크)
        List<Tuple> results = jpaQueryFactory
            .select(managerInquiry, managerReply.answerId)
            .from(managerInquiry)
            .leftJoin(managerInquiry.managerReply, managerReply)
            .where(
                authorEq(authorId),             // 작성자 ID 일치
                notDeleted(),                   // 삭제되지 않은 게시글
                createdAtGoe(fromCreatedAt),    // 작성일 >= 시작일
                createdAtLoe(toCreatedAt),      // 작성일 <= 종료일
                replyStatusCond(replyStatus, managerInquiry),   // 답변 상태 필터
                titleContains(titleKeyword),    // 제목 검색어 포함
                contentContains(contentKeyword) // 내용 검색어 포함
            )
            .offset(pageable.getOffset())               // 시작 위치
            .limit(pageable.getPageSize())              // 페이지 사이즈
            .orderBy(managerInquiry.inquiryId.desc())   // 정렬(게시글ID 기준 내림차순)
            .fetch();

        return new PageImpl<>(results, pageable, total);
    }

    // 작성자 ID 일치
    private BooleanExpression authorEq(Long authorId) {
        return QManagerInquiry.managerInquiry.authorId.eq(authorId);
    }

    // 삭제되지 않은 게시글
    private BooleanExpression notDeleted() {
        return QManagerInquiry.managerInquiry.isDeleted.isFalse();
    }

    // 작성일 >= 시작일
    private BooleanExpression createdAtGoe(LocalDateTime from) {
        return from != null ? QManagerInquiry.managerInquiry.createdAt.goe(from) : null;
    }

    // 작성일시 <= 종료일
    private BooleanExpression createdAtLoe(LocalDateTime to) {
        return to != null ? QManagerInquiry.managerInquiry.createdAt.loe(to) : null;
    }

    // 답변 상태 필터
    private BooleanExpression replyStatusCond(ReplyStatus status, QManagerInquiry managerInquiry) {
        if (status == null) return null;
        return switch (status) {
            case ANSWERED -> managerInquiry.managerReply.isNotNull(); // 답변이 존재함
            case PENDING -> managerInquiry.managerReply.isNull();     // 답변이 없음
        };
    }

    // 제목 검색어 포함
    private BooleanExpression titleContains(String keyword) {
        return keyword != null && !keyword.isBlank()
            ? QManagerInquiry.managerInquiry.title.containsIgnoreCase(keyword)
            : null;
    }

    // 내용 검색어 포함
    private BooleanExpression contentContains(String keyword) {
        return keyword != null && !keyword.isBlank()
            ? QManagerInquiry.managerInquiry.content.containsIgnoreCase(keyword)
            : null;
    }
}
