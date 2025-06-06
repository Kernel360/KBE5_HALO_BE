package com.kernel.common.global.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.apache.tomcat.jni.FileInfo;

import java.util.List;

@Getter
@Builder
public class FileUploadRspDTO {

    // UploadedFiles 객체 ID
    private Long fileId;

    // 업로드된 파일 경로의 Json 형식 리스트
    private String files;
}
