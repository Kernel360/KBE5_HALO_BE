package com.kernel.reservation.service;

import com.kernel.reservation.domain.entity.ExtraService;
import com.kernel.reservation.repository.common.ExtraServiceRepository;
import com.kernel.reservation.service.request.ReservationReqDTO;
import com.kernel.sharedDomain.domain.entity.Reservation;
import com.kernel.sharedDomain.domain.entity.ServiceCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExtraServiceServiceImpl implements ExtraServiceService {

    private final ExtraServiceRepository extraServiceRepository;
    private final ServiceCategoryService serviceCategoryService;

    /**
     * 예약 요청시 추가서브시 저장
     * @param reqDTO 수요자 예약 요청 DTO
     * @param requestedReservation 수요자 요청 예약
     */
    @Override
    public void saveExtraServices(ReservationReqDTO reqDTO, Reservation requestedReservation) {

        // 1. 추가 서비스 조회
        List<ServiceCategory> categories = serviceCategoryService.getServiceCategoriesById(reqDTO.getAdditionalServiceIds());

        // 2. 추가 서비스 저장
        List<ExtraService> extraServices = categories.stream()
                .map(category -> ExtraService.builder()
                        .reservation(requestedReservation)
                        .price(category.getPrice())
                        .serviceTime(category.getServiceTime())
                        .build())
                .collect(Collectors.toList());

        extraServiceRepository.saveAll(extraServices);
    }

}
