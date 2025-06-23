package com.kernel.member.service;

import com.kernel.member.service.request.ManagerTerminationReqDTO;

public interface ManagerTerminationService {

    void requestTermination(Long managerId, ManagerTerminationReqDTO request);

}
