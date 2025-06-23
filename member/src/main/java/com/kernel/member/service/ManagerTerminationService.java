package com.kernel.member.service;

import com.kernel.member.service.request.ManagerTerminationReqDTO;
import com.kernel.member.service.response.ManagerTerminationRspDTO;

public interface ManagerTerminationService {

    void requestTermination(Long managerId, ManagerTerminationReqDTO request);
    ManagerTerminationRspDTO getTerminationDetails(Long managerId);

}
