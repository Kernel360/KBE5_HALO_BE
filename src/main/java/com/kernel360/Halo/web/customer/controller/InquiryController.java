package com.kernel360.Halo.web.customer.controller;

import com.kernel360.Halo.domain.customer.service.InquiryService;
import com.kernel360.Halo.web.customer.dto.response.InquiryResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class InquiryController {

    private final InquiryService inquiryService;

    @GetMapping("/inquiries")
    public ResponseEntity<List<InquiryResponseDTO>> getInquires(
            @RequestParam String keyword
    ) {
        List<InquiryResponseDTO> responseList = inquiryService.getInquires(keyword);
        // keyword == all이면 전체 조회
        // keyword != all이면 검색
        return ResponseEntity.status(HttpStatus.OK).body(responseList);
    }
}

