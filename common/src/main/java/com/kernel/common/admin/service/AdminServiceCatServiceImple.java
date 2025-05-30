package com.kernel.common.admin.service;


import com.kernel.common.admin.dto.mapper.ServiceMapper;
import com.kernel.common.admin.dto.request.AdminServiceCatReqDTO;
import com.kernel.common.admin.dto.response.AdminServiceCatRspDTO;
import com.kernel.common.admin.repository.ServiceCatRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceCatServiceImple implements AdminSerivceCatService {

    private final ServiceCatRepo serviceCatRepo;
    private final ServiceMapper serviceMapper;

    @Transactional(readOnly = true)
    @Override
    public List<AdminServiceCatRspDTO>  getServiceCats() {
        // 서비스 카테고리 목록 조회
        return serviceMapper.toServiceCatRspDTOList(serviceCatRepo.findAll());
    }

    @Transactional
    @Override
    public void createServiceCat(AdminServiceCatReqDTO request) {
        // 서비스 카테고리 생성
        serviceCatRepo.save(serviceMapper.toServiceCat(request));
    }

    @Transactional
    @Override
    public void updateServiceCat(Long serviceCatId, AdminServiceCatReqDTO request) {
        // 서비스 카테고리 수정
        serviceCatRepo.findById(serviceCatId).ifPresentOrElse(
            serviceCategory -> {
                serviceCategory.update(request);
            },
            () -> log.error("서비스 카테고리를 찾을 수 없습니다. ID: {}", serviceCatId)
        );
    }

    @Transactional
    @Override
    public void deleteServiceCat(Long serviceCatId) {
        // 서비스 카테고리 삭제
        serviceCatRepo.findById(serviceCatId).ifPresentOrElse(
            serviceCategory -> {
                serviceCategory.delete();
                serviceCatRepo.save(serviceCategory);
            },
            () -> log.error("서비스 카테고리를 찾을 수 없습니다. ID: {}", serviceCatId)
        );
    }
}