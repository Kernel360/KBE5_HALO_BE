package com.kernel.admin.repository;

import com.kernel.admin.domain.entity.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminBannerRepository extends JpaRepository<Banner, Long>{

        Page<Banner> findAllByIsDeletedFalse(Pageable pageable);
}