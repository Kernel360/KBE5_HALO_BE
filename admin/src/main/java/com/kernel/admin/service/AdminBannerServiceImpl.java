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

        // 1. 배너 목록 조회 (삭제되지 않은 배너만)
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

        // 1. 배너 조회
        Banner banner = bannerRepository.findById(bannerId)
                .orElseThrow(() -> new NoSuchElementException("배너가 존재하지 않습니다."));

        // 2. 배너 조회수 증가
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

        // 1. 파일 정보 정보 조회
        File file = fileRepository.findById(bannerDto.getFileId())
                .orElseThrow(() -> new NoSuchElementException("파일을 찾을 수 없습니다."));

        // 2. DTO -> Entity 변환 및 배너 저장
        Banner banner = AdminBannerReqDTO.toEntity(bannerDto, file);
        bannerRepository.save(banner);

        // 3. 파일 상태 업데이트
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

        // 1. 배너 정보 조회
        Banner banner = bannerRepository.findById(bannerId)
                .orElseThrow(() -> new NoSuchElementException("배너를 찾을 수 없습니다."));

        // 2. 파일 정보 조회
        File file = fileRepository.findById(bannerDto.getFileId())
                .orElseThrow(() -> new NoSuchElementException("파일을 찾을 수 없습니다."));

        // 3. 배너 정보 업데이트
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

        // 1. 배너 정보 조회
        Banner banner = bannerRepository.findById(bannerId)
                .orElseThrow(() -> new NoSuchElementException("배너를 찾을 수 없습니다."));

        // 2. 파일 정보 조회
        File file = fileRepository.findById(banner.getFile().getFileId())
                .orElseThrow(() -> new NoSuchElementException("파일을 찾을 수 없습니다."));

        // 3. 배너 삭제
        banner.delete();

        //4. 파일의 게시물 상태 업데이트
        file.updatePostStatus(PostStatus.DELETED);
    }
}
