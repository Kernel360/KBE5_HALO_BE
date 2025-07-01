package com.kernel.reservation.service;

import com.kernel.reservation.service.request.ReservationReqDTO;
import com.kernel.sharedDomain.domain.entity.Reservation;

public interface ExtraServiceService {

    // 추가 서비스 저장
    void saveExtraServices(ReservationReqDTO reqDTO, Reservation requestedReservation);
}
