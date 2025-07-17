package com.kernel.inquiry.repository;

import com.kernel.global.domain.entity.QUser;
import com.kernel.inquiry.common.enums.AuthorType;
import com.kernel.inquiry.common.exception.InquiryNotFoundException;
import com.kernel.inquiry.domain.entity.QInquiry;
import com.kernel.inquiry.domain.entity.QReply;
import com.kernel.inquiry.service.dto.request.InquiryAdminSearchReqDTO;
import com.kernel.inquiry.service.info.InquiryAdminDetailInfo;
import com.kernel.inquiry.service.info.InquiryAdminSummaryInfo;
import com.kernel.inquiry.service.info.ReplyAdminDetailInfo;
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

import static com.kernel.inquiry.common.enums.InquiryErrorCode.INQUIRY_NOT_FOUND;

@Repository
@RequiredArgsConstructor
public class CustomInquiryAdminRepositoryImpl implements CustomInquiryAdminRepository {

    private final JPQLQueryFactory jpaQueryFactory;
    private final QInquiry inquiry = QInquiry.inquiry;
    private final QReply reply = QReply.reply;
    private final QUser user = QUser.user;

    /**
     * Inquiry 검색 및 페이징 처리
     * @param searchReqDTO   검색 요청 DTO
     * @param pageable  페이지 정보
     * @return 페이징된 Inquiry 결과
     */
    @Override
    public Page<InquiryAdminSummaryInfo> searchInquiriesWithPagination(InquiryAdminSearchReqDTO searchReqDTO, Pageable pageable) {

        System.out.println(searchReqDTO.getReplyStatus());

        // 전체 개수 조회
        long total = Optional.ofNullable(
                jpaQueryFactory
                        .select(inquiry.count())
                        .from(inquiry)
                        .join(user).on(inquiry.authorId.eq(user.userId))
                        .where(
                                authorIdEq(searchReqDTO.getAuthorId()),
                                userNameEq(searchReqDTO.getUserName()),
                                authorTypeEq(searchReqDTO.getAuthorType()),
                                createdAtGoe(searchReqDTO.getFromCreatedAt()),
                                createdAtLoe(searchReqDTO.getToCreatedAt()),
                                titleContains(searchReqDTO.getTitleKeyword()),
                                contentContains(searchReqDTO.getContentKeyword()),
                                replyStatusCond(searchReqDTO.getReplyStatus()),
                                categoryContains(searchReqDTO.getCategories()),
                                inquiry.isDeleted.isFalse()
                                )
                        .fetchOne()
        ).orElse(0L);

        // 페이지 결과 조회
        List<InquiryAdminSummaryInfo> results = jpaQueryFactory
                .select(Projections.fields(InquiryAdminSummaryInfo.class,
                        inquiry.inquiryId,
                        inquiry.categoryName,
                        inquiry.title,
                        inquiry.createdAt,
                        inquiry.isReplied,
                        inquiry.authorType,
                        user.userName
                ))
                .from(inquiry)
                .join(user).on(inquiry.authorId.eq(user.userId))
                .where(
                        authorIdEq(searchReqDTO.getAuthorId()),
                        userNameEq(searchReqDTO.getUserName()),
                        authorTypeEq(searchReqDTO.getAuthorType()),
                        createdAtGoe(searchReqDTO.getFromCreatedAt()),
                        createdAtLoe(searchReqDTO.getToCreatedAt()),
                        titleContains(searchReqDTO.getTitleKeyword()),
                        contentContains(searchReqDTO.getContentKeyword()),
                        replyStatusCond(searchReqDTO.getReplyStatus()),
                        categoryContains(searchReqDTO.getCategories()),
                        inquiry.isDeleted.isFalse()
                        )
                .orderBy(inquiry.createdAt.desc()) // 최신순 정렬
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(results, pageable, total);
    }

    /**
     * Inquiry 상세 조회
     * @param inquiryId 문의사항ID
     * @return  Inquiry 결과
     */
    @Override
    public InquiryAdminDetailInfo getInquiryDetails(Long inquiryId) {

        // 1. 문의사항 조회
        InquiryAdminDetailInfo inquiryInfo = jpaQueryFactory
                .select(Projections.fields(InquiryAdminDetailInfo.class,
                        inquiry.inquiryId,
                        inquiry.categoryName,
                        inquiry.title,
                        inquiry.content,
                        inquiry.authorId,
                        user.userName.as("authorName"),
                        inquiry.authorType,
                        user.phone,
                        user.email,
                        inquiry.fileId,
                        inquiry.createdAt
                ))
                .from(inquiry)
                .join(user).on(inquiry.authorId.eq(user.userId))
                .where(inquiry.inquiryId.eq(inquiryId))
                .fetchOne();

        if(inquiryInfo == null)
            throw new InquiryNotFoundException(INQUIRY_NOT_FOUND);

        // 2. 답변 조회
        ReplyAdminDetailInfo replyInfo = jpaQueryFactory
                .select(Projections.fields(ReplyAdminDetailInfo.class,
                        reply.replyId,
                        user.userName,
                        reply.content,
                        reply.fileId,
                        reply.createdAt
                ))
                .from(reply)
                .join(user).on(reply.authorId.eq(user.userId))
                .where(reply.inquiryId.inquiryId.eq(inquiryId))
                .fetchOne();

        // 3. 답변 있을 경우, 초기화
        if(replyInfo != null)
            inquiryInfo.initReplyInfo(replyInfo);

        return inquiryInfo;
    }

    /**
     * 작성자 ID 조건식
     * @param authorId 작성자 ID
     * @return 작성자 ID 조건식
     */
    private BooleanExpression authorIdEq(Long authorId) {
        return authorId != null ? inquiry.authorId.eq(authorId) : null;
    }

    /**
     * 작성자 이름 키워드 조건식
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
    private BooleanExpression authorTypeEq(AuthorType authorType) {
        return authorType != null ? inquiry.authorType.eq(authorType) : null;
    }

    /**
     * 작성일이 지정된 날짜 이후인 조건식
     * @param fromCreatedAt 시작 날짜
     * @return 지정된 날짜 이후인 Inquiry 조건식
     */
    private BooleanExpression createdAtGoe(LocalDateTime fromCreatedAt) {
        return fromCreatedAt != null ? inquiry.createdAt.goe(fromCreatedAt) : null;
    }

    /**
     * 작성일이 지정된 날짜 이전인 조건식
     * @param toCreatedAt 종료 날짜
     * @return 지정된 날짜 이전인 Inquiry 조건식
     */
    private BooleanExpression createdAtLoe(LocalDateTime toCreatedAt) {
        return toCreatedAt != null ? inquiry.createdAt.loe(toCreatedAt) : null;
    }

    /**
     * 제목 키워드 조건식
     * @param titleKeyword 제목 키워드
     * @return 제목에 키워드가 포함된 Inquiry 조건식
     */
    private BooleanExpression titleContains(String titleKeyword) {
        return titleKeyword != null && !titleKeyword.isEmpty() ? inquiry.title.containsIgnoreCase(titleKeyword) : null;
    }

    /**
     * 내용 키워드 조건식
     * @param contentKeyword 내용 키워드
     * @return 내용에 키워드가 포함된 Inquiry 조건식
     */
    private BooleanExpression contentContains(String contentKeyword) {
        return contentKeyword != null && !contentKeyword.isEmpty() ? inquiry.content.containsIgnoreCase(contentKeyword) : null;
    }

    /**
     * 답변 상태 조건식
     * @param replyStatus 답변 상태 (true: 답변 있음, false: 답변 없음)
     * @return 답변 상태에 따른 Inquiry 조건식
     */
    private BooleanExpression replyStatusCond(Boolean replyStatus) {
        if (replyStatus == null)
            return null;

        return replyStatus ? inquiry.isReplied.isTrue() : inquiry.isReplied.isFalse();
    }

    /**
     * 카테고리 포함 조건식
     * @param categories 카테고리 List
     * @return 답변 상태에 따른 Inquiry 조건식
     */
    private BooleanExpression categoryContains(List<String> categories) {
        return categories != null && !categories.isEmpty() ? inquiry.categoryName.in(categories) : null;
    }
}
