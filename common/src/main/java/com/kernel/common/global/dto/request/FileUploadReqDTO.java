package com.kernel.common.global.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
public class FileUploadReqDTO {

    // 업로드한 파일 URL 목록
    @NotEmpty(message = "파일 목록은 필수입니다.")
    private List<String> fileUrls;
}
