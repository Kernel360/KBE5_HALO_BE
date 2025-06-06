package com.kernel.common.global.service;

import com.kernel.common.global.util.ListJsonConverter;
import com.kernel.common.global.util.S3Processor;
import com.kernel.common.global.dto.Mapper.FileUploadMapper;
import com.kernel.common.global.dto.request.FileDeleteReqDTO;
import com.kernel.common.global.dto.request.FileUploadReqDTO;
import com.kernel.common.global.dto.response.FileDeleteRspDTO;
import com.kernel.common.global.dto.response.FileUploadRspDTO;
import com.kernel.common.global.entity.UploadedFiles;
import com.kernel.common.repository.UploadedFileRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService{

    private final S3Processor s3Processor;
    private final UploadedFileRepository uploadedFileRepository;
    private final FileUploadMapper fileUploadMapper;
    private final ListJsonConverter listJsonConverter;

    /**
     * 첨부파일 업로드 메서드
     * 이 메서드는 파일 업로드 요청을 처리하고, S3에 파일을 업로드한 후, UploadedFiles 엔티티에 파일 경로를 저장합니다.
     * @param request 파일 업로드 요청 DTO
     * @return 업로드된 파일 정보가 담긴 응답 DTO
     */
    @Transactional
    @Override
    public FileUploadRspDTO uploadFiles(FileUploadReqDTO request) {
        UploadedFiles existingFiles;

        // UploadedFiles 엔티티가 존재하지 않으면 새로 생성
        if (request.getFileId() == null) {
            existingFiles = UploadedFiles.builder().filePathsJson("[]").build();
        } else {
            existingFiles = uploadedFileRepository.findById(request.getFileId())
                    .orElseThrow(() -> new NoSuchElementException("File을 찾을 수 없습니다."));
        }

        List<String> fileUrls = listJsonConverter.parseJsonToList((existingFiles.getFilePathsJson()));

        for (MultipartFile file : request.getFiles()) {
            // S3에 파일 업로드
            String s3Url = s3Processor.upload(file, "uploads");

            // fileUrls에 업로드된 파일 URL 추가
            if (!fileUrls.contains(s3Url)) {
                fileUrls.add(s3Url);
            }
        }

        existingFiles.updateFiles(listJsonConverter.convertListToJson(fileUrls));

        return fileUploadMapper.toFileUploadRspDTO(uploadedFileRepository.save(existingFiles)); // uploadedFiles에 save를 사용한 이유는 새로운 엔터티를 생성한 경우가 있기 때문
    }

    /**
     * 첨부파일 삭제 메서드
     * 이 메서드는 파일 삭제 요청을 처리하고, S3에서 파일을 삭제한 후, UploadedFiles 엔티티에서 해당 파일 경로를 제거합니다.
     * @param request 파일 삭제 요청 DTO
     * @return 삭제된 파일 정보가 담긴 응답 DTO
     */
    @Transactional
    @Override
    public FileDeleteRspDTO deleteFiles(FileDeleteReqDTO request) {
        UploadedFiles existingFiles = uploadedFileRepository.findById(request.getFileId())
                .orElseThrow(() -> new RuntimeException("Uploaded files not found"));

        List<String> existingPaths = listJsonConverter.parseJsonToList(existingFiles.getFilePathsJson());
        for (String s3Url : request.getS3Urls()) {
            // S3에서 파일 삭제
            s3Processor.delete(s3Url);

            // 파일 정보에서 삭제한 파일 정보 삭제
            existingPaths.removeIf(path -> path.equals(s3Url));
        }

        existingFiles.updateFiles(listJsonConverter.convertListToJson(existingPaths));

        return fileUploadMapper.toFileDeleteRspDTO(existingFiles);
    }
}
