package com.kernel.inquiry.repository;

import com.kernel.inquiry.domain.entity.QInquiry;
import com.kernel.inquiry.service.info.InquirySummaryInfo;
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
    private final QInquiry inquiry = QInquiry.inquiry;

    /**
     * Inquiry 검색 및 페이징 처리
     *
     * @param searchReqDTO   검색 요청 DTO
     * @param userId  작성자 ID
     * @param pageable  페이지 정보
     * @return 페이징된 Inquiry 결과
     */
    @Override
    public Page<InquirySummaryInfo> searchInquiriesWithPagination(InquirySearchReqDTO searchReqDTO, Long userId, Pageable pageable) {

        // 전체 개수 조회
        long total = Optional.ofNullable(
                jpaQueryFactory
                        .select(inquiry.count())
                        .from(inquiry)
                        .where(
                                inquiry.authorId.eq(userId),
                                inquiry.isDeleted.isFalse(),
                                createdAtGoe(searchReqDTO.getFromCreatedAt()),
                                createdAtLoe(searchReqDTO.getToCreatedAt()),
                                titleContains(searchReqDTO.getTitleKeyword()),
                                contentContains(searchReqDTO.getContentKeyword()),
                                replyStatusCond(searchReqDTO.getReplyStatus(), inquiry)
                        )
                        .fetchOne()
        ).orElse(0L);

        // 페이지 결과 조회
        List<InquirySummaryInfo> results = jpaQueryFactory
                .select(Projections.fields(InquirySummaryInfo.class,
                        inquiry.inquiryId,
                        inquiry.categoryName,
                        inquiry.title,
                        inquiry.createdAt,
                        inquiry.isReplied,
                        inquiry.authorType
                ))
                .from(inquiry)
                .where(
                        inquiry.authorId.eq(userId),
                        inquiry.isDeleted.isFalse(),
                        createdAtGoe(searchReqDTO.getFromCreatedAt()),
                        createdAtLoe(searchReqDTO.getToCreatedAt()),
                        titleContains(searchReqDTO.getTitleKeyword()),
                        contentContains(searchReqDTO.getContentKeyword()),
                        replyStatusCond(searchReqDTO.getReplyStatus(), inquiry)
                )
                .orderBy(inquiry.createdAt.desc()) // 최신순 정렬
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(results, pageable, total);
    }

    /**
     * 작성일이 지정된 날짜 이후인 Inquiry를 조회하는 조건식
     *
     * @param fromCreatedAt 시작 날짜
     * @return 지정된 날짜 이후인 Inquiry 조건식
     */
    private BooleanExpression createdAtGoe(LocalDateTime fromCreatedAt) {
        return fromCreatedAt != null ? inquiry.createdAt.goe(fromCreatedAt) : null;
    }

    /**
     * 작성일이 지정된 날짜 이전인 Inquiry를 조회하는 조건식
     *
     * @param toCreatedAt 종료 날짜
     * @return 지정된 날짜 이전인 Inquiry 조건식
     */
    private BooleanExpression createdAtLoe(LocalDateTime toCreatedAt) {
        return toCreatedAt != null ? inquiry.createdAt.loe(toCreatedAt) : null;
    }

    /**
     * 제목에 특정 키워드가 포함된 Inquiry를 조회하는 조건식
     *
     * @param titleKeyword 제목 키워드
     * @return 제목에 키워드가 포함된 Inquiry 조건식
     */
    private BooleanExpression titleContains(String titleKeyword) {
        return titleKeyword != null && !titleKeyword.isEmpty() ? inquiry.title.containsIgnoreCase(titleKeyword) : null;
    }

    /**
     * 내용에 특정 키워드가 포함된 Inquiry를 조회하는 조건식
     *
     * @param contentKeyword 내용 키워드
     * @return 내용에 키워드가 포함된 Inquiry 조건식
     */
    private BooleanExpression contentContains(String contentKeyword) {
        return contentKeyword != null && !contentKeyword.isEmpty() ? inquiry.content.containsIgnoreCase(contentKeyword) : null;
    }

    /**
     * 답변 상태에 따라 Inquiry를 조회하는 조건식
     *
     * @param replyStatus 답변 상태 (true: 답변 있음, false: 답변 없음)
     * @param inquiry QInquiry 인스턴스
     * @return 답변 상태에 따른 Inquiry 조건식
     */
    private BooleanExpression replyStatusCond(Boolean replyStatus, QInquiry inquiry) {
        if (replyStatus == null)
            return null;

        return replyStatus ? inquiry.isReplied.isTrue() : inquiry.isReplied.isFalse();
    }
}
