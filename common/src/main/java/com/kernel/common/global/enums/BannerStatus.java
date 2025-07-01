package com.kernel.common.global.enums;

public enum BannerStatus {
    ACTIVE("활성"),
    PENDING("대기"),
    EXPIRED("만료");

    private final String label;

    BannerStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
