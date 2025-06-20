package com.kernel.admin.service.banner;

import com.kernel.admin.domain.entity.UploadedFiles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadedFileRepository extends JpaRepository<UploadedFiles, Long> {
}
