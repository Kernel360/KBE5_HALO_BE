package com.kernel.global.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface ReissueService {
    ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response);
}
