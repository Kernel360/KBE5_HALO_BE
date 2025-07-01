package com.kernel.common.manager.service;

import com.kernel.common.manager.dto.response.ManagerPaymentRspDTO;
import java.util.List;

public interface ManagerPaymentService {

    /**
     * 매니저 주급 정산 조회
     * @param managerId 매니저ID
     * @param searchYear 조회년도
     * @param searchMonth 조회월
     * @param searchWeekIndexInMonth 조회주차수
     * @return 매니저 주급 정산 내역을 담은 응답 리스트
     */
    List<ManagerPaymentRspDTO> getManagerPayments(Long managerId, Integer searchYear, Integer searchMonth, Integer searchWeekIndexInMonth);
}
