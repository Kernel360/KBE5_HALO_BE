package com.kernel.member.common.exception;

public class ManagerNotFoundException extends RuntimeException {
    public ManagerNotFoundException(Long managerId) {
        super("매니저를 찾을 수 없습니다. ID: " + managerId);
    }
}
