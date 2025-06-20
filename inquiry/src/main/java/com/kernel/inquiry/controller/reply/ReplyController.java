package com.kernel.inquiry.controller.reply;

import com.kernel.global.domain.entity.ApiResponse;
import com.kernel.inquiry.service.dto.request.ReplyCreateReqDTO;
import com.kernel.inquiry.service.reply.ReplyService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reply")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createReply(
            //@AuthenticationPrincipal AuthenticatedUser authorId,
            @RequestBody @Valid ReplyCreateReqDTO request
    ) {
        // 현재는 authorId를 1L로 하드코딩되어 있으나, 실제 서비스에서는 사용자의 ID를 받아와야 합니다.
        // replyService.createReply(request, authorId);
        replyService.createReply(request, 1L);

        return ResponseEntity.ok(new ApiResponse<>(true, "답변 등록 완료", null));
    }

}
