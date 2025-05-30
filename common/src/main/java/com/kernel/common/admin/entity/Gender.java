package com.kernel.common.admin.entity;

public enum Gender {
    MALE("남자"),
    FEMALE("여자");

    private String genderName;

    Gender(String genderName) {
        this.genderName = genderName;
    }

    public String getGenderName() {
        return genderName;
    }
}

