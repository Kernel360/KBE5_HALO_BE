package com.kernel.common.admin.dto.mapper;

import com.kernel.common.admin.dto.response.AdminSearchRspDTO;
import com.kernel.common.admin.entity.Admin;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AdminSearchMapper {


    @Mapping(target = "phone", source = "admin.phone")
    @Mapping(target = "userName", source = "admin.userName")
    @Mapping(target = "email", source = "admin.email")
    @Mapping(target = "status", source = "admin.status")
    AdminSearchRspDTO toAdminSearchRspDTO(Admin admin);
}
