package com.kernel.common.admin.service;

import com.kernel.common.admin.dto.mapper.AdminInquiryMapper;
import com.kernel.common.admin.dto.mapper.AdminReplyMapper;
import com.kernel.common.admin.dto.request.AdminInquiryReplyReqDTO;
import com.kernel.common.admin.dto.request.AdminInquirySearchReqDTO;
import com.kernel.common.admin.dto.response.AdminInquiryDetailRspDTO;
import com.kernel.common.admin.dto.response.AdminInquirySummaryManagerRspDTO;
import com.kernel.common.manager.entity.*;
import com.kernel.common.manager.repository.ManagerInquiryRepository;
import com.kernel.common.manager.repository.ManagerReplyRepository;
import com.kernel.common.manager.repository.ManagerRepository;

import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AdminManagerInquiryServiceImpl implements AdminManagerInquiryService {

    private final ManagerInquiryRepository managerInquiryRepository;
    private final ManagerReplyRepository managerReplyRepository;
    private final ManagerRepository managerRepository;
    private final AdminInquiryMapper adminInquiryMapper;
    private final AdminReplyMapper adminReplyMapper;
    private final QManagerInquiry managerInquiry = QManagerInquiry.managerInquiry;
    private final QManagerReply managerReply = QManagerReply.managerReply;


    /*     * 관리자 문의사항 목록 조회 및 검색
     * @param request
     * @param pageable
     * @return Page of AdminInquirySummaryRspDTO
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AdminInquirySummaryManagerRspDTO> getManagerInquiryPage(AdminInquirySearchReqDTO request, Pageable pageable) {
        boolean isEmptySearchConditions = (request.getAuthorName() == null || request.getAuthorName().isEmpty())
                && (request.getTitle() == null || request.getTitle().isEmpty());

        // 검색 조건이 비어있으면 모든 문의사항을 조회
        if (isEmptySearchConditions) {
            Page<ManagerInquiry> inquiryPage = managerInquiryRepository.findAll(pageable);
            return inquiryPage.map(inquiry -> adminInquiryMapper.toSummaryManagerRspDTO(inquiry));
        }

        // 작성자 이름으로 매니저를 조회
        List<Manager> authors = managerRepository.findByUserNameContaining(request.getAuthorName().trim());

        if (authors.isEmpty()) {
            throw new NoSuchElementException("해당 이름의 매니저를 찾을 수 없습니다.");
        }

        // request의 작성자 이름인 모든 id를 추출
        List<Long> authorIds = authors.stream()
                .map(Manager::getManagerId)
                .toList();

        // 모든 작성자 ID에 대해 페이지네이션된 문의사항을 조회
        List<Tuple> allInquiries = new ArrayList<>();
        for (Long authorId : authorIds) {
            Page<Tuple> inquiryPage = managerInquiryRepository.searchManagerinquiriesWithPaging(
                    authorId,
                    null,
                    null,
                    null,
                    request.getTitle(),
                    null,
                    pageable
            );
            allInquiries.addAll(inquiryPage.getContent());
        }

        return new PageImpl<>(
            allInquiries.stream()
                    .map(tuple -> {
                        ManagerInquiry inquiry = tuple.get(managerInquiry);
                        Long replyId = tuple.get(managerReply.answerId);

                        return AdminInquirySummaryManagerRspDTO.builder()
                                .inquiryId(inquiry.getInquiryId())
                                .authorId(inquiry.getAuthorId())
                                .title(inquiry.getTitle())
                                .createdAt(inquiry.getCreatedAt())
                                .build();
                    })
                    .toList(),
            pageable,
            allInquiries.size()
        );
    }

    /**
     * 관리자 문의사항 상세 조회
     * @param inquiryId
     * @return AdminInquiryDetailRspDTO
     */
    @Override
    @Transactional(readOnly = true)
    public AdminInquiryDetailRspDTO getManagerInquiryDetail(Long inquiryId) {
        ManagerInquiry inquiry = managerInquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new IllegalArgumentException("문의사항을 찾을 수 없습니다."));

        ManagerReply reply = inquiry.getManagerReply() != null
                ? managerReplyRepository.findById(inquiry.getManagerReply().getAnswerId()).orElse(null)
                : null; // 답변이 없을 수도 있으므로 null 처리

        return adminInquiryMapper.toDetailRspDTO(inquiry, reply);
    }

    /**
     * 관리자 문의사항 삭제
     * @param inquiryId
     */
    @Override
    @Transactional
    public void DeleteManagerInquiry(Long inquiryId) {
        ManagerInquiry inquiry = managerInquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new IllegalArgumentException("문의사항을 찾을 수 없습니다."));

        inquiry.delete();
    }

    /**
     * 관리자 문의사항 답변 생성
     * @param reply
     * @param authorId
     */
    @Override
    @Transactional
    public void CreateReplyManagerInquiry(AdminInquiryReplyReqDTO reply, Long authorId) {
        ManagerInquiry inquiry = managerInquiryRepository.findById(reply.getInquiryId())
                .orElseThrow(() -> new IllegalArgumentException("문의사항을 찾을 수 없습니다."));

        ManagerReply newReply = adminReplyMapper.toManagerReply(reply, authorId, inquiry);
        managerReplyRepository.save(newReply);
    }

    /**
     * 관리자 문의사항 답변 수정
     * @param reply
     * @param authorId
     * @param replyId
     */
    @Override
    @Transactional
    public void UpdateReplyManagerInquiry(AdminInquiryReplyReqDTO reply, Long authorId, Long replyId) {
        ManagerReply existingReply = managerReplyRepository.findById(replyId)
                .orElseThrow(() -> new IllegalArgumentException("답변을 찾을 수 없습니다."));

        if (!existingReply.getAuthorId().equals(authorId)) {
            throw new IllegalArgumentException("답변 작성자가 아닙니다.");
        }

        existingReply.update(reply.getContent(), reply.getFileId());
    }
}
