package com.kernel.inquiry.service.inquiry;

import com.kernel.inquiry.common.enums.AuthorType;
import com.kernel.inquiry.domain.entity.Inquiry;
import com.kernel.inquiry.repository.InquiryRepository;
import com.kernel.inquiry.service.dto.request.InquiryCreateReqDTO;

import com.kernel.inquiry.service.dto.request.InquiryUpdateReqDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class InquiryCommandServiceImpl implements InquiryCommandService {

    private final InquiryRepository inquiryRepository;

    /**
     * 문의사항 생성
     *
     * @param request   요청 DTO
     * @param authorId  작성자 ID
     * @param authorType 작성자 타입 (일반 사용자, 관리자 등)
     */
    @Transactional
    @Override
    public void createInquiry(InquiryCreateReqDTO request, Long authorId, AuthorType authorType) {

        // 문의 카테고리 검증
        if (authorType == AuthorType.CUSTOMER && !(request.getCategory() instanceof com.kernel.inquiry.common.enums.CustomerInquiryCategory)) {
            throw new IllegalArgumentException("잘못된 고객 문의 카테고리입니다.");
        } else if (authorType == AuthorType.MANAGER && !(request.getCategory() instanceof com.kernel.inquiry.common.enums.ManagerInquiryCategory)) {
            throw new IllegalArgumentException("잘못된 관리자 문의 카테고리입니다.");
        }

        Inquiry inquiry = request.toEntity(authorId, authorType);
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
        if (authorId != null && inquiry.getAuthorType() == AuthorType.CUSTOMER &&
                !(request.getCategory() instanceof com.kernel.inquiry.common.enums.CustomerInquiryCategory)) {
            throw new IllegalArgumentException("잘못된 고객 문의 카테고리입니다.");
        } else if (authorId != null && inquiry.getAuthorType() == AuthorType.MANAGER &&
                !(request.getCategory() instanceof com.kernel.inquiry.common.enums.ManagerInquiryCategory)) {
            throw new IllegalArgumentException("잘못된 관리자 문의 카테고리입니다.");
        }
        inquiry.update(request.toEntity());
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
