package com.kernel.admin.service;

import com.kernel.admin.domain.entity.Banner;
import com.kernel.admin.repository.BannerRepository;
import com.kernel.admin.service.dto.request.AdminBannerReqDTO;
import com.kernel.admin.service.dto.response.AdminBannerRspDTO;
import com.kernel.global.common.enums.PostStatus;
import com.kernel.global.domain.entity.File;
import com.kernel.global.repository.FileRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AdminBannerServiceImpl implements AdminBannerService {

    private final BannerRepository bannerRepository;
    private final FileRepository fileRepository;

    /**
     * 배너 목록 조회
     *
     * @param pageable 페이징 정보
     * @return 배너 목록 페이지
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AdminBannerRspDTO> getBanners(Pageable pageable) {
        Page<Banner> banners = bannerRepository.findAllByIsDeletedFalse(pageable);

        return AdminBannerRspDTO.fromEntityPage(banners);
    }

    /**
     * 배너 상세 조회
     *
     * @param bannerId 배너 ID
     * @return 배너 응답 DTO
     */
    @Override
    @Transactional
    public AdminBannerRspDTO getBanner(Long bannerId) {
        Banner banner = bannerRepository.findById(bannerId)
                .orElseThrow(() -> new NoSuchElementException("Banner not found with id: " + bannerId));
        banner.incrementViews();

        return AdminBannerRspDTO.fromEntity(banner);
    }

    /**
     * 배너 생성
     *
     * @param bannerDto 배너 요청 DTO
     */
    @Override
    @Transactional
    public void createBanner(AdminBannerReqDTO bannerDto) {
        // 파일 정보 정보 조회
        File file = fileRepository.findById(bannerDto.getFileId())
                .orElseThrow(() -> new NoSuchElementException("파일을 찾을 수 없습니다."));

        Banner banner = AdminBannerReqDTO.toEntity(bannerDto, file);
        bannerRepository.save(banner);

        file.updatePostStatus(PostStatus.REGISTERED);
    }

    /**
     * 배너 수정
     *
     * @param bannerId  배너 ID
     * @param bannerDto 배너 요청 DTO
     */
    @Override
    @Transactional
    public void updateBanner(Long bannerId, AdminBannerReqDTO bannerDto) {
        Banner banner = bannerRepository.findById(bannerId)
                .orElseThrow(() -> new NoSuchElementException("배너를 찾을 수 없습니다."));

        File file = fileRepository.findById(bannerDto.getFileId())
                .orElseThrow(() -> new NoSuchElementException("파일을 찾을 수 없습니다."));

        banner.update(bannerDto, file);
    }

    /**
     * 배너 삭제
     *
     * @param bannerId 배너 ID
     */
    @Override
    @Transactional
    public void deleteBanner(Long bannerId) {
        Banner banner = bannerRepository.findById(bannerId)
                .orElseThrow(() -> new NoSuchElementException("배너를 찾을 수 없습니다."));

        File file = fileRepository.findById(banner.getFile().getFileId())
                .orElseThrow(() -> new NoSuchElementException("파일을 찾을 수 없습니다."));

        // 배너 삭제 전 업로드 파일 삭제
        banner.delete();
    }
}
