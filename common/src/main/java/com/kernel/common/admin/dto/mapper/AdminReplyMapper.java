package com.kernel.common.admin.dto.mapper;

import com.kernel.common.admin.dto.request.AdminInquiryReplyReqDTO;
import com.kernel.common.customer.entity.CustomerInquiry;
import com.kernel.common.customer.entity.CustomerReply;
import com.kernel.common.manager.entity.ManagerInquiry;
import com.kernel.common.manager.entity.ManagerReply;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AdminReplyMapper {

    // AdminInquiryReplyReqDTO -> CustomerReply
    @Mapping(target = "answerId", ignore = true)
    @Mapping(source = "inquiry", target = "customerInquiry")
    @Mapping(source = "reqDTO.content", target = "content")
    @Mapping(source = "reqDTO.fileId", target = "fileId")
    @Mapping(source = "authorId", target = "authorId")
    CustomerReply toCustomerReply(AdminInquiryReplyReqDTO reqDTO, Long authorId, CustomerInquiry inquiry);

    // AdminInquiryReplyReqDTO -> ManagerReply
    @Mapping(target = "answerId", ignore = true)
    @Mapping(source = "inquiry", target = "managerInquiry")
    @Mapping(source = "reqDTO.content", target = "content")
    @Mapping(source = "reqDTO.fileId", target = "fileId")
    @Mapping(source = "authorId", target = "authorId")
    ManagerReply toManagerReply(AdminInquiryReplyReqDTO reqDTO, Long authorId, ManagerInquiry inquiry);


}
