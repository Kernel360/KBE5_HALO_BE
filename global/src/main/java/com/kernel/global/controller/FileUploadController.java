package com.kernel.global.controller;

import com.kernel.global.service.FileUploadService;
import com.kernel.global.service.dto.request.FileUpdateReqDTO;
import com.kernel.global.service.dto.request.FileUploadReqDTO;
import com.kernel.global.service.dto.request.PresignedUrlReqDTO;
import com.kernel.global.service.dto.response.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileUploadController {

    private final FileUploadService fileUploadService;

    @GetMapping("/{file-id}")
    public ResponseEntity<ApiResponse<FileGetRspDTO>> getFileList(
            @PathVariable("file-id") Long fileId
    ) {

        FileGetRspDTO response = fileUploadService.getFileList(fileId);
        return ResponseEntity.ok(new ApiResponse<>(true, "파일 목록 조회 성공", response));
    }

    @PostMapping("/presigned-urls")
    public ResponseEntity<ApiResponse<List<PresignedUrlRspDTO>>> generatePresignedUrls(
            @RequestBody @Valid PresignedUrlReqDTO request
    ) {
        List<PresignedUrlRspDTO> response = fileUploadService.generatePresignedUrls(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "프리사인드 URL 생성 성공", response));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<FileUploadRspDTO>> uploadFiles(
            @RequestBody @Valid FileUploadReqDTO request
    ) {
        FileUploadRspDTO response = fileUploadService.uploadFiles(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "파일 업로드 성공", response));
    }

    @PatchMapping
    public ResponseEntity<ApiResponse<FileUpdateRspDTO>> updateFiles(
            @RequestBody @Valid FileUpdateReqDTO request
    ) {
        FileUpdateRspDTO response = fileUploadService.updateFiles(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "파일 업데이트 성공", response));
    }
}
