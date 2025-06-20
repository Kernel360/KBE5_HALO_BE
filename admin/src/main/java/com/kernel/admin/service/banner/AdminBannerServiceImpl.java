package com.kernel.admin.service.banner;

import com.kernel.admin.domain.entity.Banner;
import com.kernel.admin.repository.AdminBannerRepository;
import com.kernel.admin.service.dto.request.AdminBannerReqDTO;
import com.kernel.admin.service.dto.responsse.AdminBannerRspDTO;

// import com.kernel.global.domain.entity.UploadedFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AdminBannerServiceImpl implements AdminBannerService {

    private final AdminBannerRepository adminBannerRepository;
    // private final UploadedFileRepository uploadedFileRepository;

    /**
     * 배너 목록 조회
     *
     * @param pageable 페이징 정보
     * @return 배너 목록 페이지
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AdminBannerRspDTO> getBanners(Pageable pageable) {
        return adminBannerRepository.findAllByIsDeletedFalse(pageable)
                .map(AdminBannerRspDTO::from);
    }
    /**
     * 배너 상세 조회
     *
     * @param bannerId 배너 ID
     * @return 배너 응답 DTO
     */
    @Override
    @Transactional(readOnly = true)
    public AdminBannerRspDTO getBanner(Long bannerId) {
        Banner banner = adminBannerRepository.findById(bannerId)
                .orElseThrow(() -> new NoSuchElementException("Banner not found with id: " + bannerId));
        banner.incrementViews();
        return AdminBannerRspDTO.from(banner);
    }

    /**
     * 배너 생성
     *
     * @param bannerDto 배너 요청 DTO
     */
    @Override
    @Transactional
    public void createBanner(AdminBannerReqDTO bannerDto) {
        Banner banner = bannerDto.toEntity();
        adminBannerRepository.save(banner);


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
        Banner banner = adminBannerRepository.findById(bannerId)
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
        Banner banner = adminBannerRepository.findById(bannerId)
                .orElseThrow(() -> new NoSuchElementException("Banner not found"));
        banner.delete();
    }
}
      /*  UploadedFiles file = uploadedFileRepository.findById(banner.getFileId())
                .orElseThrow(() -> new NoSuchElementException("File not found"));
*/
        // 배너 삭제 전 업로드 파일 삭제

