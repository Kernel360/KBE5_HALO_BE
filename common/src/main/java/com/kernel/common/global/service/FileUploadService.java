package com.kernel.common.global.service;

import com.kernel.common.global.dto.request.FileUpdateReqDTO;
import com.kernel.common.global.dto.request.FileUploadReqDTO;
import com.kernel.common.global.dto.request.PresignedUrlReqDTO;
import com.kernel.common.global.dto.response.FileUploadRspDTO;
import com.kernel.common.global.dto.response.PresignedUrlRspDTO;

import java.util.List;

public interface FileUploadService {

    FileUploadRspDTO getFileList(Long fileId);

    List<PresignedUrlRspDTO> generatePresignedUrls(PresignedUrlReqDTO request);

    FileUploadRspDTO uploadFiles(FileUploadReqDTO request);

    FileUploadRspDTO updateFiles(FileUpdateReqDTO request);
}
