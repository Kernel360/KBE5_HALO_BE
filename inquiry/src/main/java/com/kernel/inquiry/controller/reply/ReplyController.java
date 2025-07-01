package com.kernel.inquiry.controller.reply;

import com.kernel.global.security.CustomUserDetails;
import com.kernel.global.service.dto.response.ApiResponse;
import com.kernel.inquiry.service.dto.request.ReplyCreateReqDTO;
import com.kernel.inquiry.service.reply.ReplyService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "ReplyController", description = "답변 등록 API")
@RestController
@RequestMapping("/api/admin/reply")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createReply(
            @AuthenticationPrincipal CustomUserDetails author,
            @RequestBody @Valid ReplyCreateReqDTO request
    ) {
        replyService.createReply(request, author.getUserId());

        return ResponseEntity.ok(new ApiResponse<>(true, "답변 등록 완료", null));
    }

}
