package com.kernel.common.admin.dto.mapper;

import com.kernel.common.admin.dto.response.*;
import com.kernel.common.customer.entity.Customer;
import com.kernel.common.customer.entity.CustomerInquiry;
import com.kernel.common.customer.entity.CustomerReply;
import com.kernel.common.manager.entity.Manager;
import com.kernel.common.manager.entity.ManagerInquiry;
import com.kernel.common.manager.entity.ManagerReply;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AdminInquiryMapper {

    // CustomerInquiry -> AdminInquirySummaryCustomerRspDTO
    @Mapping(source = "inquiry.category.categoryName", target = "categoryName")
    @Mapping(expression = "java(inquiry.getCustomerReply() != null && inquiry.getCustomerReply().getAnswerId() != null)", target = "replyStatus")
    AdminInquirySummaryCustomerRspDTO toSummaryRspDTO(CustomerInquiry inquiry);

    // ManagerInquiry -> AdminInquirySummaryRspDTO
    @Mapping(expression = "java(inquiry.getManagerReply() != null && inquiry.getManagerReply().getAnswerId() != null)", target = "replyStatus")
    AdminInquirySummaryRspDTO toSummaryRspDTO(ManagerInquiry inquiry);

    // CustomerInquiry -> AdminInquiryDetailRspDTO
    @Mapping(source = "inquiry.authorId", target = "authorId")
    @Mapping(source = "inquiry.content", target = "content")
    @Mapping(source = "inquiry.fileId", target = "fileId")
    @Mapping(source = "inquiry.createdAt", target = "createdAt")
    @Mapping(source = "inquiry.category.categoryName", target = "categoryName")
    @Mapping(expression = "java(inquiry.getCustomerReply() != null && inquiry.getCustomerReply().getAnswerId() != null)", target = "replyStatus")
    @Mapping(source = "reply.answerId", target = "answerId")
    @Mapping(source = "reply.content", target = "reply")
    @Mapping(source = "reply.fileId", target = "replyFileId")
    @Mapping(source = "reply.createdAt", target = "replyCreatedAt")
    @Mapping(source = "reply.authorId", target = "replyAuthorId")
    AdminInquiryDetailCustomerRspDTO toDetailRspDTO(CustomerInquiry inquiry, CustomerReply reply, Customer author);

    // ManagerInquiry -> AdminInquiryDetailRspDTO
    @Mapping(source = "inquiry.authorId", target = "authorId")
    @Mapping(source = "inquiry.content", target = "content")
    @Mapping(source = "inquiry.fileId", target = "fileId")
    @Mapping(source = "inquiry.createdAt", target = "createdAt")
    @Mapping(expression = "java(inquiry.getManagerReply() != null && inquiry.getManagerReply().getAnswerId() != null)", target = "replyStatus")
    @Mapping(source = "reply.answerId", target = "answerId")
    @Mapping(source = "reply.content", target = "reply")
    @Mapping(source = "reply.fileId", target = "replyFileId")
    @Mapping(source = "reply.createdAt", target = "replyCreatedAt")
    @Mapping(source = "reply.authorId", target = "replyAuthorId")
    AdminInquiryDetailManagerRspDTO toDetailRspDTO(ManagerInquiry inquiry, ManagerReply reply, Manager author);
}
