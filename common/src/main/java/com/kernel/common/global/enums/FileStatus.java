package com.kernel.common.global.enums;

import lombok.Getter;

@Getter
public enum FileStatus {
    TEMP("임시"),
    REGISTERED("등록됨");

    private final String label;

    FileStatus(String label) {
        this.label = label;
    }

    /*public static FileStatus fromString(String status) {
        for (FileStatus fileStatus : values()) {
            if (fileStatus.name().equalsIgnoreCase(status)) {
                return fileStatus;
            }
        }
        throw new IllegalArgumentException("Unknown file status: " + status);
    }*/
}
