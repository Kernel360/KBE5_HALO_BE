package com.kernel.common.manager.service;

import com.kernel.common.manager.dto.mapper.CleaningLogMapper;
import com.kernel.common.manager.dto.request.CleaningLogCheckInReqDTO;
import com.kernel.common.manager.dto.request.CleaningLogCheckOutReqDTO;
import com.kernel.common.manager.dto.response.CleaningLogCheckInRspDTO;
import com.kernel.common.manager.dto.response.CleaningLogCheckOutRspDTO;
import com.kernel.common.manager.entity.CleaningLog;
import com.kernel.common.manager.repository.CleaningLogRepository;
import com.kernel.common.reservation.entity.Reservation;
import com.kernel.common.reservation.enums.ReservationStatus;
import com.kernel.common.reservation.repository.ReservationRepository;
import jakarta.transaction.Transactional;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CleaningLogServiceImpl implements CleaningLogService {

    private final CleaningLogRepository cleaningLogRepository;
    private final ReservationRepository reservationRepository;
    private final CleaningLogMapper cleaningLogMapper;

    /**
     * 체크인
     * @param managerId 매니저ID
     * @param reservationId 예약ID
     * @param cleaningLogCheckInReqDTO 체크인요청DTO
     * @return 체크인 정보를 담은 응답
     */
    @Override
    @Transactional
    public CleaningLogCheckInRspDTO checkIn(Long managerId, Long reservationId, CleaningLogCheckInReqDTO cleaningLogCheckInReqDTO) {

        // 해당 예약건의 매니저가 맞는지 체크
        if (!reservationRepository.existsByReservationIdAndManager_ManagerId(reservationId, managerId)) {
            throw new IllegalStateException("해당 예약건의 매니저가 아닙니다.");
        }

        // 이미 등록된 cleaning log가 있는지 체크
        if (cleaningLogRepository.existsByReservation_ReservationId(reservationId)) {
            throw new IllegalStateException("이미 체크인된 예약입니다.");
        }

        // RequestDTO -> Entity
        Reservation foundReservation = reservationRepository.findReservationByreservationId(reservationId);
        if (foundReservation == null) {
            throw new NoSuchElementException("존재하지 않는 예약입니다.");
        }
        CleaningLog checkedInEntity = cleaningLogMapper.toCheckInEntity(foundReservation, cleaningLogCheckInReqDTO);

        // 체크인
        CleaningLog savedCheckIn = cleaningLogRepository.save(checkedInEntity);

        // Entity -> ResponseDTO 변환 후, return
        return cleaningLogMapper.toCheckInResponseDTO(savedCheckIn);
    }

    /**
     * 체크아웃
     * @param managerId 매니저ID
     * @param reservationId 예약ID
     * @param cleaningLogCheckOutReqDTO 체크아웃요청DTO
     * @return 체크아웃 정보를 담은 응답
     */
    @Override
    @Transactional
    public CleaningLogCheckOutRspDTO checkOut(Long managerId, Long reservationId, CleaningLogCheckOutReqDTO cleaningLogCheckOutReqDTO) {

        // 해당 예약건의 매니저가 맞는지 체크
        if (!reservationRepository.existsByReservationIdAndManager_ManagerId(reservationId, managerId)) {
            throw new IllegalStateException("해당 예약건의 매니저가 아닙니다.");
        }

        // Entity 조회 (= 예약ID로 조회)
        CleaningLog foundCleaningLog = cleaningLogRepository.findByReservation_ReservationId(reservationId);
        if (foundCleaningLog == null) {
            throw new NoSuchElementException("체크인을 먼저 진행해주세요.");
        }

        // 체크아웃
        foundCleaningLog.checkOut(cleaningLogCheckOutReqDTO.getOutFileId());

        // 방문완료로 변경
        Reservation foundReservation = reservationRepository.findReservationByreservationId(reservationId);
        foundReservation.updateStatus(ReservationStatus.COMPLETED);

        // Entity -> ResponseDTO 변환 후, return
        return cleaningLogMapper.toCheckOutResponseDTO(foundCleaningLog);
    }
}
