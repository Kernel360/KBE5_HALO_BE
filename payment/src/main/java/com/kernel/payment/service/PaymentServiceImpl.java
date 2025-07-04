package com.kernel.payment.service;

import com.kernel.payment.common.enums.PaymentStatus;
import com.kernel.payment.domain.Payment;
import com.kernel.payment.repository.PaymentRepository;
import com.kernel.payment.service.request.ReservationPayReqDTO;
import com.kernel.sharedDomain.domain.entity.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    public void processReservationPayment(Long reservationId, ReservationPayReqDTO payReqDTO) {

        paymentRepository.save(Payment.builder()
                .reservation(Reservation.builder().reservationId(reservationId).build())
                .amount(payReqDTO.getAmount())
                .paymentMethod(payReqDTO.getPaymentMethod())
                .status(PaymentStatus.SUCCESS)
                .build()
        );
    }

    @Override
    public void changeStatus(Long reservationId, PaymentStatus newStatus) {

        Payment foundPayment = paymentRepository.findByReservation_ReservationId(reservationId)
                .orElseThrow(() -> new NoSuchElementException("결제 정보가 존재하지 않습니다."));

        foundPayment.updateStatus(newStatus);

    }
}
