package com.kernel.common.global.enums;

import lombok.Getter;

@Getter
public enum DayOfWeek {
    MONDAY("월", "월요일", 1),
    TUESDAY("화", "화요일", 2),
    WEDNESDAY("수", "수요일", 3),
    THURSDAY("목", "목요일", 4),
    FRIDAY("금", "금요일", 5),
    SATURDAY("토", "토요일", 6),
    SUNDAY("일", "일요일", 0); // 일요일만 0으로 설정

    private final String label;
    private final String fullLabel;
    private final int value; // ← 추가

    DayOfWeek(String label, String fullLabel, int value) {
        this.label = label;
        this.fullLabel = fullLabel;
        this.value = value;
    }

    public static DayOfWeek fromJavaDayOfWeek(java.time.DayOfWeek javaDayOfWeek) {
        int val = javaDayOfWeek.getValue() % 7; // 일요일이면 7 → 0
        for (DayOfWeek day : DayOfWeek.values()) {
            if (day.getValue() == val) {
                return day;
            }
        }
        throw new IllegalArgumentException("Invalid day value: " + val);
    }
}
