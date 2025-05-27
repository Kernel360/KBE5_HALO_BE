package com.kernel.common.admin.dto.request;

import com.kernel.common.admin.entity.Status; // 추후에 Status entity가 정의된 위치로 변경 필요

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ManagerRequestDTO {
    // TODO: Request로 받을 필드 정의
    private Status status; // manager 패키지나 global 패키지에서 Status는 enum으로 정의되어 있다고 가정
}
