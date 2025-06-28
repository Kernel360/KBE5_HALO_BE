package com.kernel.global.service;

import com.kernel.global.service.dto.request.FileUpdateReqDTO;
import com.kernel.global.service.dto.request.FileUploadReqDTO;
import com.kernel.global.service.dto.request.PresignedUrlReqDTO;
import com.kernel.global.service.dto.response.FileGetRspDTO;
import com.kernel.global.service.dto.response.FileUpdateRspDTO;
import com.kernel.global.service.dto.response.FileUploadRspDTO;
import com.kernel.global.service.dto.response.PresignedUrlRspDTO;

import java.util.List;

public interface FileUploadService {

    FileGetRspDTO getFileList(Long fileId);

    List<PresignedUrlRspDTO> generatePresignedUrls(PresignedUrlReqDTO request);

    FileUploadRspDTO uploadFiles(FileUploadReqDTO request);

    FileUpdateRspDTO updateFiles(FileUpdateReqDTO request);
}
