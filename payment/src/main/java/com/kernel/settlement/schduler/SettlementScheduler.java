package com.kernel.settlement.schduler;

import com.kernel.settlement.service.AdminSettlementService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class SettlementScheduler {

    private final AdminSettlementService adminSettlementService;

    @Scheduled(cron = "0 0 2 * * THU")
    public void runWeeklySettlement() {
        LocalDate start = LocalDate.now().minusWeeks(1).with(DayOfWeek.MONDAY);
        LocalDate end = start.plusDays(6);

        adminSettlementService.createWeeklySettlement(start, end, 0L);
    }
}
