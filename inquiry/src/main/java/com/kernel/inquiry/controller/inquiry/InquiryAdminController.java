package com.kernel.inquiry.controller.inquiry;

import com.kernel.global.service.dto.response.ApiResponse;
import com.kernel.global.service.dto.response.EnumValueDTO;
import com.kernel.inquiry.common.enums.CustomerInquiryCategory;
import com.kernel.inquiry.common.enums.ManagerInquiryCategory;
import com.kernel.inquiry.service.dto.request.InquiryAdminSearchReqDTO;
import com.kernel.inquiry.service.dto.response.InquiryAdminDetailRspDTO;
import com.kernel.inquiry.service.dto.response.InquirySummaryRspDTO;
import com.kernel.inquiry.service.inquiry.InquiryAdminIService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "관리자 문의사항 API", description = "관리자 문의사항 조회, 상세조회, 카테고리, 작성자 타입")
@RestController
@RequestMapping("/api/admin/inquiries")
@RequiredArgsConstructor
public class InquiryAdminController {

    private final InquiryAdminIService inquiryAdminIService;


    /**
     * 문의사항 카테고리 조회
     * @return 문의사항 카테고리 목록
     */
    @GetMapping("/categories")
    public ResponseEntity<ApiResponse<Map<String, List<EnumValueDTO>>>> getAllInquiryCategoriesForAdmin() {
        Map<String, List<EnumValueDTO>> result = new HashMap<>();

        result.put("CUSTOMER", Arrays.stream(CustomerInquiryCategory.values())
                .map(e -> new EnumValueDTO(e.name(), e.getLabel()))
                .toList());

        result.put("MANAGER", Arrays.stream(ManagerInquiryCategory.values())
                .map(e -> new EnumValueDTO(e.name(), e.getLabel()))
                .toList());

        return ResponseEntity.ok(new ApiResponse<>(true, "문의 카테고리 전체 조회 성공", result));
    }

    /**
     * 문의사항 작성자 타입 조회
     * @return 작성자 타입 목록
     */
    @GetMapping("/authorTypes")
    public ResponseEntity<ApiResponse<List<EnumValueDTO>>> getAllInquiryAuthorTypes() {

        List<EnumValueDTO> result = Arrays.stream(CustomerInquiryCategory.values())
                .map(e -> new EnumValueDTO(e.name(), e.getLabel()))
                .toList();

        return ResponseEntity.ok(new ApiResponse<>(true, "수요자 문의사항 카테고리 조회 성공", result));
    }

    /**
     * 문의사항 조회
     * @param searchReqDTO 검색 조건
     * @param pageable     페이징 정보
     * @return 검색된 문의사항 목록
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Page<InquirySummaryRspDTO>>> searchInquiries(
            @RequestBody @Valid InquiryAdminSearchReqDTO searchReqDTO,
            Pageable pageable
    ) {
        Page<InquirySummaryRspDTO> inquiries = inquiryAdminIService.searchInquiries(searchReqDTO, pageable);
        return ResponseEntity.ok(new ApiResponse<>(true, "문의사항 목록 조회 성공", inquiries));
    }

    /**
     * 수요자 문의사항 상세 조회
     * @param inquiryId 문의사항 ID
     * @return 상세 문의사항 정보
     */
    @GetMapping("{inquiry-id}")
    public ResponseEntity<ApiResponse<InquiryAdminDetailRspDTO>> getInquiryDetails(
            @PathVariable("inquiry-id") Long inquiryId
    ) {
        InquiryAdminDetailRspDTO inquiryDetails = inquiryAdminIService.getInquiryDetails(inquiryId);
        return ResponseEntity.ok(new ApiResponse<>(true, "문의사항 상세 조회 성공", inquiryDetails));
    }
}
