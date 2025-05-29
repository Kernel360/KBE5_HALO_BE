package com.kernel.common.manager.service;

import com.kernel.common.enums.ReplyStatus;
import com.kernel.common.manager.dto.mapper.ManagerInquiryMapper;
import com.kernel.common.manager.dto.reponse.ManagerInquiryRspDTO;
import com.kernel.common.manager.dto.reponse.ManagerInquirySummaryRspDTO;
import com.kernel.common.manager.dto.request.ManagerInquiryCreateReqDTO;
import com.kernel.common.manager.dto.request.ManagerInquiryUpdateReqDTO;
import com.kernel.common.manager.entity.ManagerInquiry;
import com.kernel.common.manager.entity.QManagerInquiry;
import com.kernel.common.manager.entity.QManagerReply;
import com.kernel.common.manager.repository.ManagerInquiryRepository;
import com.querydsl.core.Tuple;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ManagerInquiryServiceImpl implements ManagerInquiryService {
    
    private final ManagerInquiryMapper managerInquiryMapper;
    private final ManagerInquiryRepository managerInquiryRepository;

    /**
     * 매니저 상담 게시글 목록 조회 (검색 조건 및 페이징 처리)
     * @param fromCreatedAt 작성일시 시작일
     * @param toCreatedAt 작성일시 종료일
     * @param replyStatus 답변 상태
     * @param titleKeyword 제목 키워드
     * @param contentKeyword 내용 키워드
     * @param pageable 페이징
     * @return 조건에 맞는 게시글 정보를 담은 Page 객체
     */
    @Override
    public Page<ManagerInquirySummaryRspDTO> searchManagerinquiriesWithPaging(
        Long authorId,
        LocalDateTime fromCreatedAt, LocalDateTime toCreatedAt, ReplyStatus replyStatus, String titleKeyword, String contentKeyword,
        Pageable pageable
    ) {

        // 조건 및 페이징 포함된 게시글 목록 조회
        Page<Tuple> searchedInquiryPage = managerInquiryRepository.searchManagerinquiriesWithPaging(authorId, fromCreatedAt, toCreatedAt, replyStatus, titleKeyword, contentKeyword, pageable);

        // Tuple -> SummaryResponseDTO 변환
        QManagerInquiry qManagerInquiry = QManagerInquiry.managerInquiry;
        QManagerReply qManagerReply = QManagerReply.managerReply;

        List<ManagerInquirySummaryRspDTO> dtoList = searchedInquiryPage.getContent().stream()
        .map(tuple -> {
            // 매니저 상담 게시글
            ManagerInquiry inquiry = tuple.get(qManagerInquiry);
            if (inquiry == null) return null;

            // 답변 게시글 ID가 존재하면 답변여부 true
            boolean isReplied = tuple.get(qManagerReply.inquiryId) != null;

            return ManagerInquirySummaryRspDTO.builder()
                .inquiryId(inquiry.getInquiryId())
                .title(inquiry.getTitle())
                .createdAt(inquiry.getCreatedAt())
                .isReplied(isReplied)
                .build();
        })
        .collect(Collectors.toList());

        // SummaryResponseDTO -> Page 후, return
        return new PageImpl<>(dtoList, pageable, searchedInquiryPage.getTotalElements());
    }

    /**
     * 매니저 상담 게시글 상세조회
     * @param authorId 작성자ID(=매니저ID)
     * @param inquiryId 게시글ID
     * @return 게시글 상세 정보를 담은 응답
     */
    @Override
    public ManagerInquiryRspDTO getManagerInquiry(Long authorId, Long inquiryId) {

        // Entity 조회 (게시글ID, 작성자ID(=매니저ID)로 조회)
        ManagerInquiry foundInquiry = managerInquiryRepository.findByInquiryIdAndAuthorId(inquiryId, authorId);
        if (foundInquiry == null) {
            throw new NoSuchElementException("게시글이 존재하지 않거나 권한이 없습니다.");
        }

        // 매니저 상담 게시글 삭제 여부 확인
        if (foundInquiry.getIsDeleted()) {
            throw new IllegalStateException("삭제된 게시글입니다.");
        }

        // Entity -> ResponseDTO 후, return
        return managerInquiryMapper.toResponseDTO(foundInquiry);
    }

    /**
     * 매니저 상담 게시글 등록
     * @param authorId 작성자ID(=매니저ID)
     * @param requestDTO 게시글 등록 요청 데이터
     * @return 작성된 게시글 정보를 담은 응답
     */
    @Override
    @Transactional
    public ManagerInquirySummaryRspDTO createManagerInquiry(Long authorId, ManagerInquiryCreateReqDTO requestDTO) {

        // RequestDTO -> Entity
        ManagerInquiry managerInquiry = managerInquiryMapper.toEntity(authorId, requestDTO);

        // 저장
        ManagerInquiry createdInquiry = managerInquiryRepository.save(managerInquiry);

        // Entity -> SummaryResponseDTO 후, return
        return managerInquiryMapper.toSummaryResponseDTO(createdInquiry);
    }

    /**
     * 매니저 상담 게시글 수정
     * @param authorId 작성자ID(=매니저ID)
     * @param requestDTO 게시글 수정 요청 데이터
     * @return 수정된 게시글 정보를 담은 응답
     */
    @Override
    @Transactional
    public ManagerInquirySummaryRspDTO updateManagerInquiry(Long authorId, ManagerInquiryUpdateReqDTO requestDTO) {

            // Entity 조회 (게시글ID, 작성자ID(=매니저ID)로 조회)
            ManagerInquiry foundInquiry = managerInquiryRepository.findByInquiryIdAndAuthorId(requestDTO.getInquiryId(), authorId);
            if (foundInquiry == null) {
                throw new NoSuchElementException("게시글이 존재하지 않거나 권한이 없습니다.");
            }

            // 매니저 상담 게시글 삭제 여부 확인
            if (foundInquiry.getIsDeleted()) {
                throw new IllegalStateException("이미 삭제된 게시글은 수정할 수 없습니다.");
            }

            // 답변 여부 확인 - 답변있으면 수정 불가
            if (foundInquiry.getManagerReply() != null) {
                throw new IllegalStateException("답변이 달린 게시글은 수정할 수 없습니다.");
            }

            // 매니저 상담 게시글 수정
            foundInquiry.update(requestDTO.getTitle(), requestDTO.getContent());

            // Entity -> SummaryResponseDTO 후, return
            return managerInquiryMapper.toSummaryResponseDTO(foundInquiry);
    }

    /**
     * 매니저 상담 게시글 삭제
     * - isDeleted = true 처리
     * @param authorId 작성자ID(=매니저ID)
     * @param inquiryId 게시글ID
     */
    @Override
    @Transactional
    public void deleteManagerInquiry(Long authorId, Long inquiryId) {

        // Entity 조회 (게시글ID, 작성자ID(=매니저ID)로 조회)
        ManagerInquiry foundInquiry = managerInquiryRepository.findByInquiryIdAndAuthorId(inquiryId, authorId);
        if (foundInquiry == null) {
            throw new NoSuchElementException("게시글이 존재하지 않거나 권한이 없습니다.");
        }

        // 매니저 상담 게시글 삭제 여부 확인
        if (foundInquiry.getIsDeleted()) {
            throw new IllegalStateException("이미 삭제된 게시글은 삭제할 수 없습니다.");
        }

        // 답변 여부 확인 - 답변있으면 삭제 불가
        if (foundInquiry.getManagerReply() != null) {
            throw new IllegalStateException("답변이 달린 게시글은 삭제할 수 없습니다.");
        }

        // 매니저 상담 게시글 삭제
        foundInquiry.delete();
    }
}
