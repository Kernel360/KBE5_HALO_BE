package com.kernel.common.global.enums;

import lombok.Getter;

@Getter
public enum DayOfWeek {
    MONDAY("월", "월요일"),
    TUESDAY("화", "월요일"),
    WEDNESDAY("수", "수요일"),
    THURSDAY("목", "목요일"),
    FRIDAY("금", "금요일"),
    SATURDAY("토", "토요일"),
    SUNDAY("일", "일요일");

    private String label;     // 짧은 이름
    private String fullLabel; // 전체 이름

    private DayOfWeek(String label, String fullLabel) {
        this.label = label;
        this.fullLabel = fullLabel;
    }
}
