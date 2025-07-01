package com.kernel.payment.service;

import com.kernel.payment.service.request.ReservationPayReqDTO;

public interface PaymentService {

    // 예약 결제
    void processReservationPayment(Long reservationId, ReservationPayReqDTO confirmReqDTO);
}
