package com.kernel.common.admin.dto.response;

import com.kernel.common.manager.entity.Manager;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminInquiryDetailManagerRspDTO extends AdminInquiryDetailRspDTO {
    // 관리자 정보
    private Manager author;
}
