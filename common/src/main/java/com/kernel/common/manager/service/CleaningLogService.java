package com.kernel.common.manager.service;

import com.kernel.common.manager.dto.request.CleaningLogCheckInReqDTO;
import com.kernel.common.manager.dto.request.CleaningLogCheckOutReqDTO;
import com.kernel.common.manager.dto.response.CleaningLogCheckInRspDTO;
import com.kernel.common.manager.dto.response.CleaningLogCheckOutRspDTO;

public interface CleaningLogService {

    /**
     * 체크인
     * @param managerId
     * @param reservationId
     * @param cleaningLogCheckInReqDTO
     * @return 체크인 정보를 담은 응답
     */
    public CleaningLogCheckInRspDTO checkIn(Long managerId, Long reservationId, CleaningLogCheckInReqDTO cleaningLogCheckInReqDTO);

    /**
     * 체크아웃
     * @param managerId
     * @param reservationId
     * @param cleaningLogCheckOutReqDTO
     * @return 체크아웃 정보를 담은 응답
     */
    public CleaningLogCheckOutRspDTO checkOut(Long managerId, Long reservationId, CleaningLogCheckOutReqDTO cleaningLogCheckOutReqDTO);
}
