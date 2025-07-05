package com.kernel.member.common.exception;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(Long customerId) {
        super("고객을 찾을 수 없습니다. ID: " + customerId);
    }
}
