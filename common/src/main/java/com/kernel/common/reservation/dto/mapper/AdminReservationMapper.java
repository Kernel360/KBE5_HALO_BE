package com.kernel.common.reservation.dto.mapper;

import com.kernel.common.reservation.dto.response.AdminReservationDetailRspDTO;
import com.kernel.common.reservation.dto.response.AdminReservationListRspDTO;
import com.kernel.common.reservation.entity.Reservation;
import org.springframework.stereotype.Component;

@Component
public class AdminReservationMapper {

    public AdminReservationListRspDTO toAdminListRspDTO(Reservation reservation) {
        return AdminReservationListRspDTO.builder()
                .reservationId(reservation.getReservationId())
                .requestDate(reservation.getRequestDate())
                .startTime(reservation.getStartTime())
                .roadAddress(reservation.getRoadAddress())
                .detailAddress(reservation.getDetailAddress())
                .managerName(reservation.getManager() != null ? reservation.getManager().getUserName() : null)
                .customerName(reservation.getCustomer().getUserName())
                .status(reservation.getStatus())
                .serviceName(reservation.getServiceCategory().getServiceName())
                .build();
    }

    public AdminReservationDetailRspDTO toAdminDetailRspDTO(Reservation reservation) {
        return AdminReservationDetailRspDTO.builder()
                .reservationId(reservation.getReservationId())
                .requestDate(reservation.getRequestDate())
                .startTime(reservation.getStartTime())
                .roadAddress(reservation.getRoadAddress())
                .detailAddress(reservation.getDetailAddress())
                .managerId(reservation.getManager() != null ? reservation.getManager().getUserName() : null)
                .managerPhone(reservation.getManager() != null ? reservation.getManager().getPhone() : null)
                .customerId(reservation.getCustomer().getUserName())
                .customerPhone(reservation.getCustomer().getPhone())
                .status(reservation.getStatus())
                .serviceName(reservation.getServiceCategory().getServiceName())
                .price(reservation.getPrice())
                .memo(reservation.getMemo())
                .cancelReason(reservation.getCancelReason())
                .build();
    }
}
