package com.kernel.member.domain.entity;

import com.kernel.global.domain.entity.User;
import com.kernel.member.common.enums.DayOfWeek;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalTime;

@Entity
@Table(name = "available_time")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
public class AvailableTime {

    // 업무 가능 시간 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long availableTimeId;

    // 매니저 ID
    @ManyToOne
    @JoinColumn(columnDefinition = "manager_id", nullable = false)
    private Manager manager;

    // 업무 가능 요일
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DayOfWeek dayOfWeek;

    // 업무 시작 시간 (HH:mm 형식)
    @Column(nullable = false)
    private LocalTime time;
}
