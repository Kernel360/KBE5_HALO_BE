package com.kernel.common.global.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class FileUpdateReqDTO {

    @NotNull
    private Long fileId;

    @NotEmpty
    private List<String> fileUrls;

}
