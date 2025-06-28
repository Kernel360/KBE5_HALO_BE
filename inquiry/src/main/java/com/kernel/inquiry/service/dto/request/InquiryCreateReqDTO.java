package com.kernel.inquiry.service.dto.request;

import com.kernel.inquiry.common.enums.AuthorType;
import com.kernel.inquiry.domain.entity.Inquiry;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class InquiryCreateReqDTO {

    // 제목
    @NotBlank(message = "제목을 입력해주세요.")
    @Size(max = 50, message = "제목은 최대 50자까지 입력할 수 있습니다.")
    private String title;

    // 내용
    @NotBlank(message = "내용을 입력해주세요.")
    @Size(max = 5000, message = "내용은 최대 5000자까지 입력할 수 있습니다.")
    private String content;

    // 첨부파일
    private List<String> filePaths;

    // 카테고리
    @NotNull(message = "카테고리를 선택해주세요.")
    private Enum<?> category;

    // reqDTO to Entity Mapping
    public static Inquiry toEntity(InquiryCreateReqDTO request, Long authorId, AuthorType authorType) {
        return Inquiry.builder()
                .authorId(authorId)
                .authorType(authorType)
                .title(request.getTitle())
                .content(request.getContent())
                .fileId(request.getFilePaths() != null && !request.getFilePaths().isEmpty() ? Long.parseLong(request.getFilePaths().get(0)) : null) // 첫 번째 파일 ID 사용
                .category(request.getCategory())
                .build();
    }
}
