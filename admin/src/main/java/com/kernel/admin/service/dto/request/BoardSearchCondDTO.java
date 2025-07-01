package com.kernel.admin.service.dto.request;

import com.kernel.admin.common.enums.BoardType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardSearchCondDTO {

    // 보드 타입
     private BoardType boardType;

     // 제목
    private String title;
}
