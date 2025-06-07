package com.kernel.common.admin.service;

import com.kernel.common.admin.dto.mapper.AdminInquiryMapper;
import com.kernel.common.admin.dto.mapper.AdminReplyMapper;
import com.kernel.common.admin.dto.request.AdminInquiryReplyReqDTO;
import com.kernel.common.admin.dto.request.AdminInquirySearchReqDTO;
import com.kernel.common.admin.dto.response.AdminInquiryDetailRspDTO;
import com.kernel.common.admin.dto.response.AdminInquirySummaryCustomerRspDTO;
import com.kernel.common.customer.entity.CustomerInquiry;
import com.kernel.common.customer.entity.CustomerReply;
import com.kernel.common.customer.repository.CustomerInquiryRepository;
import com.kernel.common.customer.repository.CustomerReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminCustomerInquiryServiceImpl implements AdminCustomerInquiryService {

    private final CustomerInquiryRepository customerInquiryRepository;
    private final CustomerReplyRepository customerReplyRepository;
    private final AdminInquiryMapper adminInquiryMapper;
    private final AdminReplyMapper adminReplyMapper;

    /**
     * Customer 문의사항 목록 조회 및 검색
     * @param request
     * @param pageable
     * @return Page of AdminInquirySummaryCustomerRspDTO
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AdminInquirySummaryCustomerRspDTO> getCustomerInquiryPage(AdminInquirySearchReqDTO request, Pageable pageable) {

        Page<CustomerInquiry> inquiryPage = customerInquiryRepository.searchCustomerInquiryByKeyword(request, pageable);

        return inquiryPage.map(
                inquiry -> adminInquiryMapper.toSummaryRspDTO(inquiry)
        );
    }

    /**
     * Customer 문의사항 상세 조회
     * @param inquiryId
     * @return AdminInquiryDetailRspDTO
     */
    @Override
    @Transactional(readOnly = true)
    public AdminInquiryDetailRspDTO getCustomerInquiryDetail(Long inquiryId) {
        CustomerInquiry inquiry = customerInquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new IllegalArgumentException("문의사항을 찾을 수 없습니다."));

        CustomerReply reply = inquiry.getCustomerReply() != null
                ? customerReplyRepository.findById(inquiry.getCustomerReply().getAnswerId()).orElse(null)
                : null; // 답변이 없을 수도 있으므로 null 처리

        return adminInquiryMapper.toDetailRspDTO(inquiry, reply);
    }

    /**
     * Customer 문의사항 삭제
     * @param inquiryId
     */
    @Override
    @Transactional
    public void DeleteCustomerInquiry(Long inquiryId) {
        CustomerInquiry inquiry = customerInquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new IllegalArgumentException("문의사항을 찾을 수 없습니다."));

        inquiry.delete();
    }

    /**
     * Customer 문의사항 답변 생성
     * @param reply
     * @param authorId
     */
    @Override
    @Transactional
    public void CreateReplyCustomerInquiry(AdminInquiryReplyReqDTO reply, Long authorId) {
        CustomerInquiry inquiry = customerInquiryRepository.findById(reply.getInquiryId())
                .orElseThrow(() -> new IllegalArgumentException("문의사항을 찾을 수 없습니다."));

        CustomerReply newReply = adminReplyMapper.toCustomerReply(reply, authorId, inquiry);
        customerReplyRepository.save(newReply);
    }

    /**
     * Customer 문의사항 답변 수정
     * @param reply
     * @param authorId
     * @param replyId
     */
    @Override
    @Transactional
    public void UpdateReplyCustomerInquiry(AdminInquiryReplyReqDTO reply, Long authorId, Long replyId) {
        CustomerReply existingReply = customerReplyRepository.findById(replyId)
                .orElseThrow(() -> new IllegalArgumentException("답변을 찾을 수 없습니다."));

        if (!existingReply.getAuthorId().equals(authorId)) {
            throw new IllegalArgumentException("답변 작성자가 아닙니다.");
        }

        existingReply.update(reply.getContent(), reply.getFileId());
    }

}
