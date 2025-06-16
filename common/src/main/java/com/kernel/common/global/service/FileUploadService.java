package com.kernel.common.global.service;

import com.kernel.common.global.dto.request.FileDeleteReqDTO;
import com.kernel.common.global.dto.request.FileUploadReqDTO;
import com.kernel.common.global.dto.request.PresignedUrlReqDTO;
import com.kernel.common.global.dto.response.FileDeleteRspDTO;
import com.kernel.common.global.dto.response.FileUploadRspDTO;

import com.kernel.common.global.dto.response.PresignedUrlRspDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileUploadService {

    List<PresignedUrlRspDTO> generatePresignedUrls(PresignedUrlReqDTO request);

    FileUploadRspDTO uploadFiles(FileUploadReqDTO request);
}
