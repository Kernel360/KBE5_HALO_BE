package com.kernel.common.admin.service;

import com.kernel.common.admin.dto.mapper.AdminBannerMapper;
import com.kernel.common.admin.dto.request.AdminBannerReqDTO;
import com.kernel.common.admin.dto.response.AdminBannerRspDTO;
import com.kernel.common.global.entity.Banner;
import com.kernel.common.global.entity.UploadedFiles;
import com.kernel.common.global.enums.PostStatus;
import com.kernel.common.repository.BannerRepository;

import com.kernel.common.global.repository.UploadedFileRepository;
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
    private final UploadedFileRepository uploadedFileRepository;
    private final AdminBannerMapper adminBannerMapper;

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

        return banners.map(adminBannerMapper::toAdminBannerRspDTO);
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

        return adminBannerMapper.toAdminBannerRspDTO(banner);
    }

    /**
     * 배너 생성
     *
     * @param bannerDto 배너 요청 DTO
     */
    @Override
    @Transactional
    public void createBanner(AdminBannerReqDTO bannerDto) {
        Banner banner = adminBannerMapper.toBannerEntity(bannerDto);
        bannerRepository.save(banner);

        // 배너 생성 후 파일 정보 업데이트
        Long bannerId = banner.getBannerId();

//        UploadedFiles file = uploadedFileRepository.findById(bannerDto.getFileId())
//                .orElseThrow(() -> new NoSuchElementException("File not found"));

//        file.updatePostId(bannerId);
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
                .orElseThrow(() -> new NoSuchElementException("Banner not found"));
        banner.update(bannerDto);
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
                .orElseThrow(() -> new NoSuchElementException("Banner not found"));

      /*  UploadedFiles file = uploadedFileRepository.findById(banner.getFileId())
                .orElseThrow(() -> new NoSuchElementException("File not found"));
*/
        // 배너 삭제 전 업로드 파일 삭제
        banner.delete();
    }
}
