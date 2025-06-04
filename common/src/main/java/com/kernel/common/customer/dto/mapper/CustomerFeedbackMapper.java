package com.kernel.common.customer.dto.mapper;

import com.kernel.common.customer.dto.request.CustomerFeedbackReqDTO;
import com.kernel.common.customer.dto.response.CustomerFeedbackUpdateRspDTO;
import com.kernel.common.customer.entity.Customer;
import com.kernel.common.global.entity.Feedback;
import com.kernel.common.manager.entity.Manager;
import org.springframework.stereotype.Component;

@Component
public class CustomerFeedbackMapper {

    // reqDTO -> Entity
    public Feedback toEntity(Long customerId, CustomerFeedbackReqDTO reqDTO){
        return Feedback.builder()
                .manager(Manager.builder().managerId(reqDTO.getManagerId()).build())
                .customer(Customer.builder().customerId(customerId).build())
                .type(reqDTO.getType())
                .build();
    }

    // Entity -> rspDTO
    public CustomerFeedbackUpdateRspDTO toRspDTO(Feedback feedback){
        return CustomerFeedbackUpdateRspDTO.builder()
                .managerId(feedback.getManager().getManagerId())
                .type(feedback.getType())
                .deleted(feedback.getDeleted())
                .build();
    }

}
