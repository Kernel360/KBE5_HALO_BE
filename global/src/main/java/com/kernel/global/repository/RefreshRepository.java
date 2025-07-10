package com.kernel.global.repository;

import com.kernel.global.domain.entity.Refresh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface RefreshRepository extends JpaRepository<Refresh, Long> {

    @Transactional(readOnly = true)
    Boolean existsByRefreshToken(String refreshToken);

    @Transactional
    void deleteByRefreshToken(String refreshToken);
}
