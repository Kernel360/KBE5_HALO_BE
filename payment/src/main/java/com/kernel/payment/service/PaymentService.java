package com.kernel.payment.service;

import com.kernel.payment.common.enums.PaymentStatus;
import com.kernel.payment.service.request.ReservationPayReqDTO;
import com.kernel.sharedDomain.domain.entity.Reservation;

public interface PaymentService {

    // 예약 결제
    void processReservationPayment(Reservation foundReservation, ReservationPayReqDTO confirmReqDTO);

    // 결제 상태 변경
    void changeStatus(Reservation foundReservation, PaymentStatus newStatus);
}
