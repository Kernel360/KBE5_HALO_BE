package com.kernel.inquiry.repository;

import com.kernel.global.domain.entity.QUser;
import com.kernel.inquiry.common.enums.AuthorType;
import com.kernel.inquiry.domain.entity.QInquiry;
import com.kernel.inquiry.service.dto.request.InquiryAdminSearchReqDTO;
import com.kernel.inquiry.service.info.InquirySummaryInfo;
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
public class CustomInquiryAdminRepositoryImpl implements CustomInquiryAdminRepository {

    private final JPQLQueryFactory jpaQueryFactory;
    private final QInquiry inquiry = QInquiry.inquiry;
    private final QUser user = QUser.user;

    /**
     * Inquiry 검색 및 페이징 처리
     * @param searchReqDTO   검색 요청 DTO
     * @param pageable  페이지 정보
     * @return 페이징된 Inquiry 결과
     */
    @Override
    public Page<InquirySummaryInfo> searchInquiriesWithPagination(InquiryAdminSearchReqDTO searchReqDTO, Pageable pageable) {

        // 전체 개수 조회
        long total = Optional.ofNullable(
                jpaQueryFactory
                        .select(inquiry.count())
                        .from(inquiry)
                        .where(
                                userNameEq(searchReqDTO.getUserName()),
                                authorTypeEq(searchReqDTO.getAuthorRole()),
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
                        userNameEq(searchReqDTO.getUserName()),
                        authorTypeEq(searchReqDTO.getAuthorRole()),
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
     * 작성자 키워드 조건식
     * @param userName 시작 날짜
     * @return 작성자 이름 조건식
     */
    private BooleanExpression userNameEq(String userName) {
        return userName != null ?  user.userName.containsIgnoreCase(userName) : null;
    }

    /**
     * 작성자 타입 조건식
     * @param authorType 작성자 타입
     * @return 작성자 이름 조건식
     */
    private BooleanExpression authorTypeEq(String authorType) {
        return switch (authorType) {
            case "CUSTOMER" -> inquiry.authorType.eq(AuthorType.CUSTOMER);
            case "MANAGER" -> inquiry.authorType.eq(AuthorType.MANAGER);
            default -> throw new IllegalArgumentException("유효하지 않은 작성자 유형입니다.");
        };
    }

    /**
     * 작성일이 지정된 날짜 이후인 조건식
     *
     * @param fromCreatedAt 시작 날짜
     * @return 지정된 날짜 이후인 Inquiry 조건식
     */
    private BooleanExpression createdAtGoe(LocalDateTime fromCreatedAt) {
        return fromCreatedAt != null ? inquiry.createdAt.goe(fromCreatedAt) : null;
    }

    /**
     * 작성일이 지정된 날짜 이전인 조건식
     *
     * @param toCreatedAt 종료 날짜
     * @return 지정된 날짜 이전인 Inquiry 조건식
     */
    private BooleanExpression createdAtLoe(LocalDateTime toCreatedAt) {
        return toCreatedAt != null ? inquiry.createdAt.loe(toCreatedAt) : null;
    }

    /**
     * 제목에 특정 키워드가 포함된 조건식
     *
     * @param titleKeyword 제목 키워드
     * @return 제목에 키워드가 포함된 Inquiry 조건식
     */
    private BooleanExpression titleContains(String titleKeyword) {
        return titleKeyword != null && !titleKeyword.isEmpty() ? inquiry.title.containsIgnoreCase(titleKeyword) : null;
    }

    /**
     * 내용에 특정 키워드가 포함된 조건식
     *
     * @param contentKeyword 내용 키워드
     * @return 내용에 키워드가 포함된 Inquiry 조건식
     */
    private BooleanExpression contentContains(String contentKeyword) {
        return contentKeyword != null && !contentKeyword.isEmpty() ? inquiry.content.containsIgnoreCase(contentKeyword) : null;
    }

    /**
     * 답변 상태 조건식
     * @param replyStatus 답변 상태 (true: 답변 있음, false: 답변 없음)
     * @param inquiry QInquiry 인스턴스
     * @return 답변 상태에 따른 Inquiry 조건식
     */
    private BooleanExpression replyStatusCond(Boolean replyStatus, QInquiry inquiry) {
       return replyStatus != null && replyStatus ? inquiry.isReplied.isTrue() : inquiry.isReplied.isFalse();
    }
}
