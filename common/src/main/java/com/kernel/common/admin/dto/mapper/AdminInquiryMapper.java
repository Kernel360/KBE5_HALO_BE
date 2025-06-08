package com.kernel.common.admin.dto.mapper;

import com.kernel.common.admin.dto.response.AdminInquiryDetailRspDTO;
import com.kernel.common.admin.dto.response.AdminInquirySummaryCustomerRspDTO;
import com.kernel.common.admin.dto.response.AdminInquirySummaryManagerRspDTO;
import com.kernel.common.customer.entity.CustomerInquiry;
import com.kernel.common.customer.entity.CustomerReply;
import com.kernel.common.manager.entity.ManagerInquiry;
import com.kernel.common.manager.entity.ManagerReply;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AdminInquiryMapper {

    // CustomerInquiry -> AdminInquirySummaryCustomerRspDTO
    @Mapping(source = "inquiry.category.categoryName", target = "categoryName")
    AdminInquirySummaryCustomerRspDTO toSummaryRspDTO(CustomerInquiry inquiry);

    // ManagerInquiry -> AdminInquirySummaryRspDTO
    AdminInquirySummaryManagerRspDTO toSummaryManagerRspDTO(ManagerInquiry inquiry);

    // CustomerInquiry -> AdminInquiryDetailRspDTO
    @Mapping(source = "inquiry.authorId", target = "authorId")
    @Mapping(source = "inquiry.content", target = "content")
    @Mapping(source = "inquiry.fileId", target = "fileId")
    @Mapping(source = "inquiry.createdAt", target = "createdAt")
    @Mapping(source = "reply.content", target = "reply")
    @Mapping(source = "reply.fileId", target = "replyFileId")
    @Mapping(source = "reply.createdAt", target = "replyCreatedAt")
    @Mapping(source = "reply.authorId", target = "replyAuthorId")
    AdminInquiryDetailRspDTO toDetailRspDTO(CustomerInquiry inquiry, CustomerReply reply);

    // ManagerInquiry -> AdminInquiryDetailRspDTO
    @Mapping(source = "inquiry.authorId", target = "authorId")
    @Mapping(source = "inquiry.content", target = "content")
    @Mapping(source = "inquiry.fileId", target = "fileId")
    @Mapping(source = "inquiry.createdAt", target = "createdAt")
    @Mapping(source = "reply.content", target = "reply")
    @Mapping(source = "reply.fileId", target = "replyFileId")
    @Mapping(source = "reply.createdAt", target = "replyCreatedAt")
    @Mapping(source = "reply.authorId", target = "replyAuthorId")
    AdminInquiryDetailRspDTO toDetailRspDTO(ManagerInquiry inquiry, ManagerReply reply);
}
