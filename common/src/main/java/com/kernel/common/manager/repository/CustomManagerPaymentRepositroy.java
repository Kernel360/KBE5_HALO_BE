package com.kernel.common.manager.repository;

import com.querydsl.core.Tuple;
import java.time.LocalDate;
import java.util.List;

public interface CustomManagerPaymentRepositroy {

    /**
     * 매니저 주급 정산 조회
     * @param managerId 매니저ID
     * @param startDate 주차시작일
     * @param endDate 주차종료일
     * @return 매니저 주급 정산 내역을 담은 리스트
     */
    List<Tuple> getManagerPayments(Long managerId, LocalDate startDate, LocalDate endDate);
}
