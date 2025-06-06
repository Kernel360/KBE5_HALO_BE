package com.kernel.common.repository;

import com.kernel.common.global.entity.UploadedFiles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadedFileRepository extends JpaRepository<UploadedFiles, Long> {
}
