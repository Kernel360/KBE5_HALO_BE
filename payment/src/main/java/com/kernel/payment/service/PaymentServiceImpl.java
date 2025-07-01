package com.kernel.payment.service;

import com.kernel.payment.common.enums.PaymentStatus;
import com.kernel.payment.domain.Payment;
import com.kernel.payment.repository.PaymentRepository;
import com.kernel.payment.service.request.ReservationPayReqDTO;
import com.kernel.sharedDomain.domain.entity.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    @Transactional
    public void processReservationPayment(Long reservationId, ReservationPayReqDTO payReqDTO) {

        paymentRepository.save(Payment.builder()
                .reservation(Reservation.builder().reservationId(reservationId).build())
                .amount(payReqDTO.getAmount())
                .paymentMethod(payReqDTO.getPaymentMethod())
                .status(PaymentStatus.SUCCESS)
                .build()
        );
    }
}
