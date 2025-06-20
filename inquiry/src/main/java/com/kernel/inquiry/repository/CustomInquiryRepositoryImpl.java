package com.kernel.inquiry.repository;

import com.kernel.inquiry.domain.entity.QInquiry;
import com.kernel.inquiry.domain.info.InquirySummaryInfo;
import com.kernel.inquiry.service.dto.request.InquirySearchReqDTO;

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
public class CustomInquiryRepositoryImpl implements CustomInquiryRepository {

    private final JPQLQueryFactory jpaQueryFactory;

    /**
     * Inquiry 검색 및 페이징 처리
     *
     * @param request   검색 요청 DTO
     * @param authorId  작성자 ID
     * @param pageable  페이지 정보
     * @return 페이징된 Inquiry 결과
     */
    @Override
    public Page<InquirySummaryInfo> searchInquiriesWithPagination(InquirySearchReqDTO request, Long authorId, Pageable pageable) {
        QInquiry inquiry = QInquiry.inquiry;

        // 전체 개수 조회
        long total = Optional.ofNullable(
                jpaQueryFactory
                        .select(inquiry.count())
                        .from(inquiry)
                        .where(
                                authorEq(authorId),
                                notDeleted(),
                                createdAtGoe(request.getFromCreatedAt()),
                                createdAtLoe(request.getToCreatedAt()),
                                titleContains(request.getTitleKeyword()),
                                contentContains(request.getContentKeyword()),
                                replyStatusCond(request.getReplyStatus(), inquiry)
                        )
                        .fetchOne()
        ).orElse(0L);

        // 페이지 결과 조회
        List<InquirySummaryInfo> results = jpaQueryFactory
                .select(Projections.constructor(InquirySummaryInfo.class,
                        inquiry.inquiryId,
                        inquiry.title,
                        inquiry.content,
                        inquiry.createdAt,
                        inquiry.isReplied
                ))
                .where(
                        authorEq(authorId),
                        notDeleted(),
                        createdAtGoe(request.getFromCreatedAt()),
                        createdAtLoe(request.getToCreatedAt()),
                        titleContains(request.getTitleKeyword()),
                        contentContains(request.getContentKeyword()),
                        replyStatusCond(request.getReplyStatus(), inquiry)
                )
                .orderBy(inquiry.inquiryId.desc()) // 최신순 정렬
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(results, pageable, total);
    }

    /**
     * 작성자 ID로 Inquiry를 조회하는 조건식
     *
     * @param authorId 작성자 ID
     * @return 작성자 ID와 일치하는 조건식
     */
    private BooleanExpression authorEq(Long authorId) {
        if (authorId == null) {
            throw new IllegalArgumentException("작성자 ID는 필수입니다.");
        }
        return QInquiry.inquiry.authorId.eq(authorId);
    }

    /**
     * 삭제되지 않은 Inquiry를 조회하는 조건식
     *
     * @return 삭제되지 않은 Inquiry 조건식
     */
    private BooleanExpression notDeleted() {
        return QInquiry.inquiry.isDeleted.isFalse();
    }

    /**
     * 작성일이 지정된 날짜 이후인 Inquiry를 조회하는 조건식
     *
     * @param fromCreatedAt 시작 날짜
     * @return 지정된 날짜 이후인 Inquiry 조건식
     */
    private BooleanExpression createdAtGoe(LocalDateTime fromCreatedAt) {
        return fromCreatedAt != null ? QInquiry.inquiry.createdAt.goe(fromCreatedAt) : null;
    }

    /**
     * 작성일이 지정된 날짜 이전인 Inquiry를 조회하는 조건식
     *
     * @param toCreatedAt 종료 날짜
     * @return 지정된 날짜 이전인 Inquiry 조건식
     */
    private BooleanExpression createdAtLoe(LocalDateTime toCreatedAt) {
        return toCreatedAt != null ? QInquiry.inquiry.createdAt.loe(toCreatedAt) : null;
    }

    /**
     * 제목에 특정 키워드가 포함된 Inquiry를 조회하는 조건식
     *
     * @param titleKeyword 제목 키워드
     * @return 제목에 키워드가 포함된 Inquiry 조건식
     */
    private BooleanExpression titleContains(String titleKeyword) {
        return titleKeyword != null && !titleKeyword.isEmpty() ? QInquiry.inquiry.title.containsIgnoreCase(titleKeyword) : null;
    }

    /**
     * 내용에 특정 키워드가 포함된 Inquiry를 조회하는 조건식
     *
     * @param contentKeyword 내용 키워드
     * @return 내용에 키워드가 포함된 Inquiry 조건식
     */
    private BooleanExpression contentContains(String contentKeyword) {
        return contentKeyword != null && !contentKeyword.isEmpty() ? QInquiry.inquiry.content.containsIgnoreCase(contentKeyword) : null;
    }

    /**
     * 답변 상태에 따라 Inquiry를 조회하는 조건식
     *
     * @param replyStatus 답변 상태 (true: 답변 있음, false: 답변 없음)
     * @param inquiry QInquiry 인스턴스
     * @return 답변 상태에 따른 Inquiry 조건식
     */
    private BooleanExpression replyStatusCond(Boolean replyStatus, QInquiry inquiry) {
       return replyStatus != null && replyStatus ? inquiry.isReplied.isTrue() : inquiry.isReplied.isFalse();
    }
}
