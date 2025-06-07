package com.kernel.common.reservation.service;

import com.kernel.common.reservation.dto.response.ServiceCategoryTreeDTO;
import com.kernel.common.reservation.repository.ReservationServiceCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceCategoryServiceImpl implements ServiceCategoryService {

    private final ReservationServiceCategoryRepository serviceCategoryRepository;

    /**
     * 서비스 카테고리 조회
     * @return 서비스 카테고리 tree
     */
    @Override
    @Transactional
    public List<ServiceCategoryTreeDTO> getServiceCategoryTree() {

        return serviceCategoryRepository.getServiceCategoryTree();
    }

    /**
     * 요청 서비스 카테고리 조회
     * @param serviceId 요청 서비스 카테고리ID
     * @return 서비스 카테고리 tree
     */
    @Override
    @Transactional(readOnly = true)
    public ServiceCategoryTreeDTO getRequestServiceCategory(Long serviceId) {

        return serviceCategoryRepository.getRequestServiceCategory(serviceId);
    }
}
