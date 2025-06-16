package com.kernel.common.manager.service;

import static com.kernel.common.reservation.entity.QExtraService.extraService;
import static com.kernel.common.reservation.entity.QReservation.reservation;

import com.kernel.common.manager.dto.response.ManagerPaymentRspDTO;
import com.kernel.common.manager.repository.CustomManagerPaymentRepositroy;
import com.kernel.common.reservation.entity.QServiceCategory;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManagerPaymentServiceImpl implements ManagerPaymentService {

    private final CustomManagerPaymentRepositroy paymentRepositroy;

    /**
     * 매니저 주급 정산 조회
     * @param managerId 매니저ID
     * @param searchYear 조회년도
     * @param searchMonth 조회월
     * @param searchWeekIndexInMonth 조회주차수
     * @return 매니저 주급 정산 내역을 담은 응답 리스트
     */
    @Override
    public List<ManagerPaymentRspDTO> getManagerPayments(Long managerId, Integer searchYear, Integer searchMonth, Integer searchWeekIndexInMonth) {

        // 1. 주차에 해당하는 시작일, 종료일 계산
        LocalDate startDate = getWeekStartDate(searchYear, searchMonth, searchWeekIndexInMonth);
        LocalDate endDate = getWeekEndDate(startDate);

        // 2. Q타입 alias 선언 (중복 조인 대비)
        QServiceCategory baseServiceCategory = new QServiceCategory("baseServiceCategory");

        // 3. Tuple로 정산 데이터 조회
        List<Tuple> managerPayments = paymentRepositroy.getManagerPayments(managerId, startDate, endDate);

        // 4. 결과를 reservationId 기준으로 그룹핑 & 누적용 map 선언
        Map<Long, ManagerPaymentRspDTO.ManagerPaymentRspDTOBuilder<?, ?>> grouped = new LinkedHashMap<>();
        Map<Long, Integer> extraPriceMap = new HashMap<>();
        Map<Long, List<ManagerPaymentRspDTO.ExtraServiceDTO>> extraServiceMap = new HashMap<>();

        // 5. 각 튜플 처리
        for (Tuple tuple : managerPayments) {
            Long reservationId = tuple.get(reservation.reservationId);

            // builder 생성 또는 재사용 (불필요한 builder 변수 제거)
            grouped.computeIfAbsent(reservationId, id ->
                ManagerPaymentRspDTO.builder()
                    .reservationId(id)
                    .customerName(tuple.get(reservation.customer.userName))
                    .requestDate(tuple.get(reservation.requestDate))
                    .reservationTime(formatTime(
                        tuple.get(reservation.startTime),
                        tuple.get(reservation.turnaround)
                    ))
                    .turnaround(tuple.get(reservation.turnaround))
                    .serviceName(tuple.get(baseServiceCategory.serviceName))
                    .price(tuple.get(reservation.price))
                    .extraPrice(0) // 나중에 계산해서 set
            );

            // 별칭(as)으로 조회한 필드 꺼내기 (Expressions 사용)
            String extraName = tuple.get(Expressions.stringPath("extraServiceName"));
            Integer extraPrice = tuple.get(Expressions.numberPath(Integer.class, "extraPrice"));
            Long extraServiceId = tuple.get(extraService.extraServiceId);

            // 추가 서비스 정보가 존재할 경우 누적
            if (extraName != null && extraPrice != null && extraServiceId != null) {
                extraServiceMap
                    .computeIfAbsent(reservationId, id -> new ArrayList<>())
                    .add(
                        ManagerPaymentRspDTO.ExtraServiceDTO.builder()
                            .extraServiceId(extraServiceId)
                            .extraServiceName(extraName)
                            .extraPrice(extraPrice)
                            .build()
                    );

                extraPriceMap.merge(reservationId, extraPrice, Integer::sum);
            }
        }

        // 6. 최종 DTO 리스트 반환 + extraService, 정산 금액 설정
        return grouped.entrySet().stream()
            .map(entry -> {
                Long reservationId = entry.getKey();
                ManagerPaymentRspDTO dto = entry.getValue()
                    .extraPrice(extraPriceMap.getOrDefault(reservationId, 0))
                    .extraServices(extraServiceMap.getOrDefault(reservationId, List.of()))
                    .build();

                Integer totalPrice = dto.getPrice() + dto.getExtraPrice();
                Integer commission = (int) Math.floor(totalPrice * 0.2);
                Integer settlement = totalPrice - commission;

                return dto.toBuilder()
                    .totalPrice(totalPrice)
                    .commission(commission)
                    .settlementAmount(settlement)
                    .build();
            })
            .collect(Collectors.toList());
    }



    // 해당 주차의 시작일 구하기
    public static LocalDate getWeekStartDate(Integer year, Integer month, Integer weekIndexInMonth) {
        LocalDate firstDay = LocalDate.of(year, month, 1);

        int weekCount = 0;
        for (int i = 0; i < 31; i++) {
            LocalDate current = firstDay.plusDays(i);
            if (current.getMonthValue() != month) break;
            if (current.getDayOfWeek() == DayOfWeek.MONDAY) {
                weekCount++;
                if (weekCount == weekIndexInMonth) {
                    return current;
                }
            }
        }

        throw new IllegalArgumentException("해당 월에 " + weekIndexInMonth + "번째 주차가 없습니다.");
    }

    // 해당 주차의 종료일 구하기
    public static LocalDate getWeekEndDate(LocalDate startDate) {
        return startDate.plusDays(6); // 월요일 + 6일 = 일요일
    }

    // 예약 시간 변환
    private String formatTime(LocalTime startTime, Integer turnaround) {
        if (startTime == null || turnaround == null) return "";

        LocalTime endTime = startTime.plusHours(turnaround);
        return String.format("%s ~ %s",
            startTime.format(DateTimeFormatter.ofPattern("HH:mm")),
            endTime.format(DateTimeFormatter.ofPattern("HH:mm"))
        );
    }
}
