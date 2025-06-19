package com.kernel.member.domain.enumerate;


public enum Gender {
    FEMALE("여자"), MALE("남자");

    private final String label;

    Gender(String label) {
        this.label = label;
    }
}
