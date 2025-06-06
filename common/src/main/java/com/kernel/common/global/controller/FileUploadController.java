package com.kernel.common.global.controller;

import com.kernel.common.global.dto.request.FileDeleteReqDTO;
import com.kernel.common.global.dto.request.FileUploadReqDTO;
import com.kernel.common.global.dto.response.FileDeleteRspDTO;
import com.kernel.common.global.dto.response.FileUploadRspDTO;
import com.kernel.common.global.entity.ApiResponse;
import com.kernel.common.global.service.FileUploadService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileUploadController {

    private final FileUploadService fileUploadService;

    @PostMapping(value = "/upload")
    public ResponseEntity<ApiResponse<FileUploadRspDTO>> uploadFiles(
            @ModelAttribute @Valid FileUploadReqDTO request
    ) {

        FileUploadRspDTO response = fileUploadService.uploadFiles(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "파일 업로드 성공", response));
    }

    @PostMapping("/remove")
    public ResponseEntity<ApiResponse<FileDeleteRspDTO>> deleteFiles(
            @RequestBody @Valid FileDeleteReqDTO request
    ) {
        FileDeleteRspDTO response = fileUploadService.deleteFiles(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "파일 삭제 성공", response));
    }

}
