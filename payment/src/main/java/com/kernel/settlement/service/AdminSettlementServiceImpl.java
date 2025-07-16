package com.kernel.settlement.service;

import com.kernel.global.domain.entity.User;
import com.kernel.settlement.common.enums.SettlementConfig;
import com.kernel.settlement.common.enums.SettlementStatus;
import com.kernel.settlement.common.utils.SettlementCalculator;
import com.kernel.settlement.domain.Settlement;
import com.kernel.settlement.repository.AdminSettlementRepository;
import com.kernel.settlement.service.dto.request.AdminSettlementSearchCond;
import com.kernel.settlement.service.dto.response.AdminSettlementSumRspDTO;
import com.kernel.settlement.service.dto.response.AdminSettlementSummaryRspDTO;
import com.kernel.settlement.service.dto.response.AdminThisWeekEstimatedRspDto;
import com.kernel.settlement.service.dto.response.SettlementCreateRspDTO;
import com.kernel.settlement.service.info.AdminSettlementSummaryInfo;
import com.kernel.settlement.service.info.SettledAmountWithPlatformFeeInfo;
import com.kernel.sharedDomain.domain.entity.Reservation;
import com.kernel.sharedDomain.service.ReservationQueryPort;
import com.kernel.sharedDomain.service.response.ReservationManagerMappingInfo;
import com.kernel.sharedDomain.service.response.ScheduleAndMatchInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminSettlementServiceImpl implements AdminSettlementService {

    private final AdminSettlementRepository adminSettlementRepository;
    private final ReservationQueryPort reservationQueryPort;

    /**
     * 주급 정산 저장
     * @param start 시작 날짜
     * @param end 끝 날짜
     */
    @Override
    @Transactional
    public SettlementCreateRspDTO createWeeklySettlement(LocalDate start, LocalDate end, Long userId) {

        // 1. 정산 예약 조회
        List<Reservation> reservations =
                reservationQueryPort.getCompletedReservationsWithoutSettlement(start, end);

        // 2. 예약 ID 추출
        List<Long> reservationIds = reservations.stream()
                .map(Reservation::getReservationId)
                .toList();

        // 3. 예약 ID 기반 매칭 매니저(User) 조회
        List<ReservationManagerMappingInfo> managerMapping =
                reservationQueryPort.getManagerIdsByReservationIds(reservationIds);

        // 4. Map<reservationId, User> 로 변환
        Map<Long, User> managerMap = managerMapping.stream()
                .collect(Collectors.toMap(ReservationManagerMappingInfo::getReservationId, ReservationManagerMappingInfo::getManager));

        // 5. 수수료율
        SettlementCalculator calculator = SettlementCalculator.builder()
                .feeRate(SettlementConfig.FEE_RATE.getValue())
                .build();

        // 6. 정산 저장
        List<Settlement> settlements = reservations.stream()
                .map(reservation -> {
                    Long reservationId = reservation.getReservationId();
                    User manager = managerMap.get(reservationId);

                    int price = reservation.getPrice();
                    int platformFee = calculator.calculatePlatformFee(price);
                    int totalAmount = price - platformFee;

                    log.info("정산 계산 - 예약ID: {}, 매니저ID: {}, 원가: {}, 수수료: {}, 정산금액: {}", 
                            reservationId, manager.getUserId(), price, platformFee, totalAmount);

                    return Settlement.builder()
                            .reservation(reservation)
                            .manager(manager)
                            .totalAmount(totalAmount)
                            .platformFee(platformFee)
                            .settledAt(LocalDateTime.now())
                            .status(SettlementStatus.SETTLED)
                            .settledBy(userId)
                            .build();
                })
                .collect(Collectors.toList());


        // 6. 정산 저장
        List<Settlement> saved = adminSettlementRepository.saveAll(settlements);

        return SettlementCreateRspDTO.builder().createdCount(saved.size()).build();
    }

    /**
     * 정산 조회
     * @param cond 조회 조건
     * @return 조회된 정산 내역
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AdminSettlementSummaryRspDTO> getSettlementWithPaging(AdminSettlementSearchCond cond, Pageable pageable) {


        // 1. 예약 조회
        List<ScheduleAndMatchInfo> reservationsInfo = reservationQueryPort.getReservationForSettlementByAdmin(cond.getStartDate(), cond.getEndDate());

        // 2. 예약 ID 추출
        List<Long> reservationIds = reservationsInfo.stream()
                .map(ScheduleAndMatchInfo::getReservationId)
                .toList();

        // 3. 정산 조회
        Page<AdminSettlementSummaryInfo> settlementInfo = adminSettlementRepository.getSettlementWithPaging(reservationIds, pageable);

        // 4. ScheduleAndMatchInfo Map 생성
        Map<Long, ScheduleAndMatchInfo> scheduleMap = reservationsInfo.stream()
                .collect(Collectors.toMap(ScheduleAndMatchInfo::getReservationId, Function.identity()));

        // 5. settlementInfo + scheduleInfo -> DTO 변환
        return settlementInfo.map(info -> AdminSettlementSummaryRspDTO.fromInfo(info, scheduleMap.get(info.getReservationId())));
    }

    /**
     * 이번주 예상 정산금액 조회
     * @return 조회된 예쌍 정산 금액
     */
    @Override
    public AdminThisWeekEstimatedRspDto getThisWeekEstimated() {

        // 1. 이번주 계산
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = today.with(DayOfWeek.SUNDAY);

        // 2. 예상 금액 조회(예약확정, 방문완료)
        Long result = reservationQueryPort.getThisWeekEstimatedForAdmin(startOfWeek, endOfWeek);

        // 3. 수수료율
        SettlementCalculator calculator = SettlementCalculator.builder()
                .feeRate(SettlementConfig.FEE_RATE.getValue())
                .build();

        return AdminThisWeekEstimatedRspDto.fromInfo(result, calculator.calculatePlatformFee(result));
    }

    /**
     * 관리자 이번주, 저번주, 이번달 정산 금액 요약
     * @return 조회된 정산 내역
     */
    @Override
    public AdminSettlementSumRspDTO getSettlementSummary() {

        LocalDate today = LocalDate.now();

        // 1. 이번주 (월~일)
        LocalDate thisWeekStart = today.with(DayOfWeek.MONDAY);
        LocalDate thisWeekEnd = today.with(DayOfWeek.SUNDAY);

        // 2. 저번주 (월~일)
        LocalDate lastWeekStart = thisWeekStart.minusWeeks(1);
        LocalDate lastWeekEnd = thisWeekEnd.minusWeeks(1);

        // 3. 이번달 (1일~저번주 일요일)
        LocalDate thisMonthStart = today.withDayOfMonth(1);

        // 4. 이번주 예상 금액 조회
        Long thisWeekEstimated = reservationQueryPort.getThisWeekEstimatedForAdmin(thisWeekStart, thisWeekEnd);

        // 5. 수수료율
        SettlementCalculator calculator = SettlementCalculator.builder()
                .feeRate(SettlementConfig.FEE_RATE.getValue())
                .build();

        // 6. 저번주 정산 금액 조회
        List<Long> lastWeekReservation = reservationQueryPort.getSettledAmountWithoutUserId(lastWeekStart, lastWeekEnd);

        SettledAmountWithPlatformFeeInfo lastWeekSettlement = adminSettlementRepository.getSettlementByReservationIds(lastWeekReservation);

        // 7. 이번달 예상 금액 조회
        List<Long> lastMonthReservation = reservationQueryPort.getSettledAmountWithoutUserId(thisMonthStart, lastWeekEnd);

        SettledAmountWithPlatformFeeInfo lastMonthSettlement = adminSettlementRepository.getSettlementByReservationIds(lastMonthReservation);

        // 8. 이번주 예상 수수료 계산
        Long thisWeekEstimatedPlatformFee = calculator.calculatePlatformFee(thisWeekEstimated);

        return AdminSettlementSumRspDTO.fromInfo(
                thisWeekEstimated,
                thisWeekEstimatedPlatformFee,
                lastWeekSettlement != null ? lastWeekSettlement.getTotalAmount() : 0L,
                lastWeekSettlement != null ? lastWeekSettlement.getPlatformFee() : 0L,
                lastMonthSettlement != null ? lastMonthSettlement.getTotalAmount() : 0L,
                lastMonthSettlement != null ? lastMonthSettlement.getPlatformFee() : 0L
        );
    }
}
