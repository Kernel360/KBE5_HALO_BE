package com.kernel.inquiry.service.inquiry;

import com.kernel.global.common.enums.UserRole;
import com.kernel.global.domain.entity.User;
import com.kernel.global.repository.UserRepository;
import com.kernel.inquiry.common.enums.CustomerInquiryCategory;
import com.kernel.inquiry.common.enums.ManagerInquiryCategory;
import com.kernel.inquiry.domain.entity.Inquiry;
import com.kernel.inquiry.domain.info.InquiryDetailInfo;
import com.kernel.inquiry.domain.info.InquirySummaryInfo;
import com.kernel.inquiry.domain.info.ReplyInfo;
import com.kernel.inquiry.repository.InquiryRepository;
import com.kernel.inquiry.repository.ReplyRepository;
import com.kernel.inquiry.service.dto.request.InquiryCreateReqDTO;

import com.kernel.inquiry.service.dto.request.InquirySearchReqDTO;
import com.kernel.inquiry.service.dto.request.InquiryUpdateReqDTO;
import com.kernel.inquiry.service.dto.response.InquiryDetailRspDTO;
import com.kernel.inquiry.service.dto.response.InquirySummaryRspDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class InquiryServiceImpl implements InquiryService {

    private final InquiryRepository inquiryRepository;
    private final ReplyRepository replyRepository;
    private final UserRepository userRepository;

    /**
     * 문의사항 검색
     *
     * @param request  검색 조건을 포함하는 요청 DTO
     * @param authorId 작성자 ID
     * @param pageable 페이징 정보
     * @return Page<InquirySummaryRspDTO>
     */
    @Transactional(readOnly = true)
    @Override
    public Page<InquirySummaryRspDTO> searchInquiries(InquirySearchReqDTO request, Long authorId, Pageable pageable) {
        Boolean isAdmin = userRepository.findById(authorId)
                .map(User::getRole)
                .map(role -> role == UserRole.ADMIN)
                .orElse(false);

        Page<InquirySummaryInfo> inquirySummaryInfo = inquiryRepository.searchInquiriesWithPagination(request, authorId, isAdmin, pageable);

        return InquirySummaryRspDTO.fromInfo(inquirySummaryInfo);
    }

    /**
     * 문의사항 상세 조회
     *
     * @param inquiryId 문의사항 ID
     * @return InquiryDetailRspDTO
     */
    @Transactional(readOnly = true)
    @Override
    public InquiryDetailRspDTO getInquiryDetails(Long inquiryId) {
        InquiryDetailInfo inquiryDetailInfo = inquiryRepository.findInfoByInquiryId(inquiryId)
                .orElseThrow(() -> new NoSuchElementException("문의사항을 찾을 수 없습니다."));

        ReplyInfo replyInfo = replyRepository.findInfoByInquiryId_InquiryId(inquiryId)
                .orElseThrow(() -> new NoSuchElementException("문의사항을 찾을 수 없습니다."));

        return InquiryDetailRspDTO.fromInfo(inquiryDetailInfo, replyInfo);
    }

    /**
     * 문의사항 생성
     *
     * @param request   요청 DTO
     * @param authorId  작성자 ID
     * @param authorRole 작성자 타입 (일반 사용자, 관리자 등)
     */
    @Transactional
    @Override
    public void createInquiry(InquiryCreateReqDTO request, Long authorId, UserRole authorRole) {

        // 문의 카테고리 검증
        if (authorRole == UserRole.CUSTOMER && !(request.getCategory() instanceof CustomerInquiryCategory)) {
            throw new IllegalArgumentException("잘못된 고객 문의 카테고리입니다.");
        } else if (authorRole == UserRole.MANAGER && !(request.getCategory() instanceof ManagerInquiryCategory)) {
            throw new IllegalArgumentException("잘못된 매니저 문의 카테고리입니다.");
        }

        Inquiry inquiry = request.toEntity(request, authorId, authorRole);
        inquiryRepository.save(inquiry);
    }

    /**
     * 문의사항 수정
     *
     * @param request   요청 DTO
     * @param authorId  작성자 ID
     */
    @Transactional
    @Override
    public void updateInquiry(InquiryUpdateReqDTO request, Long authorId) {
        Inquiry inquiry = inquiryRepository.findById(request.getInquiryId())
                .orElseThrow(() -> new NoSuchElementException("문의사항을 찾을 수 없습니다."));

        // 작성자 ID가 일치하는지 확인
        if (!inquiry.getAuthorId().equals(authorId)) {
            throw new IllegalArgumentException("작성자 ID가 일치하지 않습니다.");
        }

        // 문의 카테고리 검증
        if (authorId != null && inquiry.getAuthorRole() == UserRole.CUSTOMER &&
                !(request.getCategory() instanceof CustomerInquiryCategory)) {
            throw new IllegalArgumentException("잘못된 고객 문의 카테고리입니다.");
        } else if (authorId != null && inquiry.getAuthorRole() == UserRole.MANAGER &&
                !(request.getCategory() instanceof ManagerInquiryCategory)) {
            throw new IllegalArgumentException("잘못된 매니저 문의 카테고리입니다.");
        }
        inquiry.update(request.toEntity(request));
    }

    /**
     * 문의사항 삭제
     *
     * @param inquiryId 삭제할 문의사항 ID
     * @param authorId  작성자 ID
     */
    @Transactional
    @Override
    public void deleteInquiry(Long inquiryId, Long authorId) {
        Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new NoSuchElementException("문의사항을 찾을 수 없습니다."));

        // 작성자 ID 일치
        // TODO: 관리자도 삭제가 가능하도록 권한 확인 로직 추가 필요
        if (!inquiry.getAuthorId().equals(authorId)) {
            throw new IllegalArgumentException("작성자 ID가 일치하지 않습니다.");
        }

        inquiry.delete();
    }
}
