package com.kernel.common.repository;

import com.kernel.common.global.entity.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BannerRepository extends JpaRepository<Banner, Long> {
    Page<Banner> findAllByIsDeletedFalse(Pageable pageable);
}
