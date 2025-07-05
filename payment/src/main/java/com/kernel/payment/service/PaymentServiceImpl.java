package com.kernel.payment.service;

import com.kernel.payment.common.enums.PaymentErrorCode;
import com.kernel.payment.common.enums.PaymentStatus;
import com.kernel.payment.common.exception.*;
import com.kernel.payment.domain.Payment;
import com.kernel.payment.repository.PaymentRepository;
import com.kernel.payment.service.request.ReservationPayReqDTO;
import com.kernel.sharedDomain.domain.entity.Reservation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    @Transactional
    public void processReservationPayment(Reservation foundReservation, ReservationPayReqDTO payReqDTO) {

        try {
            // 1. 결제 금액 유효성 검증
            validatePaymentAmount(payReqDTO.getAmount());
            
            // 2. 중복 결제 확인
            checkDuplicatePayment(foundReservation.getReservationId());
            
            // 3. 결제 처리
            Payment payment = Payment.builder()
                    .reservation(foundReservation)
                    .amount(payReqDTO.getAmount())
                    .paymentMethod(payReqDTO.getPaymentMethod())
                    .status(PaymentStatus.SUCCESS)
                    .build();
            
            paymentRepository.save(payment);

        } catch (DataAccessException e) {
            log.error("결제 처리 중 데이터베이스 오류 발생 - 예약ID: {}", foundReservation.getReservationId(), e);
            throw new PaymentException(PaymentErrorCode.PAYMENT_SERVER_ERROR);
        } catch (Exception e) {
            log.error("결제 처리 중 예상치 못한 오류 발생 - 예약ID: {}", foundReservation.getReservationId(), e);
            throw new PaymentException(PaymentErrorCode.PAYMENT_FAILED);
        }
    }

    @Override
    @Transactional
    public void changeStatus(Reservation foundReservation, PaymentStatus newStatus) {

        try {
            Payment foundPayment = paymentRepository.findByReservation_ReservationId(foundReservation.getReservationId())
                    .orElseThrow(() -> {
                        log.warn("결제 정보를 찾을 수 없음 - 예약ID: {}", foundReservation.getReservationId());
                        return new NotFoundPaymentException(PaymentErrorCode.NOT_FOUND_PAYMENT);
                    });

            // 상태 변경 가능 여부 검증
            validateStatusChange(foundPayment.getStatus(), newStatus);

            foundPayment.updateStatus(newStatus);

        } catch (DataAccessException e) {
            log.error("결제 상태 변경 중 데이터베이스 오류 발생 - 예약ID: {}", foundReservation.getReservationId(), e);
            throw new PaymentException(PaymentErrorCode.PAYMENT_SERVER_ERROR);
        }
    }

    private void validatePaymentAmount(Integer amount) {
        if (amount == null || amount <= 0) {
            log.warn("잘못된 결제 금액: {}", amount);
            throw new InvalidPaymentAmountException(PaymentErrorCode.INVALID_PAYMENT_AMOUNT);
        }
        if (amount > 10000000) { // 최대 결제 금액 1천만원
            log.warn("결제 금액이 최대 한도 초과: {}", amount);
            throw new InvalidPaymentAmountException(PaymentErrorCode.INVALID_PAYMENT_AMOUNT);
        }
    }

    private void checkDuplicatePayment(Long reservationId) {
        boolean existsPayment = paymentRepository.findByReservation_ReservationId(reservationId).isPresent();
        if (existsPayment) {
            log.warn("이미 결제가 완료된 예약 - 예약ID: {}", reservationId);
            throw new AlreadyPaidException(PaymentErrorCode.ALREADY_PAID_RESERVATION);
        }
    }

    private void validateStatusChange(PaymentStatus currentStatus, PaymentStatus newStatus) {
        // 성공한 결제를 실패로 변경하려는 경우
        if (currentStatus == PaymentStatus.SUCCESS && newStatus == PaymentStatus.FAILED) {
            log.warn("성공한 결제를 실패로 변경할 수 없음 - 현재 상태: {}, 새로운 상태: {}", currentStatus, newStatus);
            throw new PaymentCancelNotAllowedException(PaymentErrorCode.PAYMENT_CANCEL_NOT_ALLOWED);
        }
        
        // 이미 취소된 결제를 다시 변경하려는 경우
        if (currentStatus == PaymentStatus.CANCELED) {
            log.warn("이미 취소된 결제는 상태 변경할 수 없음 - 현재 상태: {}, 새로운 상태: {}", currentStatus, newStatus);
            throw new PaymentCancelNotAllowedException(PaymentErrorCode.PAYMENT_CANCEL_NOT_ALLOWED);
        }
        
        // 이미 환불된 결제를 다시 변경하려는 경우
        if (currentStatus == PaymentStatus.REFUNDED) {
            log.warn("이미 환불된 결제는 상태 변경할 수 없음 - 현재 상태: {}, 새로운 상태: {}", currentStatus, newStatus);
            throw new PaymentCancelNotAllowedException(PaymentErrorCode.PAYMENT_CANCEL_NOT_ALLOWED);
        }
    }
}
