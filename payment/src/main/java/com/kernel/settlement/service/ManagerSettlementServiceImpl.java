package com.kernel.settlement.service;

import com.kernel.settlement.common.enums.SettlementConfig;
import com.kernel.settlement.common.utils.SettlementCalculator;
import com.kernel.settlement.repository.ManagerSettlementRepository;
import com.kernel.settlement.service.dto.request.ManagerSettlementSearchCond;
import com.kernel.settlement.service.dto.response.ManagerSettlementSumRspDTO;
import com.kernel.settlement.service.dto.response.ManagerSettlementSummaryRspDTO;
import com.kernel.settlement.service.dto.response.ManagerThisWeekEstimatedRspDto;
import com.kernel.settlement.service.info.ManagerSettlementSummaryInfo;
import com.kernel.sharedDomain.service.ReservationQueryPort;
import com.kernel.sharedDomain.service.response.ReservationScheduleInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ManagerSettlementServiceImpl implements ManagerSettlementService {

    private final ManagerSettlementRepository managerSettlementRepository;
    private final ReservationQueryPort reservationQueryPort;

    /**
     * 정산 조회
     * @param cond 시작 날짜
     * @param pageable 페이지
     * @param userId 로그인한 유저Id
     * @return 조회된 정산 내역
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ManagerSettlementSummaryRspDTO> getSettlementWithPaging(ManagerSettlementSearchCond cond, Pageable pageable, Long userId) {

        // 1. 날짜 기반 예약 조회
        List<Long> reservationIds = reservationQueryPort.getCompletedReservationIdByScheduleAndManagerId(userId, cond.getStartDate(), cond.getEndDate());

        // 2. 정산 조회
        Page<ManagerSettlementSummaryInfo> info = managerSettlementRepository.getSettlementWithPaging(reservationIds, pageable, userId);

        // 3. 예약 일정 조회
        List<ReservationScheduleInfo> foundReservationSchedule = reservationQueryPort.getReservationSchedule(reservationIds);

        // 4. 예약 ID를 키로 하는 Map 생성
        Map<Long, ReservationScheduleInfo> scheduleMap = foundReservationSchedule.stream()
                .collect(Collectors.toMap(ReservationScheduleInfo::getReservationId, schedule -> schedule));

        // 5. 정산 정보와 예약 일정 정보를 매핑하여 DTO 생성
        Page<ManagerSettlementSummaryRspDTO> result = info.map(settlementInfo -> ManagerSettlementSummaryRspDTO.fromInfo(settlementInfo, scheduleMap.get(settlementInfo.getReservationId())));
        
        return result;
    }

    /**
     * 이번주 예상 정산 금액 조회
     * @param userId 로그인한 유저Id
     * @return 조회된 정산 금액
     */
    @Override
    @Transactional(readOnly = true)
    public ManagerThisWeekEstimatedRspDto getThisWeekEstimated(Long userId) {

        // 1. 이번주 계산
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = today.with(DayOfWeek.SUNDAY);

        // 2. 수수료 계산
        SettlementCalculator calculator = SettlementCalculator.builder()
                .feeRate(SettlementConfig.FEE_RATE.getValue())
                .build();

        // 3. 예상 금액 조회(예약확정, 방문완료)
        Long result = reservationQueryPort.getThisWeekEstimated(userId, startOfWeek, endOfWeek);

        return ManagerThisWeekEstimatedRspDto.fromInfo(calculator.calculateTotalAmount(result.intValue()));
    }

    /**
     * 매니저 이번주, 저번주, 이번달 정산 금액 요약
     * @param userId 로그인한 유저 ID
     * @return 조회된 정산 금액들
     */
    @Override
    @Transactional(readOnly = true)
    public ManagerSettlementSumRspDTO getSettlementSummary(Long userId) {

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
        Long thisWeekEstimated = reservationQueryPort.getThisWeekEstimated(userId, thisWeekStart, thisWeekEnd);

        // 5. 수수료 계산
        SettlementCalculator calculator = SettlementCalculator.builder()
                .feeRate(SettlementConfig.FEE_RATE.getValue())
                .build();

        // 6. 저번주 정산 금액 조회
        Long LastWeekSettled = reservationQueryPort.getSettledAmount(userId, lastWeekStart, lastWeekEnd);

        // 7. 이번달 예상 금액 조회
        Long thisMonthSettled = reservationQueryPort.getSettledAmount(userId, thisMonthStart, lastWeekEnd);

        return ManagerSettlementSumRspDTO.fromInfo(calculator.calculateTotalAmount(thisWeekEstimated), LastWeekSettled, thisMonthSettled);
    }
}

