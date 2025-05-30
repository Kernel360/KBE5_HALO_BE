package com.kernel.common.manager.service;

import com.kernel.common.manager.dto.mapper.CleaningLogMapper;
import com.kernel.common.manager.dto.request.CleaningLogCheckInReqDTO;
import com.kernel.common.manager.dto.request.CleaningLogCheckOutReqDTO;
import com.kernel.common.manager.dto.response.CleaningLogCheckInRspDTO;
import com.kernel.common.manager.dto.response.CleaningLogCheckOutRspDTO;
import com.kernel.common.manager.entity.CleaningLog;
import com.kernel.common.manager.repository.CleaningLogRepository;
import jakarta.transaction.Transactional;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CleaningLogServiceImpl implements CleaningLogService {

    private final CleaningLogRepository cleaningLogRepository;
    private final CleaningLogMapper cleaningLogMapper;

    /**
     * 체크인
     * @param managerId
     * @param reservationId
     * @param cleaningLogCheckInReqDTO
     * @return 체크인 정보를 담은 응답
     */
    @Override
    @Transactional
    public CleaningLogCheckInRspDTO checkIn(Long managerId, Long reservationId, CleaningLogCheckInReqDTO cleaningLogCheckInReqDTO) {

        // TODO: 해당 예약건의 매니저가 맞는지 체크

        // 이미 등록된 cleaning log가 있는지 체크
        if (cleaningLogRepository.existsByReservationId(reservationId)) {
            throw new IllegalStateException("이미 체크인된 예약입니다.");
        }

        // RequestDTO -> Entity
        CleaningLog checkedInEntity = cleaningLogMapper.toCheckInEntity(reservationId, cleaningLogCheckInReqDTO);

        // 체크인
        CleaningLog savedCheckIn = cleaningLogRepository.save(checkedInEntity);

        // Entity -> ResponseDTO 변환 후, return
        return cleaningLogMapper.toCheckInResponseDTO(savedCheckIn);
    }

    /**
     * 체크아웃
     * @param managerId
     * @param reservationId
     * @param cleaningLogCheckOutReqDTO
     * @return 체크아웃 정보를 담은 응답
     */
    @Override
    @Transactional
    public CleaningLogCheckOutRspDTO checkOut(Long managerId, Long reservationId, CleaningLogCheckOutReqDTO cleaningLogCheckOutReqDTO) {

        // TODO: 해당 예약건의 매니저가 맞는지 체크

        // Entity 조회 (= 예약ID로 조회)
        CleaningLog foundCleaningLog = cleaningLogRepository.findByReservationId(reservationId);
        if (foundCleaningLog == null) {
            throw new NoSuchElementException("체크인이 되지 않았습니다.");
        }

        // 체크아웃
        foundCleaningLog.checkOut(cleaningLogCheckOutReqDTO.getOutFileId());

        // Entity -> ResponseDTO 변환 후, return
        return cleaningLogMapper.toCheckOutResponseDTO(foundCleaningLog);
    }
}
