package com.kernel.inquiry.service.inquiry;

import com.kernel.global.common.enums.UserRole;
import com.kernel.global.service.dto.response.EnumValueDTO;
import com.kernel.inquiry.common.enums.AuthorType;
import com.kernel.inquiry.common.enums.CustomerInquiryCategory;
import com.kernel.inquiry.common.enums.InquiryErrorCode;
import com.kernel.inquiry.common.enums.ManagerInquiryCategory;
import com.kernel.inquiry.common.exception.InquiryNotFoundException;
import com.kernel.inquiry.common.exception.InvalidInquiryCategoryException;
import com.kernel.inquiry.domain.entity.Inquiry;
import com.kernel.inquiry.domain.entity.Reply;
import com.kernel.inquiry.repository.InquiryRepository;
import com.kernel.inquiry.repository.ReplyRepository;
import com.kernel.inquiry.service.dto.request.InquiryCreateReqDTO;
import com.kernel.inquiry.service.dto.request.InquirySearchReqDTO;
import com.kernel.inquiry.service.dto.request.InquiryUpdateReqDTO;
import com.kernel.inquiry.service.dto.response.InquiryDetailRspDTO;
import com.kernel.inquiry.service.dto.response.InquirySummaryRspDTO;
import com.kernel.inquiry.service.info.InquirySummaryInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InquiryServiceImpl implements InquiryService {

    private final InquiryRepository inquiryRepository;
    private final ReplyRepository replyRepository;

    /**
     * 문의사항 검색
     * @param searchReq  검색 조건을 포함하는 요청 DTO
     * @param userId 작성자 ID
     * @param pageable 페이징 정보
     * @return Page<InquirySummaryRspDTO>
     */
    @Transactional(readOnly = true)
    @Override
    public Page<InquirySummaryRspDTO> searchInquiries(InquirySearchReqDTO searchReq, Long userId, Pageable pageable) {

        Page<InquirySummaryInfo> inquirySummaryInfo = inquiryRepository.searchInquiriesWithPagination(searchReq, userId, pageable);

        return InquirySummaryRspDTO.fromInfo(inquirySummaryInfo);
    }

    /**
     * 문의사항 상세 조회
     * @param inquiryId 문의사항 ID
     * @return InquiryDetailRspDTO
     */
    @Transactional(readOnly = true)
    @Override
    public InquiryDetailRspDTO getInquiryDetails(Long inquiryId, Long userId) {

        Inquiry foundInquiry = inquiryRepository.findByInquiryIdAndAuthorId(inquiryId, userId)
                .orElseThrow(() -> new InquiryNotFoundException(InquiryErrorCode.INQUIRY_NOT_FOUND));

        Reply foundReply = replyRepository.findByInquiryId(foundInquiry).orElse(null);

        return InquiryDetailRspDTO.fromEntity(foundInquiry, foundReply);
    }

    /**
     * 문의사항 생성
     * @param createReqDTO  요청 DTO
     * @param userId  작성자 ID
     * @param userRole 작성자 타입
     */
    @Transactional
    @Override
    public Long createInquiry(InquiryCreateReqDTO createReqDTO, Long userId, UserRole userRole) {

        // 1. 작성자 타입 변환
        AuthorType authorType = AuthorType.fromUserRole(userRole);

        // 2. 문의 카테고리 검사
        validateCategory(createReqDTO.getCategory(), authorType);

        // 3. 문의 저장
        Inquiry saveInquiry = inquiryRepository.save(Inquiry.builder()
                            .categoryName(createReqDTO.getCategory())
                            .authorId(userId)
                            .authorType(authorType)
                            .title(createReqDTO.getTitle())
                            .content(createReqDTO.getContent())
                            .fileId(createReqDTO.getFileId())
                            .build()
                    );

        return saveInquiry.getInquiryId();
    }

    /**
     * 문의사항 수정
     * @param updateReqDTO 요청 DTO
     * @param userId       로그인 유저 ID
     */
    @Transactional
    @Override
    public void updateInquiry(Long inquiryId, InquiryUpdateReqDTO updateReqDTO, Long userId, UserRole userRole)
    {
        // 1. 문의사항 조회
        Inquiry foundInquiry = inquiryRepository.findByInquiryIdAndAuthorId(inquiryId, userId)
                .orElseThrow(() -> new InquiryNotFoundException(InquiryErrorCode.INQUIRY_NOT_FOUND));

        // 2. 작성자 타입 변환
        AuthorType authorType = AuthorType.fromUserRole(userRole);

        // 3. 문의 카테고리 검사
        validateCategory(updateReqDTO.getCategory(), authorType);

        // 4. 수정
        foundInquiry.update(
                updateReqDTO.getTitle(),
                updateReqDTO.getContent(),
                updateReqDTO.getFileId() != null? updateReqDTO.getFileId(): null,
                updateReqDTO.getCategory()
        );
    }

    /**
     * 문의사항 삭제
     * @param inquiryId 삭제할 문의사항 ID
     * @param userId   로그인 한 유져
     */
    @Transactional
    @Override
    public void deleteInquiry(Long inquiryId, Long userId) {

        // 1. 문의사항 조회
        Inquiry foundInquiry = inquiryRepository.findByInquiryIdAndAuthorId(inquiryId, userId)
                .orElseThrow(() -> new InquiryNotFoundException(InquiryErrorCode.INQUIRY_NOT_FOUND));

        foundInquiry.delete();
    }

    /**
     * 수요자 문의사항 카테고리 조회
     * @return 문의사항 카테고리 목록
     */
    @Override
    public List<EnumValueDTO> getCustomerInquiryCategory() {

        return Arrays.stream(CustomerInquiryCategory.values())
                .map(e -> new EnumValueDTO(e.name(), e.getLabel()))
                .toList();
    }

    /**
     * 관리자 문의사항 카테고리 조회
     * @return 문의사항 카테고리 목록
     */
    @Override
    public List<EnumValueDTO> getManagerInquiryCategory() {

        return Arrays.stream(ManagerInquiryCategory.values())
                .map(e -> new EnumValueDTO(e.name(), e.getLabel()))
                .toList();
    }

    /**
     * 카테고리 검사
     * @param categoryName 카테고리 name
     * @param authorType  작성자 타입
     */
    private void validateCategory(String categoryName, AuthorType authorType) {
        
        try {
            switch (authorType) {
                case CUSTOMER -> CustomerInquiryCategory.valueOf(categoryName);
                case MANAGER -> ManagerInquiryCategory.valueOf(categoryName);
                default -> throw new InvalidInquiryCategoryException(InquiryErrorCode.UNSUPPORTED_USER_TYPE);
            }
        } catch (IllegalArgumentException e) {
            throw new InvalidInquiryCategoryException(InquiryErrorCode.INVALID_INQUIRY_CATEGORY);
        }
    }
}
