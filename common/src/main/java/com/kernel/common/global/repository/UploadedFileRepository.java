package com.kernel.common.global.repository;

import com.kernel.common.global.entity.UploadedFiles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadedFileRepository extends JpaRepository<UploadedFiles, Long> {
}
