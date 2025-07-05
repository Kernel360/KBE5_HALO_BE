package com.kernel.payment.service;

import com.kernel.payment.common.enums.PaymentStatus;
import com.kernel.payment.service.request.ReservationPayReqDTO;

public interface PaymentService {

    // 예약 결제
    void processReservationPayment(Long reservationId, ReservationPayReqDTO confirmReqDTO);

    // 결제 상태 변경
    void changeStatus(Long reservationId, PaymentStatus newStatus);
}
