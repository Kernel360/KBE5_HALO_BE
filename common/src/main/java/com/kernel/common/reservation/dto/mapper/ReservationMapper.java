package com.kernel.common.reservation.dto.mapper;

import com.kernel.common.customer.entity.Customer;
import com.kernel.common.reservation.dto.request.CustomerReservationReqDTO;
import com.kernel.common.reservation.dto.response.ReservationRspDTO;
import com.kernel.common.reservation.entity.Reservation;
import com.kernel.common.reservation.entity.ServiceCategory;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {

    // CustomerReservationReqDTO -> Reservation
    public Reservation toEntity(Long customerId, CustomerReservationReqDTO reservationReqDTO) {
        return Reservation.builder()
                .serviceCategory(ServiceCategory.builder().serviceId(reservationReqDTO.getServiceCategoryId()).build())
                .customer(Customer.builder().customerId(customerId).build())
                .zipcode(reservationReqDTO.getZipcode())
                .roadAddress(reservationReqDTO.getRoadAddress())
                .detailAddress(reservationReqDTO.getDetailAddress())
                .latitude(reservationReqDTO.getLatitude())
                .longitude(reservationReqDTO.getLongitude())
                .requestDate(reservationReqDTO.getRequestDate())
                .startTime(reservationReqDTO.getStartTime())
                .turnaround(reservationReqDTO.getTurnaround())
                .price(reservationReqDTO.getPrice())
                .memo(reservationReqDTO.getMemo())
                .build();
    }

    // Reservation -> ReservationRspDTO
    public ReservationRspDTO toRspDTO(Reservation reservation) {
        return ReservationRspDTO.builder()
                .reservationId(reservation.getReservationId())
                .serviceCategoryId(reservation.getServiceCategory().getServiceId())
                .zipcode(reservation.getZipcode())
                .roadAddress(reservation.getRoadAddress())
                .detailAddress(reservation.getDetailAddress())
                .latitude(reservation.getLatitude())
                .longitude(reservation.getLongitude())
                .requestDate(reservation.getRequestDate())
                .startTime(reservation.getStartTime())
                .turnaround(reservation.getTurnaround())
                .price(reservation.getPrice())
                .memo(reservation.getMemo())
                .build();
    }
}
