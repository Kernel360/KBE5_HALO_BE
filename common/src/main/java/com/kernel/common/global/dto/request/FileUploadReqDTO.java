package com.kernel.common.global.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Setter
@Getter
public class FileUploadReqDTO {

    // UploadedFiles 객체 ID
    private Long fileId;

    // 업로드 할 파일
    @NotEmpty(message = "파일 목록은 필수입니다.")
    private List<MultipartFile> files;
}
