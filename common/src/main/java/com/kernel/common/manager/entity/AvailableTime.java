package com.kernel.common.manager.entity;

import com.kernel.common.global.entity.BaseEntity;
import com.kernel.common.global.enums.DayOfWeek;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "available_time")
@Getter
@SuperBuilder
@NoArgsConstructor
public class AvailableTime extends BaseEntity {

    // 가능시간ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long timeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id", nullable = false)
    private Manager manager;

    // 가능 요일
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DayOfWeek dayOfWeek;

    // 가능 시간 (8시~18시)
    @Column(nullable = false)
    private LocalTime time;
}
