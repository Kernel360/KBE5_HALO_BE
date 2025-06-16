package com.kernel.common.global.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PresignedUrlRspDTO {
    private List<String> preSignedUrls;
}
