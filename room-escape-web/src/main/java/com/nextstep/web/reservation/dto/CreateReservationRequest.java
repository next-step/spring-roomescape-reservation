package com.nextstep.web.reservation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateReservationRequest {

    private Long scheduleId;
    private String name;

    public CreateReservationRequest(Long scheduleId,  String name) {
        this.scheduleId = scheduleId;
        this.name = name;
    }
}
