package com.kernel.common.global.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {
     private boolean success;
     private String message;
     private T body;
}
