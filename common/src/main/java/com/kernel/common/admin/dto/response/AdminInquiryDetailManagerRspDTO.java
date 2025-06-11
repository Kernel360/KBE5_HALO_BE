package com.kernel.common.admin.dto.response;

import com.kernel.common.manager.entity.Manager;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminInquiryDetailManagerRspDTO extends AdminInquiryDetailRspDTO {
    // 매니저 이름
    private String userName;

    // 매니저 연락처
    private String phone;

    // 매니저 이메일
    private String email;
}
