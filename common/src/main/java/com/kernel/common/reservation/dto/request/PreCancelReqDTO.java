package com.kernel.common.reservation.dto.request;

import lombok.Getter;
import java.util.List;

@Getter
public class PreCancelReqDTO {
    private List<Long> matchedManagers;
}