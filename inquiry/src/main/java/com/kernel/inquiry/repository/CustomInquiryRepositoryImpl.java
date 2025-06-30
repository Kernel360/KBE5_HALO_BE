package com.kernel.inquiry.repository;

import com.kernel.global.common.enums.UserRole;
import com.kernel.inquiry.common.exception.CustomNoDataFoundException;
import com.kernel.inquiry.domain.entity.QInquiry;
import com.kernel.inquiry.domain.info.InquirySummaryInfo;
import com.kernel.inquiry.service.dto.request.InquirySearchReqDTO;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.kernel.inquiry.domain.entity.QInquiry.inquiry;

@Repository
@RequiredArgsConstructor
public class CustomInquiryRepositoryImpl implements CustomInquiryRepository {

    private final JPQLQueryFactory jpaQueryFactory;
    private final QInquiry inquiry = QInquiry.inquiry;

    /**
     * Inquiry 검색 및 페이징 처리
     *
     * @param request   검색 요청 DTO
     * @param authorId  작성자 ID
     * @param pageable  페이지 정보
     * @return 페이징된 Inquiry 결과
     */
    @Override
    public Page<InquirySummaryInfo> searchInquiriesWithPagination(InquirySearchReqDTO request, Long authorId, Boolean isAdmin, Pageable pageable) {

        // 전체 개수 조회
        long total = Optional.ofNullable(
                jpaQueryFactory
                        .select(inquiry.count())
                        .from(inquiry)
                        .where(
                                authorEq(authorId, isAdmin),
                                authorRoleEq(request.getAuthorRole(), isAdmin),
                                notDeleted(),
                                createdAtGoe(request.getFromCreatedAt()),
                                createdAtLoe(request.getToCreatedAt()),
                                titleContains(request.getTitleKeyword()),
                                contentContains(request.getContentKeyword()),
                                replyStatusCond(request.getReplyStatus(), inquiry)
                        )
                        .fetchOne()
        ).orElse(0L);

        // 조회 개수가 0인 경우 예외 처리
        if (total == 0) {
            throw new CustomNoDataFoundException("조건에 맞는 문의사항이 없습니다.");
        }

        // 페이지 결과 조회
        List<InquirySummaryInfo> results = jpaQueryFactory
                .select(Projections.fields(InquirySummaryInfo.class,
                        inquiry.inquiryId,
                        inquiry.title,
                        inquiry.content,
                        inquiry.createdAt,
                        inquiry.isReplied
                ))
                .where(
                        authorEq(authorId, isAdmin),
                        authorRoleEq(request.getAuthorRole(), isAdmin),
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
     * 작성자 ID에 따라 Inquiry를 조회하는 조건식
     *
     * @param authorId 작성자 ID
     * @param isAdmin  관리자 여부
     * @return 작성자 ID에 따른 Inquiry 조건식
     */
    private BooleanExpression authorEq(Long authorId, Boolean isAdmin) {
        // 관리자일 경우 작성자 ID가 null인 경우 모든 Inquiry를 조회
        if (isAdmin != null && isAdmin) {
            return authorId == null ? null : inquiry.authorId.eq(authorId);
        }
        // 일반 사용자의 경우 작성자 ID가 null이 아니어야 함
        if (authorId == null) {
            throw new IllegalArgumentException("작성자 ID는 필수입니다.");
        }
        return inquiry.authorId.eq(authorId);
    }

    private BooleanExpression authorRoleEq(String authorRole, Boolean isAdmin) {
        // 관리자가 아닐 경우 null
        if (isAdmin != null && !isAdmin) {
            return Expressions.asBoolean(false);
        }

        // 관리자가 문의를 조회할 때 작성자 유형이 지정되어야 함
        if (isAdmin != null && isAdmin && (authorRole == null || authorRole.isEmpty())) {
            throw new IllegalArgumentException("관리자가 문의를 조회할 때 조회할 작성자 유형을 지정해야합니다.");
        }

        // 작성자 유형에 따라 조건식 생성
        if (UserRole.CUSTOMER.name().equals(authorRole)) {
            return inquiry.authorRole.eq(UserRole.CUSTOMER);
        } else if (UserRole.MANAGER.name().equals(authorRole)) {
            return inquiry.authorRole.eq(UserRole.MANAGER);
        } else {
            throw new IllegalArgumentException("유효하지 않은 작성자 유형입니다.");
        }
    }

    /**
     * 삭제되지 않은 Inquiry를 조회하는 조건식
     *
     * @return 삭제되지 않은 Inquiry 조건식
     */
    private BooleanExpression notDeleted() {
        return inquiry.isDeleted.isFalse();
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
       return replyStatus != null && replyStatus ? inquiry.isReplied.isTrue() : inquiry.isReplied.isFalse();
    }
}
