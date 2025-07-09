package com.kernel.reservation.service;

import com.kernel.member.domain.entity.CustomerStatistic;
import com.kernel.member.domain.entity.ManagerStatistic;
import jakarta.persistence.OptimisticLockException;

public interface ReservationStatisticUpdateService {

    void updateManagerReservationStatistic(ManagerStatistic managerStatistic, Integer count);

    void updateCustomerReservationStatistic(CustomerStatistic customerStatistic, Integer count);

    void recover(OptimisticLockException e, Object statistic, Integer count);
}
