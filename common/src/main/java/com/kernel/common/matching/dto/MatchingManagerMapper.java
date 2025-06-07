package com.kernel.common.matching.dto;

import com.kernel.common.manager.entity.Manager;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MatchingManagerMapper {

    public List<ManagerMatchingRspDTO> toRspDTOList(List<Manager> managers) {
        return managers.stream()
                .map(manager -> ManagerMatchingRspDTO.builder()
                        .managerId(manager.getManagerId())
                        .managerName(manager.getUserName())
                        .averageRating(manager.getAverageRating())
                        .reviewCount(manager.getReviewCount())
                        .profileImageId(manager.getProfileImageId())
                        .bio(manager.getBio())
                        //.feedbackType(manager.)
                        //.recentReservationDate()
                        .build()
                ).toList();
    }
}

