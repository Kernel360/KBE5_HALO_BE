package com.kernel.reservation.service;


import com.kernel.reservation.repository.common.ServiceCategoryRepository;
import com.kernel.reservation.service.info.ServiceCategoryTreeInfo;
import com.kernel.reservation.service.response.common.ServiceCategoryTreeDTO;
import com.kernel.sharedDomain.domain.entity.ServiceCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ServiceCategoryServiceImpl implements ServiceCategoryService {

    private final ServiceCategoryRepository serviceCategoryRepository;

    /**
     * 서비스 카테고리 List 조회 for 예약요청service
     * @return 조회된 서비스 카테고리 List
     */
    @Override
    public List<ServiceCategory> getServiceCategoriesById(List<Long> serviceCategoryId) {
        return serviceCategoryRepository.findAllById(serviceCategoryId);
    }

    /**
     * 서비스 카테고리 조회 for 예약요청service
     * @return 조회된 서비스 카테고리
     */
    @Override
    public ServiceCategory getServiceCategoryById(Long serviceCategoryId) {
        return serviceCategoryRepository.findById(serviceCategoryId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 카테고리입니다."));
    }

    /**
     * 전체 서비스 카테고리 조회
     * @return 서비스 카테고리 tree
     */
    @Override
    @Transactional(readOnly = true)
    public List<ServiceCategoryTreeDTO> getServiceCategoryTree() {

        List<ServiceCategoryTreeInfo> result = serviceCategoryRepository.getServiceCategoryTree();

        return result.stream()
                .map(ServiceCategoryTreeDTO::fromInfo)
                .toList();
    }

    /**
     * 요청 서비스 카테고리 조회
     * @param mainServiceId 요청 메인 서비서ID
     * @param extraServiceId 요청 추가서비스 ID
     * @return 서비스 카테고리 tree
     */
    @Override
    @Transactional(readOnly = true)
    public ServiceCategoryTreeDTO getRequestServiceCategory(Long mainServiceId, List<Long> extraServiceId) {

        ServiceCategoryTreeInfo result = serviceCategoryRepository.getRequestServiceCategory(mainServiceId, extraServiceId);

        return ServiceCategoryTreeDTO.fromInfo(result);
    }


}
