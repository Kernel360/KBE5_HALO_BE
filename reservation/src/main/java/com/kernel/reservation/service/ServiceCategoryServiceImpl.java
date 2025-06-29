package com.kernel.reservation.service;


import com.kernel.reservation.repository.common.ServiceCategoryRepository;
import com.kernel.reservation.service.response.common.ServiceCategoryTreeDTO;
import com.kernel.sharedDomain.domain.entity.ServiceCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

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

        // 1. 모든 카테고리 조회
        List<ServiceCategory> allCategories = serviceCategoryRepository.findAllByOrderByCreatedAtAsc();

        // 2. 활성 카테고리만 필터링
        List<ServiceCategory> validCategories = allCategories.stream()
                .filter(this::isFullyActive)
                .toList();

        // 3. 트리 구성 후 루트만 반환
        Map<Long, ServiceCategoryTreeDTO> treeMap = buildServiceCategoryTree(validCategories);

        return validCategories.stream()
                .filter(c -> c.getParent() == null)
                .map(c -> treeMap.get(c.getServiceId()))
                .collect(Collectors.toList());
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

        // 1. 요청 카테고리 조회
        List<ServiceCategory> filteredCategories = serviceCategoryRepository.getRequestServiceCategory(mainServiceId, extraServiceId);

        // 2. 트리 구성 후 루트 반환
        Map<Long, ServiceCategoryTreeDTO> treeMap = buildServiceCategoryTree(filteredCategories);


        return filteredCategories.stream()
                .filter(c -> c.getParent() == null || !treeMap.containsKey(c.getParent().getServiceId()))
                .map(c -> treeMap.get(c.getServiceId()))
                .findFirst()
                .orElse(null);
    }

    /**
     * 공통 트리 구성 로직
     */
    private Map<Long, ServiceCategoryTreeDTO> buildServiceCategoryTree(List<ServiceCategory> categories) {

        // 1. Entity → DTO 변환
        Map<Long, ServiceCategoryTreeDTO> dtoMap = categories.stream()
                .collect(Collectors.toMap(
                        ServiceCategory::getServiceId,
                        ServiceCategoryTreeDTO::fromEntity,
                        (a, b) -> a,
                        LinkedHashMap::new
                ));

        // 2. 부모-자식 관계 설정
        categories.forEach(category -> {
            if (category.getParent() != null && dtoMap.containsKey(category.getParent().getServiceId())) {
                ServiceCategoryTreeDTO parent = dtoMap.get(category.getParent().getServiceId());
                ServiceCategoryTreeDTO child = dtoMap.get(category.getServiceId());
                parent.getChildren().add(child);
            }
        });

        return dtoMap;
    }

    /**
     * 부모 카테고리 isActive = False 확인
     * @return 활성 여부
     */
    private boolean isFullyActive(ServiceCategory category) {
        while (category != null) {
            if (category.getIsActive() == null || !category.getIsActive()) {
                return false;
            }
            category = category.getParent();
        }
        return true;
    }


}
